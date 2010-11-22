package dao;

import domain.Saida;
import domain.TipoSaida;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public class SaidaJDBCDao implements SaidaDao {

    private Connection conexao = null;
    private String servidor;

    public SaidaJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public void inserirSaida(int codUsuario, int codSaida, int codSolicitacao) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO dsm (data_saida,cod_solicitacao,cod_tipo_saida,cod_usuario) values (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            Date dataUtil = new Date();
            java.sql.Date data = new java.sql.Date(dataUtil.getTime());
            ps.setDate(1, data);
            ps.setInt(2, codSolicitacao);
            ps.setInt(3, codSaida);
            ps.setInt(4, codUsuario);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public int obterUltimaDsm() throws SQLException {
        int num = 0;
        String sql;
        PreparedStatement ps;
        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
        conexao.setAutoCommit(false);
        sql = "select cod_dsm from dsm order by cod_dsm desc LIMIT 1";
        ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        conexao.commit();
        while (res.next()) {
            num = (res.getInt("cod_dsm"));
        }
        conexao.close();
        return num;
    }

    public void registrarSaida(Saida saida) throws SQLException {
        MaterialDao materialDao = new MaterialJDBCDao(this.servidor);
        int n = 0;
        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
        String sql;
        PreparedStatement ps;
        for (int i = 0; i < saida.getMaterial().size(); i++) {
            double soma = 0;
            String qntAtendida = saida.getQuantidadeAtendida().get(i).toString();
            Vector linhaRecebida = materialDao.carregarLotesMaterial(saida.getMaterial().get(i).getCodigo());
            boolean saidaOk = false;
            while (saidaOk == false) {
                if (soma + Integer.parseInt(((Vector) linhaRecebida.get(n)).get(1).toString()) >= saida.getQuantidadeAtendida().get(i)) {
                    try {
                        sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida) " +
                                "VALUES (?,?,?,?)";
                        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
                        conexao.setAutoCommit(false);
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, saida.getCodigo());
                        ps.setInt(2, Integer.parseInt(((Vector) linhaRecebida.get(n)).get(0).toString()));
                        ps.setDouble(3, saida.getQuantidadeAtendida().get(i) - soma);
                        Date dataUtil = new Date();
                        java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                        ps.setDate(4, data);
                        ps.executeUpdate();
                        conexao.commit();
                        soma = soma + (saida.getQuantidadeAtendida().get(i) - soma);
                        materialDao.alterarEstoqueMaterial(saida.getMaterial().get(i).getCodigo(), saida.getQuantidadeAtendida().get(i));
                        if (saida.getMaterial().get(i).getEstoqueAtual() - saida.getQuantidadeAtendida().get(i) < saida.getMaterial().get(i).getQntMinima()) {
                            materialDao.MaterialAbaixo(saida.getMaterial().get(i).getCodigo());
                        }
                        sql = "UPDATE solicitacao SET estado = 'sa' WHERE cod_solicitacao = " + saida.getRequisicao().getCodigo();
                        ps = conexao.prepareStatement(sql);
                        ps.executeUpdate();
                        conexao.commit();
                        conexao.close();
                    } catch (SQLException ex) {
                        throw new SQLException(ex.getCause());
                    }
                    saidaOk = true;
                } else {
                    try {
                        sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida) " +
                                "VALUES (?,?,?,?)";
                        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
                        conexao.setAutoCommit(false);
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, saida.getCodigo());
                        ps.setInt(2, Integer.parseInt(((Vector) linhaRecebida.get(n)).get(0).toString()));
                        ps.setDouble(3, Double.parseDouble(((Vector) linhaRecebida.get(n)).get(1).toString()));
                        Date dataUtil = new Date();
                        java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                        ps.setDate(4, data);
                        ps.executeUpdate();
                        conexao.commit();
                        conexao.close();
                        soma = soma + Double.parseDouble(((Vector) linhaRecebida.get(n)).get(1).toString());
                    } catch (SQLException ex) {
                        throw new SQLException(ex.getCause());
                    }
                    n++;
                }
            }
            try {
                sql = "UPDATE item_solicitacao SET quantidade_atendida = " + Double.parseDouble(qntAtendida) +
                        " WHERE cod_solicitacao = " + saida.getRequisicao().getCodigo() +
                        " AND cod_material = " + saida.getMaterial().get(0).getCodigo();
                conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
                // conexao.setAutoCommit(false);
                ps = conexao.prepareStatement(sql);
                ps.executeUpdate();
                conexao.commit();
                conexao.close();
            } catch (SQLException ex) {
                throw new SQLException(ex.getCause());
            }
        }
    }

    public void registrarSaidaOutrosMotivos(Saida saida) throws SQLException {
        MaterialDao materialDao = new MaterialJDBCDao(this.servidor);
        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
        String sql;
        PreparedStatement ps;
        for (int i = 0; i < saida.getMaterial().size(); i++) {
            double soma = 0;
            double estoqueMinimo = saida.getMaterial().get(i).getQntMinima();
            int n = 0;
            double qntEstoque = saida.getMaterial().get(i).getEstoqueAtual();
            double qntAtendida = Double.parseDouble(saida.getQuantidadeAtendida().get(i).toString());
            int codMaterial = saida.getMaterial().get(i).getCodigo();

            sql = "SELECT entrada.cod_lote,  COALESCE(entrada.val_entrada - saida.val_saida,0) AS total " +
                    "FROM (SELECT lo.cod_lote as cod_lote, " +
                    "sum(lo.qnt_entrada) as val_entrada " +
                    "FROM lote lo " +
                    "WHERE lo.cod_material = " + codMaterial +
                    "GROUP BY lo.cod_lote " +
                    "ORDER BY lo.cod_lote) entrada, " +
                    "(SELECT lo.cod_lote as cod_lote, " +
                    "COALESCE(sum(sm.qnt_saida),0) as val_saida " +
                    "FROM saida_material sm " +
                    "RIGHT OUTER JOIN lote lo ON sm.cod_lote = lo.cod_lote " +
                    "WHERE lo.cod_material = " + codMaterial +
                    "GROUP BY lo.cod_lote  " +
                    "ORDER BY lo.cod_lote) saida " +
                    "WHERE entrada.cod_lote = saida.cod_lote AND " +
                    "COALESCE(entrada.val_entrada - saida.val_saida,0) > 0;";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector linhaRecebida = new Vector();
            while (res.next()) {
                Vector linhaMaterial = new Vector();
                linhaMaterial.add(res.getInt("cod_lote"));
                linhaMaterial.add(res.getInt("total"));
                linhaRecebida.addElement(linhaMaterial);
            }
            conexao.close();
            boolean saidaOk = false;
            while (saidaOk == false) {
                if (soma + Integer.parseInt(((Vector) linhaRecebida.get(n)).get(1).toString()) >= qntAtendida) {
                    try {
                        sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida,observacao) " +
                                "VALUES (?,?,?,?,?)";
                        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
                        conexao.setAutoCommit(false);
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, saida.getCodigo());
                        ps.setInt(2, Integer.parseInt(((Vector) linhaRecebida.get(n)).get(0).toString()));
                        ps.setDouble(3, qntAtendida - soma);
                        Date dataUtil = new Date();
                        java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                        ps.setDate(4, data);
                        ps.setString(5, saida.getObservacao());
                        ps.executeUpdate();
                        conexao.commit();
                        soma = soma + (qntAtendida - soma);
                        materialDao.alterarEstoqueMaterial(codMaterial, qntAtendida);
                        if (qntEstoque - qntAtendida < estoqueMinimo) {
                            materialDao.MaterialAbaixo(codMaterial);
                        }
                    } catch (SQLException ex) {
                        throw new SQLException(ex.getCause());
                    }
                    saidaOk = true;
                } else {
                    try {
                        sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida,observacao) " +
                                "VALUES (?,?,?,?,?)";
                        conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
                        conexao.setAutoCommit(false);
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, saida.getCodigo());
                        ps.setInt(2, Integer.parseInt(((Vector) linhaRecebida.get(n)).get(0).toString()));
                        ps.setDouble(3, Double.parseDouble(((Vector) linhaRecebida.get(n)).get(1).toString()));
                        Date dataUtil = new Date();
                        java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                        ps.setDate(4, data);
                        ps.setString(5, saida.getObservacao());
                        ps.executeUpdate();
                        conexao.commit();
                        soma = soma + Double.parseDouble(((Vector) linhaRecebida.get(n)).get(1).toString());
                    } catch (SQLException ex) {
                        throw new SQLException(ex.getCause());
                    }
                    n++;
                }
            }
        }
    }

    public Vector<Saida> obterTodasSaidasEntre(Date dataInicial, Date dataFinal) throws SQLException {
        try {
            Date dataUtil = dataInicial;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataInicioSql = (java.sql.Date) dataUtil;

            dataUtil = dataFinal;
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataFinalSql = (java.sql.Date) dataUtil;

            this.conexao = FabricaConexao.obterConexao("JDBC", this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT d.cod_dsm, u.nome_usuario, ts.nome_tipo_saida,d.data_saida " +
                    "FROM dsm d, usuario u, tipo_saida ts " +
                    "WHERE d.cod_tipo_saida = ts.cod_tipo_saida AND " +
                    "u.cod_usuario = d.cod_usuario AND " +
                    " d.data_saida BETWEEN '" + dataInicioSql + "' AND '" + dataFinalSql + "'" +
                    " ORDER BY d.cod_dsm ASC";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Saida> saidas;
            saidas = new Vector<Saida>();
            while (res.next()) {
                Saida saida = new Saida();
                Usuario usuario = new Usuario();
                TipoSaida tipoSaida = new TipoSaida();
                saida.setCodigo(res.getInt("cod_dsm"));
                usuario.setNome(res.getString("nome_usuario"));
                tipoSaida.setNome(res.getString("nome_tipo_saida"));
                saida.setResponsavel(usuario);
                saida.setTipoSaida(tipoSaida);
                saida.setDataSaida(res.getDate("data_saida"));
                saidas.add(saida);
            }
            return saidas;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
