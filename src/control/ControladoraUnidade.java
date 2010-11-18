package control;

import dao.UnidadeDao;
import dao.UnidadeJDBCDao;
import domain.Unidade;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import util.PesquisarVector;

public class ControladoraUnidade implements ControladoraClasse {

    private Vector<Unidade> unidadesAtivas;
    private Vector<Unidade> unidadesInativas;
    private UnidadeDao unidadeDao;

    public ControladoraUnidade() {
        this.unidadeDao = new UnidadeJDBCDao();
        unidadesAtivas = new Vector<Unidade>();
        unidadesInativas = new Vector<Unidade>();
    }

    private Vector criarLinhaUnidade(Unidade unidade) {
        Vector linha = new Vector();
        linha.addElement(unidade.getCodigo());
        linha.addElement(unidade.getNome());
        return linha;
    }

    private Vector<Unidade> obterUnidades(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            unidadesAtivas = unidadeDao.obterUnidades(nome, status);
            return unidadesAtivas;
        } else {
            unidadesInativas = unidadeDao.obterUnidades(nome, status);
            return unidadesInativas;
        }
    }

    public Vector obterLinhas(String nome, String status) {
        Vector<Unidade> c;
        if (status.equals("Ativo")) {
            if (unidadesAtivas.isEmpty()) {
                c = new Vector<Unidade>();
                try {

                    c = obterUnidades(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = unidadesAtivas;
            }
        } else {
            if (unidadesInativas.isEmpty()) {
                c = new Vector<Unidade>();
                try {
                    c = obterUnidades(nome, status);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                            "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                c = unidadesInativas;
            }
        }
        Vector linhas = new Vector();
        for (int i = 0; i < c.size(); i++) {
            Unidade unidade = c.get(i);
            linhas.addElement(this.criarLinhaUnidade(unidade));
        }
        return linhas;

    }

    private void atualizarUnidade(Unidade unidade, Vector linha) {
        unidade.setCodigo(Integer.parseInt(linha.get(0).toString()));
        unidade.setNome(linha.get(1).toString());
    }

    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
    }

    public boolean inserir(Vector linha) {
        Unidade unidade = new Unidade();
        this.atualizarUnidade(unidade, linha);
        try {
            unidade.setCodigo(unidadeDao.inserirUnidade(unidade));
            unidadesAtivas.add(unidade);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar(Vector linha, String status) {
        Unidade unidade = new Unidade();
        this.atualizarUnidade(unidade, linha);
        unidade.setStatus(status.substring(0, 1).toLowerCase());
        try {
            unidadeDao.alterarUnidade(unidade);
            if (status.equals("Ativo")) {
                for (int i = 0; i < unidadesAtivas.size(); i++) {
                    if (unidadesAtivas.get(i).getCodigo() == unidade.getCodigo()) {
                        unidadesAtivas.get(i).setNome(unidade.getNome());
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < unidadesInativas.size(); i++) {
                    if (unidadesInativas.get(i).getCodigo() == unidade.getCodigo()) {
                        unidadesInativas.get(i).setNome(unidade.getNome());
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
        Unidade unidade = new Unidade();
        Usuario r = new Usuario();
        atualizarUnidade(unidade, linha);
        atualizarUsuario(r, responsavel);
        try {
            unidadeDao.alterarStatusUnidade(unidade.getCodigo(), motivo, r, acao);
            if (acao.equals("Desativar")) {
                unidade.setStatus("i");
                excluirUnidadeVector(unidadesAtivas, unidade);
                if (unidadesInativas.isEmpty()) {
                    this.obterUnidades("", "Inativa");
                } else {
                    unidadesInativas.add(unidade);
                }
                return true;
            } else {
                unidade.setStatus("a");
                unidadesAtivas.add(unidade);
                excluirUnidadeVector(unidadesInativas, unidade);
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

    private void excluirUnidadeVector(Vector<Unidade> v, Unidade c) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).equals(c)) {
                v.remove(i);
                break;
            }
        }
    }

    public Vector pesquisar(String nome, String status) {
        Vector resultadoUnidades, resultadoLinhas = new Vector();
        PesquisarVector pesq;
        if (status.equals("Ativo")) {
            pesq = new PesquisarVector(unidadesAtivas);
        } else {
            pesq = new PesquisarVector(unidadesInativas);
        }
        resultadoUnidades = pesq.pesquisar(nome);
        for (int i = 0; i < resultadoUnidades.size(); i++) {
            resultadoLinhas.addElement(criarLinhaUnidade((Unidade) resultadoUnidades.get(i)));
        }
        return resultadoLinhas;
    }
}
