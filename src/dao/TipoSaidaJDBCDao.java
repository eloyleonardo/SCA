package dao;

import domain.TipoSaida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoSaidaJDBCDao implements TipoSaidaDao {

    private Connection conexao;
    private String servidor;

    public TipoSaidaJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public Vector<TipoSaida> obterTiposSaida() throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT cod_tipo_saida,nome_tipo_saida FROM tipo_saida";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<TipoSaida> tiposSaida = new Vector<TipoSaida>();

            while (res.next()) {
                TipoSaida tipoSaida = new TipoSaida();
                tipoSaida.setCodigo(res.getInt("cod_tipo_saida"));
                tipoSaida.setNome(res.getString("nome_tipo_saida"));
                tiposSaida.add(tipoSaida);
            }
            conexao.close();
            return tiposSaida;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
