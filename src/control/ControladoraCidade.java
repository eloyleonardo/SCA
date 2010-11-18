/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.CidadeDao;
import dao.CidadeJDBCDao;
import domain.Cidade;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class ControladoraCidade implements ControladoraClasse {

    private Vector<Cidade> cidadesAtivas;
    private Vector<Cidade> cidadesInativas;
    private CidadeDao cidadeDao;
    private int marcador;

    public ControladoraCidade() {
        this.cidadeDao = new CidadeJDBCDao();
        cidadesAtivas = new Vector<Cidade>();
        cidadesInativas = new Vector<Cidade>();
    }

    public int getMarcador() {
        return marcador;
    }

    public void setMarcador(int marcador) {
        this.marcador = marcador;
    }

    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao) {
        Cidade cidade = new Cidade();
        atualizarCidade(cidade, linha);
        Vector r = responsavel;
        try {
            cidadeDao.alterarStatusCidade(cidade, motivo, r, acao);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean inserir(Vector linha) {

        Cidade cidade = new Cidade();
        this.atualizarCidade(cidade, linha);
        try {
            //cidade.setSigla(cidade.getSigla());
            this.cidadesAtivas.add(cidade);
            this.cidadeDao.inserirCidade(cidade);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean alterar(Vector linha, String status) {
        Cidade cidade = new Cidade();
        this.atualizarCidade(cidade, linha);
        cidade.setStatus(status.substring(0, 1).toLowerCase());
        try {
            this.cidadeDao.alterarCidade(cidade);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);

        }
        return false;
    }

    public Vector pesquisar(String nome, String status) {
        return null;
    }

    public Vector obterLinhas(String nome, String status) {
        Vector<Cidade> c = new Vector<Cidade>();
        try {
            c = obterCidades(nome, status);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        Vector linhas = new Vector();
        for (int i = 0; i < c.size(); i++) {
            Cidade cidade = c.get(i);
            linhas.addElement(this.criarLinhaCidade(cidade));
        }
        return linhas;
    }

    private Vector<Cidade> obterCidades(String nome, String status) throws SQLException {
        if (status.equals("Ativo")) {
            cidadesAtivas = cidadeDao.obterCidades(nome, status);
            return cidadesAtivas;
        } else {
            cidadesInativas = cidadeDao.obterCidades(nome, status);
            return cidadesInativas;
        }
    }

    private Vector criarLinhaCidade(Cidade cidade) {
        Vector linha = new Vector();
        linha.addElement(cidade.getCodigo());
        linha.addElement(cidade.getNome());
        linha.addElement(cidade.getUf().getDescricao());
        return linha;
    }

    private void atualizarCidade(Cidade cidade, Vector linha) {
        cidade.setCodigo(Integer.parseInt(linha.get(0).toString()));
        cidade.setNome(linha.get(1).toString());
        cidade.getUf().setSigla(linha.get(2).toString());
    }
}
