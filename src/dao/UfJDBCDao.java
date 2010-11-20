package dao;

import domain.Uf;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class UfJDBCDao implements UfDao {

    Connection conexao = null;

    public void inserirUf(Uf uf) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO UF(sigla_uf,descricao_uf,estado) VALUES (?, ?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, uf.getSigla());
            ps.setString(2, uf.getDescricao());
            ps.setString(3, "a");
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    public void alterarUf(Uf uf) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE uf SET " +
                    " sigla_uf = ?, " +
                    " descricao_uf = '" + uf.getDescricao() + "'," +
                    " estado = '" + uf.getStatus() + "'" +
                    " WHERE sigla_uf = " + uf.getSigla();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, uf.getSigla());
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    @Override
    public void alterarStatusUf(String id, String motivo, Usuario responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "uf");
            ps.setString(2, id);
            ps.setInt(3, responsavel.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE uf SET " +
                        "estado = 'i' " +
                        "WHERE sigla_uf = ' " + id + " ';";
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE uf SET " +
                        "estado = 'a' " +
                        "WHERE sigla_uf = ' " + id + " ';";
            }
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    public Vector<Uf> obterUfs(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT * FROM uf WHERE estado = 'a' ORDER BY sigla_uf";
                } else {
                    sql = "SELECT * FROM uf WHERE descricao_uf LIKE '" + nome + "%' AND estado = 'a' ORDER BY sigla_uf";
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT * FROM uf WHERE estado = 'i' ORDER BY sigla_uf";
                } else {
                    sql = "SELECT * FROM uf WHERE descricao_uf LIKE '" + nome + "%' AND estado = 'i' ORDER BY sigla_uf";
                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Uf> ufs = new Vector<Uf>();

            while (res.next()) {
                Uf uf = new Uf();
                uf.setSigla(res.getString("sigla_uf"));
                uf.setDescricao(res.getString("descricao_uf"));
                uf.setStatus(res.getString("estado"));
                ufs.addElement(uf);
            }
            conexao.close();
            return ufs;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }

    public Uf obterUf(String nome) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT * FROM uf WHERE sigla_uf = '" + nome + "';";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            res.next();
            Uf uf = new Uf();
            uf.setSigla(res.getString("sigla_uf"));
            uf.setDescricao(res.getString("descricao_uf"));
            uf.setStatus(res.getString("estado"));
            conexao.close();
            return uf;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
