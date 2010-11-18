package dao;

import domain.Entrada;
import domain.Documento;
import domain.Fornecedor;
import domain.Lote;
import domain.TipoEntrada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class DemJDBCDao implements DemDao {

    private Connection conexao;
    private LoteDao loteDao;
    private MaterialDao materialBEDao;

    public void inserirDem(Entrada dem, Vector<Lote> lotes) throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            this.conexao.setAutoCommit(false);

            Date dataUtil = dem.getDataNota();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;

            String sql = "INSERT INTO documento(id_documento,data_documento,sigla_tipo_documento)" +
                    "VALUES('" + dem.getNumNota() + "','" + dataSql + "','" + dem.getTipoEntrada().getTipoDoc().getSiglaTipoDoc() + "')";

            PreparedStatement ps = this.conexao.prepareStatement(sql);
            ps.executeUpdate();

            sql = "INSERT INTO dem(cod_usuario,id_documento,id_fornecedor,cod_tipo,numero_ne,data_dem,valor_nota)" +
                    "VALUES('" + dem.getReponsavel().getCodigo() + "','" + dem.getNumNota() + "'," + dem.getFornecedor().getId() + "," + dem.getTipoEntrada().getCodigo() + "," + dem.getNumNE() + ",now()," + dem.getValorNota() + ")";

            ps = this.conexao.prepareStatement(sql);
            ps.executeUpdate();

            Entrada demm = this.obterCodigoUltimo();
            int cont = demm.getCodigo();

            for (int i = 0; i < lotes.size(); i++) {
                lotes.get(i).setCodigoDem(cont);
            }

            this.loteDao = new LoteJDBCDao();
            this.loteDao.inserirLotes(lotes, this.conexao);

            this.materialBEDao = new MaterialJDBCDao();
            this.materialBEDao.excluirMaterial(this.conexao);

            this.conexao.commit();
            this.conexao.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage().toString());
            this.conexao.rollback();
            this.conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Entrada> obterTodosDemEntre(Date dataInicial, Date dataFinal) throws SQLException {
        try {
            Date dataUtil = dataInicial;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataInicioSql = (java.sql.Date) dataUtil;

            dataUtil = dataFinal;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataFinalSql = (java.sql.Date) dataUtil;

            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT d.codigo_dem as codigo_entrada," +
                    " d.data_dem as data_entrada," +
                    " d.valor_nota as valor_total," +
                    " d.numero_ne as numero_nota_empenho," +
                    " f.nome_fornecedor as nome_fornecedor," +
                    " td.nome_tipo as tipo_entrada," +
                    " doc.id_documento as numero_doc," +
                    " doc.data_documento as data_doc," +
                    " tdoc.nome_tipo_documento as nome_tipo_doc" +
                    " FROM dem d," +
                    " fornecedor f," +
                    " documento doc," +
                    " tipo_dem td," +
                    " tipo_documento tdoc" +
                    " WHERE d.id_fornecedor = f.id_fornecedor  AND" +
                    " d.cod_tipo      = td.cod_tipo      AND" +
                    " d.id_documento  = doc.id_documento AND" +
                    " d.data_dem BETWEEN '" + dataInicioSql + "' AND '" + dataFinalSql + "'" +
                    " ORDER BY d.codigo_dem ASC";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Entrada> dems;
            dems = new Vector<Entrada>();
            this.loteDao = new LoteJDBCDao();
            while (res.next()) {
                Entrada dem = new Entrada();
                TipoEntrada tipoDem = new TipoEntrada();
                Fornecedor fornecedor = new Fornecedor();
                Documento documento = new Documento();

                dem.setCodigo(res.getInt("codigo_entrada"));
                dem.setData(res.getDate("data_entrada"));
                dem.setNumNE(res.getInt("numero_nota_empenho") + "");
                dem.setValorNota(res.getDouble("valor_total"));
                fornecedor.setNome(res.getString("nome_fornecedor"));

                dem.setFornecedor(fornecedor);
                tipoDem.setNome(res.getString("tipo_entrada"));

                dem.setTipoEntrada(tipoDem);
                documento.setId_documento(res.getString("numero_doc"));

                documento.setDataDoc(res.getDate("data_doc"));
                dem.setDocumento(documento);
                dems.add(dem);
            }
            return dems;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public Entrada obterCodigoUltimo() throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT MAX(d.codigo_dem) as codigo FROM dem d";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Entrada dem = new Entrada();
            while (res.next()) {
                dem.setCodigo(res.getInt("codigo"));
            }
            return dem;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
