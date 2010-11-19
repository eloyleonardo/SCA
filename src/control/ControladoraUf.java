package control;

import dao.UfDao;
import dao.UfJDBCDao;
import domain.Uf;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import util.PesquisarVector;

public class ControladoraUf implements ControladoraClasse {

    private Vector<Uf> ufsAtivas;
    private Vector<Uf> ufsInativas;
    private UfDao ufDao;

    public ControladoraUf() {
        this.ufDao = new UfJDBCDao();
        ufsAtivas = new Vector<Uf>();
        ufsInativas = new Vector<Uf>();
    }

    private Vector criarLinhauf(Uf uf) {
        Vector linha = new Vector();
        linha.addElement(uf.getSigla());
        linha.addElement(uf.getDescricao());
        return linha;
    }

    private Vector<Uf> obterUfs(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            ufsAtivas = ufDao.obterUfs(nome, status);
            return ufsAtivas;
        } else {
            ufsInativas = ufDao.obterUfs(nome, status);
            return ufsInativas;
        }
    }

    public Vector obterLinhas(String nome, String status) {
        Vector<Uf> c;
        if (status.equals("Ativo")) {
            if (ufsAtivas.isEmpty()) {
                c = new Vector<Uf>();
                try {

                    c = obterUfs(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = ufsAtivas;
            }
        } else {
            if (ufsInativas.isEmpty()) {
                c = new Vector<Uf>();
                try {
                    c = obterUfs(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = ufsInativas;
            }
        }
        Vector linhas = new Vector();
        for (int i = 0; i < c.size(); i++) {
            Uf uf = c.get(i);
            linhas.addElement(this.criarLinhauf(uf));
        }
        return linhas;

    }

    private void atualizarUf(Uf uf, Vector linha) {
        uf.setSigla(linha.get(0).toString());
        uf.setDescricao(linha.get(1).toString());
    }

    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
    }

    public boolean inserir(Vector linha) {
        Uf uf = new Uf();
        this.atualizarUf(uf, linha);
        try {
            uf.setSigla(uf.getSigla());
            this.ufsAtivas.add(uf);
            this.ufDao.inserirUf(uf);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar(Vector linha, String status) {
        Uf uf = new Uf();
        this.atualizarUf(uf, linha);
        uf.setStatus(status.substring(0, 1).toLowerCase());
        try {
            this.ufDao.alterarUf(uf);
            if (status.equals("Ativo")) {
                for (int i = 0; i < ufsAtivas.size(); i++) {
                    if (this.ufsAtivas.get(i).getSigla().equals(uf.getSigla())) {
                        this.ufsAtivas.get(i).setDescricao(uf.getDescricao());
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < ufsInativas.size(); i++) {
                    if (ufsInativas.get(i).getSigla().equals(uf.getSigla())) {
                        ufsInativas.get(i).setDescricao(uf.getDescricao());
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
        Uf uf = new Uf();
        Usuario r = new Usuario();
        atualizarUf(uf, linha);
        atualizarUsuario(r, responsavel);
        try {
            ufDao.alterarStatusUf(uf.getSigla(), motivo, r, acao);
            if (acao.equals("Desativar")) {
                uf.setStatus("i");
                excluirUfVector(ufsAtivas, uf);
                if (ufsInativas.isEmpty()) {
                    this.obterUfs("", "Inativa");
                } else {
                    ufsInativas.add(uf);
                }
                return true;
            } else {
                uf.setStatus("a");
                ufsAtivas.add(uf);
                excluirUfVector(ufsInativas, uf);
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

    private void excluirUfVector(Vector<Uf> v, Uf c) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).equals(c)) {
                v.remove(i);
                break;
            }
        }
    }

    public Vector pesquisar(String nome, String status) {
        Vector resultadoUfs, resultadoLinhas = new Vector();
        PesquisarVector pesq;
        if (status.equals("Ativo")) {
            pesq = new PesquisarVector(ufsAtivas);
        } else {
            pesq = new PesquisarVector(ufsInativas);
        }
        resultadoUfs = pesq.pesquisar(nome);
        for (int i = 0; i < resultadoUfs.size(); i++) {
            resultadoLinhas.addElement(criarLinhauf((Uf) resultadoUfs.get(i)));
        }
        return resultadoLinhas;
    }
}
