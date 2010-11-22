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

    private String servidor;

    public DemJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public void inserirDem(Entrada dem, Vector<Lote> lotes) throws SQLException {
        Connection conexao2 = null;
        MaterialDao materialBEDao;
        try {
            Entrada demm = this.obterCodigoUltimo();

            conexao2 = FabricaConexao.obterConexao("JDBC", this.servidor);
            conexao2.setAutoCommit(false);

            Date dataUtil = dem.getDataNota();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;

            String sql = "INSERT INTO documento(id_documento,data_documento,sigla_tipo_documento)" + "VALUES('" + dem.getNumNota() + "','" + dataSql + "','" + dem.getTipoEntrada().getTipoDoc().getSiglaTipoDoc() + "')";

            PreparedStatement ps = conexao2.prepareStatement(sql);
            ps.executeUpdate();

            int codigo = demm.getCodigo() + 1;
            if(dem.getNumNE().equals("")){
                sql = "INSERT INTO dem(codigo_dem,cod_usuario,id_documento,id_fornecedor,cod_tipo,data_dem,valor_nota)" + "VALUES(" + codigo + ",'" + dem.getReponsavel().getCodigo() + "','" + dem.getNumNota() + "'," + dem.getFornecedor().getId() + "," + dem.getTipoEntrada().getCodigo() + ",now()," + dem.getValorNota() + ")";
            }
            else{
                sql = "INSERT INTO dem(codigo_dem,cod_usuario,id_documento,id_fornecedor,cod_tipo,numero_ne,data_dem,valor_nota)" + "VALUES(" + codigo + ",'" + dem.getReponsavel().getCodigo() + "','" + dem.getNumNota() + "'," + dem.getFornecedor().getId() + "," + dem.getTipoEntrada().getCodigo() + "," + dem.getNumNE() + ",now()," + dem.getValorNota() + ")";
            }
            ps = conexao2.prepareStatement(sql);
            ps.executeUpdate();

            for (int i = 0; i < lotes.size(); i++) {
                Entrada e = new Entrada();
                e.setCodigo(codigo);
                lotes.get(i).setEntrada(e);
            }
            LoteDao loteDao;
            loteDao = new LoteJDBCDao(this.servidor);
            loteDao.inserirLotes(lotes, conexao2);

            materialBEDao = new MaterialJDBCDao(this.servidor);
            materialBEDao.excluirMaterial(conexao2);

            conexao2.commit();
            conexao2.close();
        } catch (SQLException ex) {
            conexao2.close();
            throw new SQLException(ex.getCause());
        }
    }

    public Vector<Entrada> obterTodosDemEntre(Date dataInicial, Date dataFinal) throws SQLException {
        Connection conexao;
        try {
            Date dataUtil = dataInicial;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataInicioSql = (java.sql.Date) dataUtil;

            dataUtil = dataFinal;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataFinalSql = (java.sql.Date) dataUtil;

            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT d.codigo_dem as codigo_entrada," + " d.data_dem as data_entrada," + " d.valor_nota as valor_total," + " d.numero_ne as numero_nota_empenho," + " f.nome_fornecedor as nome_fornecedor," + " td.nome_tipo as tipo_entrada," + " doc.id_documento as numero_doc," + " doc.data_documento as data_doc," + " tdoc.nome_tipo_documento as nome_tipo_doc" + " FROM dem d," + " fornecedor f," + " documento doc," + " tipo_dem td," + " tipo_documento tdoc" + " WHERE d.id_fornecedor = f.id_fornecedor  AND" + " d.cod_tipo      = td.cod_tipo      AND" + " d.id_documento  = doc.id_documento AND" + " d.data_dem BETWEEN '" + dataInicioSql + "' AND '" + dataFinalSql + "'" + " ORDER BY d.codigo_dem ASC";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Entrada> dems;
            dems = new Vector<Entrada>();
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
            throw new SQLException(ex.getCause());
        }
    }

    public Entrada obterCodigoUltimo() throws SQLException {
        Connection conexao;
        try {
            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT MAX(d.codigo_dem) as codigo FROM dem d";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Entrada dem = new Entrada();
            while (res.next()) {
                dem.setCodigo(res.getInt("codigo"));
            }
            return dem;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
