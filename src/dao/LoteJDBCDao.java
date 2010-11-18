package dao;

import domain.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class LoteJDBCDao implements LoteDao {

    private Connection conexao;
    private MaterialJDBCDao materialDao;

    public void inserirLote(Lote lote) throws SQLException {
        try {
            Date dataUtil = new Date(lote.getValidade());
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;

            String sql = "INSERT INTO lote( cod_material, codigo_dem, valor_material_lote, validade_lote, qnt_entrada, data_entrada)" + "VALUES(?,?,?,?,?,now());";
            PreparedStatement ps = conexao.prepareStatement(sql);
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

    public void inserirLotes(Vector<Lote> lote, Connection conexao) throws SQLException {
        this.conexao = conexao;
        this.materialDao = new MaterialJDBCDao();

        Lote loteAtual = new Lote();
        for (int i = 0; i < lote.size(); i++) {
            loteAtual = (Lote) lote.get(i);
            try {
                this.inserirLote(loteAtual);
                this.materialDao.alterarQuantidadeMaterial(loteAtual.getMaterial(), this.conexao);
            } catch (SQLException ex) {
                this.conexao.rollback();
                this.conexao.close();
            }
        }
    }
}
