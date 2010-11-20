package dao;

import domain.Cidade;
import domain.Uf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CidadeJDBCDao implements CidadeDao {

    Connection conexao = null;

    public void inserirCidade(Cidade cidade) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO cidade(nome_cidade,sigla_uf,estado) VALUES (?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cidade.getNome());
            ps.setString(2, cidade.getUf().getSigla());
            ps.setString(3, "a");
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarCidade(Cidade cidade) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE cidade SET "
                    + " sigla_uf = '" + cidade.getUf().getSigla() + "', "
                    + " nome_cidade = '" + cidade.getNome() + "',"
                    + " estado = '" + cidade.getStatus() + "'"
                    + " WHERE cod_cidade = " + cidade.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Cidade> obterCidades(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT c.cod_cidade, c.nome_cidade, u.descricao_uf, c.estado "
                            + "FROM cidade c, uf u "
                            + "WHERE c.sigla_uf = u.sigla_uf AND "
                            + "c.estado = 'a' "
                            + "ORDER BY c.cod_cidade";
                } else {
                    sql = "SELECT c.cod_cidade, c.nome_cidade, u.descricao_uf, c.estado "
                            + "FROM cidade c, uf u "
                            + "WHERE c.sigla_uf = u.sigla_uf AND "
                            + "c.estado = 'a' AND "
                            + "c.nome_cidade LIKE '" + nome + "%' "
                            + "ORDER BY c.cod_cidade";
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT c.cod_cidade, c.nome_cidade, u.descricao_uf, c.estado "
                            + "FROM cidade c, uf u "
                            + "WHERE c.sigla_uf = u.sigla_uf AND "
                            + "c.estado = 'i' "
                            + "ORDER BY c.cod_cidade";
                } else {
                    sql = "SELECT c.cod_cidade, c.nome_cidade, u.descricao_uf, c.estado "
                            + "FROM cidade c, uf u "
                            + "WHERE c.sigla_uf = u.sigla_uf AND "
                            + "c.estado = 'i' AND "
                            + "c.nome_cidade LIKE '" + nome + "%' "
                            + "ORDER BY c.cod_cidade";
                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Cidade> cidades = new Vector<Cidade>();

            while (res.next()) {
                Cidade cidade = new Cidade();
                cidade.setCodigo(res.getInt("cod_cidade"));
                cidade.setNome(res.getString("nome_cidade"));
                cidade.getUf().setDescricao(res.getString("descricao_uf"));
                cidade.setStatus(res.getString("estado"));
                cidades.addElement(cidade);
            }
            conexao.close();
            return cidades;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public void alterarStatusCidade(Cidade cidade, String motivo, Vector responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)"
                    + "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "cidade");
            ps.setInt(2, cidade.getCodigo());
            ps.setInt(3, Integer.parseInt(responsavel.get(0).toString()));
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Ativar")) {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE cidade SET "
                        + "estado = 'a' "
                        + "WHERE cod_cidade = " + cidade.getCodigo();
            } else {
                ps.setString(6, "i");
                ps.executeUpdate();
                sql = "UPDATE cidade SET "
                        + "estado = 'i' "
                        + "WHERE cod_cidade = " + cidade.getCodigo();
            }
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Cidade> obterCidades(Uf uf) throws SQLException {
        UfDao ufDao = new UfJDBCDao();
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT * FROM cidade WHERE sigla_uf = '" + uf.getSigla() + "';";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Cidade> cidades = new Vector<Cidade>();

            while (res.next()) {
                Cidade cidade = new Cidade();
                cidade.setCodigo(res.getInt("cod_cidade"));
                cidade.setNome(res.getString("nome_cidade"));
                cidade.setUf(ufDao.obterUf(res.getString("sigla_uf")));
                cidades.addElement(cidade);
            }
            conexao.close();
            return cidades;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
