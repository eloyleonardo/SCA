package dao;

import domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NdJDBCDao implements NdDao {

    Connection conexao = null;

    public Vector<Nd> obterNds() throws SQLException {
        Vector<Nd> Nds = new Vector<Nd>();
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT * from nd";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Nd nd = new Nd();
                nd.setCodigo(res.getInt("cod_nd"));
                nd.setDescricao(res.getString("descricao_nd"));
                Nds.addElement(nd);
            }
            conexao.close();
            return Nds;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
