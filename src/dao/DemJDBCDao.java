package dao;

import domain.Entrada;
import domain.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class DemJDBCDao implements DemDao {

    private Connection conexao;
    private LoteDao loteDao;
    private MaterialDao materialDao;

    public void inserirDem(Entrada dem, Vector<Lote> lotes) throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            this.conexao.setAutoCommit(false);

            Date dataUtil = dem.getDataNota();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;

            String sqlDoc = "INSERT INTO documento(id_documento,data_documento,sigla_tipo_documento)" +
                    "VALUES(?,?,?)";

            PreparedStatement psDoc = this.conexao.prepareStatement(sqlDoc);
            psDoc.setString(1, dem.getNumNota());
            psDoc.setDate(2, dataSql);
            psDoc.setString(3, dem.getTipoEntrada().getTipoDoc().getSiglaTipoDoc());
            psDoc.executeUpdate();

            String sql = "INSERT INTO dem(cod_usuario,id_documento,id_fornecedor,cod_tipo,numero_ne,data_dem,valor_nota)" + "VALUES(?,?,?,?,?,now(),?);";

            PreparedStatement psDem = this.conexao.prepareStatement(sql);

            psDem.setInt(1, dem.getReponsavel().getCodigo());
            psDem.setString(2, dem.getNumNota());
            psDem.setLong(3, Long.parseLong(dem.getFornecedor().getId()));
            psDem.setInt(4, dem.getTipoEntrada().getCodigo());
            psDem.setString(5, dem.getNumNE());
            psDem.setDouble(6, Double.parseDouble(dem.getValorNota() + "".replace(',', '.')));
            psDem.executeUpdate();

            int cont = this.obterCodigoUltimo();

            for (int i = 0; i < lotes.size(); i++) {
                lotes.get(i).setCodigoDem(cont);
            }

            this.loteDao = new LoteJDBCDao();
            this.loteDao.inserirLotes(lotes, this.conexao);

            this.materialDao = new MaterialJDBCDao();
            this.materialDao.excluirMaterial(this.conexao);

            this.conexao.commit();
            this.conexao.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            this.conexao.rollback();
            this.conexao.close();
            throw new SQLException();
        }
    }

    public int obterCodigoUltimo() throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT MAX(d.codigo_dem) as codigo FROM dem d";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            int codigo = 0;
            while (res.next()) {
                codigo = res.getInt("codigo");
            }
            return codigo;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }

    }
}
