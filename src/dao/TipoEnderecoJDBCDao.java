package dao;

import domain.TipoEndereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoEnderecoJDBCDao implements TipoEnderecoDao {

    private Connection conexao = null;
    private String servidor;

    public TipoEnderecoJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    @Override
    public Vector<TipoEndereco> obterTiposEndereco() throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            String sql = "SELECT * FROM tipo_endereco WHERE estado = 'a' ORDER BY cod_tipo_endereco";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<TipoEndereco> tipoEnderecos = new Vector<TipoEndereco>();
            while (res.next()) {
                TipoEndereco te = new TipoEndereco();
                te.setCodigo(res.getInt("cod_tipo_endereco"));
                te.setNome(res.getString("nome_tipo_endereco"));
                tipoEnderecos.addElement(te);
            }
            conexao.close();
            return tipoEnderecos;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
