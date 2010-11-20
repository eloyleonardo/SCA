package dao;

import domain.TipoDocumento;
import domain.TipoEntrada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TipoEntradaJDBCDao implements TipoEntradaDao {

    private Connection conexao = null;

    public Vector<TipoEntrada> obterTipoEntrada() throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql =  "SELECT td.cod_tipo AS codigo, "+
                             "td.nome_tipo              AS nome, "+
                             " td.sigla_tipo_documento   AS sigla_tipo_doc, "+
                             " tdoc.nome_tipo_documento  AS tipo_doc "+
                             " FROM tipo_dem td, "+
                             " tipo_documento tdoc "+
                             " WHERE td.estado = 'A' AND "+
                                   " td.sigla_tipo_documento = tdoc.sigla_tipo_documento "+
                             "ORDER BY cod_tipo";

            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<TipoEntrada> tipoEntrada = new Vector<TipoEntrada>();
            while (res.next()) {
                TipoEntrada te = new TipoEntrada();
                TipoDocumento td = new TipoDocumento();

                td.setSiglaTipoDoc(res.getString("sigla_tipo_doc"));
                td.setNomeTipoDoc(res.getString("tipo_doc"));

                te.setCodigo(res.getInt("codigo"));
                te.setNome(res.getString("nome"));

                te.setTipoDoc(td);
                tipoEntrada.add(te);
            }
            this.conexao.close();
            return tipoEntrada;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            throw new SQLException();
        }
    }
}
