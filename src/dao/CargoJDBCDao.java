package dao;

import domain.Cargo;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class CargoJDBCDao implements CargoDao {

    Connection conexao = null;

    public int inserirCargo(Cargo cargo) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String chefia;
            if (cargo.isChefia()) {
                chefia = "s";
            } else {
                chefia = "n";
            }
            String sql = "INSERT INTO cargo (nome_cargo,chefia) values ('" + cargo.getNome() + "','" + chefia + "')";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
            return obterIdCargo(cargo);
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    private int obterIdCargo(Cargo cargo) throws SQLException {
        conexao = FabricaConexao.obterConexao("JDBC");
        String sql = "SELECT cod_cargo FROM cargo WHERE nome_cargo ='" + cargo.getNome() + "'";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            return res.getInt("cod_cargo");
        }
        return 0;
    }

    public void alterarCargo(Cargo cargo) throws SQLException {
        try {
            String chefia;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            if (cargo.isChefia()) {
                chefia = "s";
            } else {
                chefia = "n";
            }
            String sql = " UPDATE cargo SET " +
                    " cod_cargo = " + cargo.getCodigo() + " , " +
                    " nome_cargo = '" + cargo.getNome() + "'," +
                    " estado = '" + cargo.getStatus() + "'," +
                    " chefia = '" + chefia + "' " +
                    " WHERE cod_cargo = " + cargo.getCodigo() + " ;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    public void alterarStatusCargo(int id, String motivo, Usuario responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "cargo");
            ps.setInt(2, id);
            ps.setInt(3, responsavel.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE cargo SET " +
                        "estado = 'i' " +
                        "WHERE cod_cargo = " + id;
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE cargo SET " +
                        "estado = 'a' " +
                        "WHERE cod_cargo = " + id;
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

    public Vector<Cargo> obterCargos(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT * FROM cargo WHERE estado = 'a' ORDER BY cod_cargo";
                } else {
                    sql = "SELECT * FROM cargo WHERE nome LIKE '" + nome + "%' AND estado = 'a' ORDER BY cod_cargo";
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT * FROM cargo WHERE estado = 'i' AND cod_cargo != 0 ORDER BY cod_cargo";
                } else {
                    sql = "SELECT * FROM cargo WHERE nome LIKE '" + nome + "%' AND estado = 'i' ORDER BY cod_cargo";
                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Cargo> cargos = new Vector<Cargo>();

            while (res.next()) {
                Cargo cargo = new Cargo();
                cargo.setCodigo(res.getInt("cod_cargo"));
                cargo.setNome(res.getString("nome_cargo"));
                cargo.setStatus(res.getString("estado"));
                if (res.getString("chefia").equals("s")) {
                    cargo.setChefia(true);
                } else {
                    cargo.setChefia(false);
                }
                cargos.addElement(cargo);
            }
            conexao.close();
            return cargos;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
