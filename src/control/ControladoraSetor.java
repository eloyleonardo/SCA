package control;

import dao.SetorDao;
import dao.SetorJDBCDao;
import domain.Setor;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import util.PesquisarVector;

public class ControladoraSetor implements ControladoraClasse {

    private Vector<Setor> setoresAtivos;
    private Vector<Setor> setoresInativos;
    private SetorDao setorDao;

    public ControladoraSetor(String servidor) {
        this.setorDao = new SetorJDBCDao(servidor);
        setoresAtivos = new Vector<Setor>();
        setoresInativos = new Vector<Setor>();
    }

    public int obterCodSetor(String nome) {
        for (int i = 0; i < setoresAtivos.size(); i++) {
            if (setoresAtivos.get(i).getNome().equals(nome)) {
                return setoresAtivos.get(i).getCodigo();
            }
        }
        return 0;
    }

    private Vector criarLinhaSetor(Setor setor) {
        Vector linha = new Vector();
        linha.addElement(setor.getCodigo());
        linha.addElement(setor.getNome());
        return linha;
    }

    private Vector<Setor> obterSetores(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            setoresAtivos = setorDao.obterSetores(nome, status);
            return setoresAtivos;
        } else {
            setoresInativos = setorDao.obterSetores(nome, status);
            return setoresInativos;
        }
    }

    public Vector obterLinhas(String nome, String status) {
        Vector<Setor> c;
        if (status.equals("Ativo")) {
            if (setoresAtivos.isEmpty()) {
                c = new Vector<Setor>();
                try {

                    c = obterSetores(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = setoresAtivos;
            }
        } else {
            if (setoresInativos.isEmpty()) {
                c = new Vector<Setor>();
                try {
                    c = obterSetores(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = setoresInativos;
            }
        }
        Vector linhas = new Vector();

        for (int i = 0; i < c.size(); i++) {
            Setor setor = c.get(i);
            linhas.addElement(this.criarLinhaSetor(setor));
        }
        return linhas;

    }

    private void atualizarSetor(Setor setor, Vector linha) {
        setor.setCodigo(Integer.parseInt(linha.get(0).toString()));
        setor.setNome(linha.get(1).toString());
    }

    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
    }

    public boolean inserir(Vector linha) {
        Setor setor = new Setor();
        this.atualizarSetor(setor, linha);
        try {
            setor.setCodigo(setorDao.inserirSetor(setor));
            setoresAtivos.add(setor);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar(Vector linha, String status) {
        Setor setor = new Setor();
        this.atualizarSetor(setor, linha);
        setor.setStatus(status.substring(0, 1).toLowerCase());
        try {
            setorDao.alterarSetor(setor);
            if (status.equals("Ativo")) {
                for (int i = 0; i < setoresAtivos.size(); i++) {
                    if (setoresAtivos.get(i).getCodigo() == setor.getCodigo()) {
                        setoresAtivos.get(i).setNome(setor.getNome());
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < setoresInativos.size(); i++) {
                    if (setoresInativos.get(i).getCodigo() == setor.getCodigo()) {
                        setoresInativos.get(i).setNome(setor.getNome());
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            return false;
        }
    }

    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao) {
        Setor setor = new Setor();
        Usuario r = new Usuario();
        atualizarSetor(setor, linha);
        atualizarUsuario(r, responsavel);
        try {
            setorDao.alterarStatusSetor(setor.getCodigo(), motivo, r, acao);
            if (acao.equals("Desativar")) {
                setor.setStatus("i");
                excluirSetorVector(setoresAtivos, setor);
                if (setoresInativos.isEmpty()) {
                    this.obterSetores("", "Inativa");
                } else {
                    setoresInativos.add(setor);
                }
                return true;
            } else {
                setor.setStatus("a");
                setoresAtivos.add(setor);
                excluirSetorVector(setoresInativos, setor);
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            return false;
        }
    }

    private void excluirSetorVector(Vector<Setor> v, Setor c) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).equals(c)) {
                v.remove(i);
                break;
            }
        }
    }

    public Vector pesquisar(String nome, String status) {
        Vector resultadoSetores, resultadoLinhas = new Vector();
        PesquisarVector pesq;
        if (status.equals("Ativo")) {
            pesq = new PesquisarVector(setoresAtivos);
        } else {
            pesq = new PesquisarVector(setoresInativos);
        }
        resultadoSetores = pesq.pesquisar(nome);
        for (int i = 0; i < resultadoSetores.size(); i++) {
            resultadoLinhas.addElement(criarLinhaSetor((Setor) resultadoSetores.get(i)));
        }
        return resultadoLinhas;
    }
}
