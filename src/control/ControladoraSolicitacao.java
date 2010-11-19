package control;

import dao.SolicitacaoDao;
import dao.SolicitacaoJDBCDao;
import domain.Material;
import domain.Setor;
import domain.Solicitacao;
import domain.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControladoraSolicitacao {

    SolicitacaoDao solicitacaoDao = new SolicitacaoJDBCDao();
    Vector<Solicitacao> solicitacoes;
    int marcador;

    public ControladoraSolicitacao() {
        this.marcador = -1;
    }

    public Vector obterItensSolicitacao(int index) {
        this.marcador = index;
        return criarLinhaItensSolicitacao(solicitacoes.get(index).getMateriais(), solicitacoes.get(index).getQuantidade());
    }

    public int getMarcador() {
        return marcador;
    }

    public Vector obter(Vector usuario) {
        Vector<Solicitacao> s = null;
        try {
            s = obterSolicitacoes(usuario);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            Vector linhas = new Vector();

            for (int i = 0; i < s.size(); i++) {
                Solicitacao sol = s.get(i);
                linhas.addElement(this.criarLinhaSolicitacao(sol));
            }
            return linhas;
        }
    }

    private Vector criarLinhaSolicitacao(Solicitacao s) {
        Vector linha = new Vector();
        linha.addElement(s.getCodigo());
        linha.addElement(s.getResponsavel().getNome());
        linha.addElement(s.getDataRequisicao());
        ////
        linha.addElement(s.getResponsavel().getSetor().getNome());
        linha.addElement(s.getDataAprovacao());

        return linha;
    }

    private Vector criarLinhaItensSolicitacao(ArrayList<Material> val, ArrayList qnt) {
        Vector linhas = new Vector();
        for (int i = 0; i < val.size(); i++) {
            Vector linha = new Vector();
            linha.add(i + 1);
            linha.add(val.get(i).getNd().getCodigo() + val.get(i).getSubitem().getCodigo());
            linha.add(val.get(i).getDescricao());
            linha.add(val.get(i).getUnidade().getNome());
            linha.add(qnt.get(i));
            linha.add(qnt.get(i));
            linhas.add(linha);
        }
        return linhas;
    }

    public Vector obterMateriaisPorSolicitacao(int cod) throws SQLException {
        Vector materiaisDaSolicitacao = new Vector();
        try {
            materiaisDaSolicitacao = solicitacaoDao.obterMateriaisPorSolicitacao(cod);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return materiaisDaSolicitacao;
    }

    private Solicitacao buscarSolicitacao(Vector linha) {
        for (int i = 0; i < solicitacoes.size(); i++) {
            if (solicitacoes.get(i).getCodigo() == Integer.valueOf(linha.get(0).toString())) {
                return solicitacoes.get(i);
            }
        }
        return null;
    }

    private Vector obterSolicitacoes(Vector usuario) throws SQLException {
        Setor s = new Setor();
        s.setCodigo(Integer.parseInt(usuario.get(6).toString()));
        s.setNome(usuario.get(7).toString());
        this.solicitacoes = solicitacaoDao.obteSolicitacoes(s);
        return solicitacoes;
    }

    public boolean aprovarSolicitacao(Vector linha, Vector qtd) {
        Solicitacao s = buscarSolicitacao(linha);
        s.setQuantidadeAprovada(qtd);
        try {
            solicitacaoDao.aprovarSolicitacao(s);
            this.removerSolicitacao(Integer.valueOf(linha.get(0).toString()));
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public boolean rejeitarSolicitacao(Vector linha) {
        try {
            solicitacaoDao.rejeitarSolicitacao(buscarSolicitacao(linha));
            this.removerSolicitacao(Integer.valueOf(linha.get(0).toString()));
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    void removerSolicitacao(int id) {
        for (int i = 0; i < this.solicitacoes.size(); i++) {
            if (solicitacoes.get(i).getCodigo() == id) {
                this.solicitacoes.remove(i);
                return;
            }
        }
    }

    public Vector obterSolicitacoesAprovadas(String nome) throws SQLException {
//        Vector solicitacoesAprovadas = new Vector();
        Vector linhas = new Vector();
        try {
            solicitacoes = solicitacaoDao.obterSolicitacoesAprovadas(nome);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        for (int i = 0; i < solicitacoes.size(); i++) {
            linhas.add(criarLinhaSolicitacao(solicitacoes.get(i)));
        }
        return linhas;
    }

    public void inserirNovaSolicitacao(Vector linha) throws SQLException {
        try {
            Solicitacao sol = new Solicitacao();
            sol = atualizarSolicitacao(linha);
            solicitacaoDao.inserirSolicitacao(sol);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);

        }
    }

    public Solicitacao atualizarSolicitacao(Vector linha) {
        Solicitacao s = new Solicitacao();
        int i;
        Usuario u = new Usuario();
        for (i = 0; i < linha.size() - 2; i++) {
            Material m = new Material();
            m.setCodigo(Integer.parseInt(((Vector) linha.get(i)).get(0).toString()));
            s.getMateriais().add(m);
            s.getQuantidade().add(Double.parseDouble(((Vector) linha.get(i)).get(1).toString()));
        }
        s.getResponsavel().setCodigo(Integer.parseInt(linha.get(i).toString()));
        s.getResponsavel().getSetor().setCodigo(Integer.parseInt(linha.get(i+1).toString()));
        return s;
    }
}
