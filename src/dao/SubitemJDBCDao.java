package dao;

import domain.SubItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SubitemJDBCDao implements SubitemDao {

    private Connection conexao = null;
    private String servidor;

    public SubitemJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public Vector<SubItem> obterSubitens() throws SQLException {
        Vector<SubItem> subitens = new Vector<SubItem>();
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT * from subitem";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                SubItem subitem = new SubItem();
                subitem.setCodigo(res.getInt("cod_subitem"));
                subitem.setDescricao(res.getString("descricao"));
                subitens.addElement(subitem);
            }
            conexao.close();
            return subitens;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
