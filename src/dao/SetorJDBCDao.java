package dao;

import domain.Setor;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class SetorJDBCDao implements SetorDao {

    Connection conexao = null;

    public int inserirSetor(Setor setor) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO setor (nome_setor,estado) VALUES ('" + setor.getNome() + "','a')";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
            return obterIdSetor(setor);
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    private int obterIdSetor(Setor setor) throws SQLException {
        conexao = FabricaConexao.obterConexao("JDBC");
        String sql = "SELECT cod_setor FROM setor WHERE nome_setor ='" + setor.getNome() + "'";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            return res.getInt("cod_setor");
        }
        return 0;
    }

    public void alterarSetor(Setor setor) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE setor SET " +
                    " cod_setor = ?, " +
                    " nome_setor = '" + setor.getNome() + "'," +
                    " estado = '" + setor.getStatus() + "'" +
                    " WHERE cod_setor = " + setor.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, setor.getCodigo());
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

    public void alterarStatusSetor(int id, String motivo, Usuario responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "setor");
            ps.setInt(2, id);
            ps.setInt(3, responsavel.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE setor SET " +
                        "estado = 'i' " +
                        "WHERE cod_setor = " + id;
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE setor SET " +
                        "estado = 'a' " +
                        "WHERE cod_setor = " + id;
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

    public Vector<Setor> obterSetores(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT * FROM setor WHERE estado = 'a' ORDER BY cod_setor";
                } else {
                    sql = "SELECT * FROM setor WHERE nome LIKE '" + nome + "%' AND estado = 'a' ORDER BY cod_setor";
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT * FROM setor WHERE estado = 'i' AND cod_setor != 0 ORDER BY cod_setor";
                } else {
                    sql = "SELECT * FROM setor WHERE nome LIKE '" + nome + "%' AND estado = 'i' ORDER BY cod_setor";
                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Setor> setors = new Vector<Setor>();

            while (res.next()) {
                Setor setor = new Setor();
                setor.setCodigo(res.getInt("cod_setor"));
                setor.setNome(res.getString("nome_setor"));
                setor.setStatus(res.getString("estado"));
                setors.addElement(setor);
            }
            conexao.close();
            return setors;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}