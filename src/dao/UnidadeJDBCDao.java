package dao;

import domain.Unidade;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class UnidadeJDBCDao implements UnidadeDao {

    Connection conexao = null;

    public int inserirUnidade(Unidade unidade) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO unidade (descricao_unidade,estado) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, unidade.getNome());
            ps.setString(2, "a");
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
            return obterIdUnidade(unidade);
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    private int obterIdUnidade(Unidade unidade) throws SQLException {
        conexao = FabricaConexao.obterConexao("JDBC");
        String sql = "SELECT cod_unidade FROM unidade WHERE descricao_unidade ='" + unidade.getNome() + "'";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            return res.getInt("cod_unidade");
        }
        return 0;
    }

    public void alterarUnidade(Unidade unidade) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE unidade SET " +
                    " cod_unidade = ?, " +
                    " descricao_unidade = '" + unidade.getNome() + "'," +
                    " estado = '" + unidade.getStatus() + "'" +
                    " WHERE cod_unidade = " + unidade.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, unidade.getCodigo());
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarStatusUnidade(int id, String motivo, Usuario responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "unidade");
            ps.setInt(2, id);
            ps.setInt(3, responsavel.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE unidade SET " +
                        "estado = 'i' " +
                        "WHERE cod_unidade = " + id;
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE unidade SET " +
                        "estado = 'a' " +
                        "WHERE cod_unidade = " + id;
            }
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Unidade> obterUnidades(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT * FROM unidade WHERE estado = 'a' ORDER BY cod_unidade";
                } else {
                    sql = "SELECT * FROM unidade WHERE nome LIKE '" + nome + "%' AND estado = 'a' ORDER BY cod_unidade";
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT * FROM unidade WHERE estado = 'i' AND cod_unidade != 0 ORDER BY cod_unidade";
                } else {
                    sql = "SELECT * FROM unidade WHERE nome LIKE '" + nome + "%' AND estado = 'i' ORDER BY cod_unidade";
                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Unidade> unidades = new Vector<Unidade>();

            while (res.next()) {
                Unidade unidade = new Unidade();
                unidade.setCodigo(res.getInt("cod_unidade"));
                unidade.setNome(res.getString("descricao_unidade"));
                unidade.setStatus(res.getString("estado"));
                unidades.addElement(unidade);
            }
            conexao.close();
            return unidades;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
