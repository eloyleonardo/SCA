package dao;

import domain.Nd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class NdJDBCDao implements NdDao {

    private Connection conexao = null;
    private String servidor;

    public NdJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public Vector<Nd> obterNds() throws SQLException {
        Vector<Nd> Nds = new Vector<Nd>();
        try {
            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
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
