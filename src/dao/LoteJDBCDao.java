
package dao;

import domain.Entrada;
import domain.Lote;
import domain.Material;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class LoteJDBCDao implements LoteDao{
    private Connection conexao;
    private MaterialJDBCDao materialDao;

    private void inserirLote(Lote lote) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = lote.getValidade();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;

            String sql = "INSERT INTO lote( cod_material, codigo_dem, valor_material_lote, validade_lote, qnt_entrada, data_entrada)"
                           +"VALUES(?,?,?,?,?,now());";
            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.setInt(1, lote.getMaterial().getCodigo());
            ps.setInt(2, lote.getCodigoDem());
            ps.setDouble(3, lote.getValorUnidade());
            ps.setDate(4, dataSql);
            ps.setDouble(5, lote.getQuantidadeTotal());
            ps.executeUpdate();
        } catch (SQLException ex) {
            this.conexao.rollback();
            this.conexao.close();
            throw new SQLException();
        }
    }
    public void inserirLotes(Vector<Lote> lote, Connection conexao) throws SQLException{
        this.conexao =  conexao;
        this.materialDao = new MaterialJDBCDao();

        Lote loteAtual = new Lote();
        for(int i = 0 ; i< lote.size() ;  i++){
            loteAtual = (Lote) lote.get(i);
            try{
                this.inserirLote(loteAtual);
                this.materialDao.alterarQuantidadeMaterial(loteAtual.getMaterial(),this.conexao);
            } catch (SQLException ex) {
                this.conexao.rollback();
                this.conexao.close();
            }
        }
    }
    public Vector<Lote> obterLotesDem(Entrada dem) throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT l.cod_lote as codigo,"+
                     "    m.descricao_material as descricao,"+
                     "    l.qnt_entrada as qnt_entrada,"+
                     "    l.validade_lote as validade," +
                     "    l.valor_material_lote as valor_material"+
                     " FROM lote l,"+
                     "      material m"+
                     " WHERE l.cod_material = m.cod_material AND"+
                     "       l.codigo_dem = " + dem.getCodigo();
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Lote> lotes = new Vector<Lote>();
            while (res.next()) {
                Lote lote  = new Lote ();
                Material material = new Material();
                lote.setCodigo(res.getInt("codigo"));
                material.setDescricao(res.getString("descricao"));
                lote.setMaterial(material);
                lote.setQuantidadeTotal(res.getDouble("qnt_entrada"));
                lote.setValidade(res.getDate("validade"));
                lote.setValorUnidade(res.getDouble("valor_material"));
                lotes.add(lote);
            }
            this.conexao.close();
            return lotes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }
}
