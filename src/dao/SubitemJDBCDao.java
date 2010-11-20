package dao;

import domain.Material;
import domain.SubItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SubitemJDBCDao implements SubitemDao {

    Connection conexao = null;

    public Vector<SubItem> obterSubitens() throws SQLException {
        Vector<SubItem> subitens = new Vector<SubItem>();
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT * from subitem";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                SubItem subitem = new SubItem();
                subitem.setCodigo(res.getInt("cod_subitem"));
                subitem.setDescricao(res.getString("descricao"));
                subitens.addElement(subitem);
            }
            conexao.close();
            return subitens;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }
}
