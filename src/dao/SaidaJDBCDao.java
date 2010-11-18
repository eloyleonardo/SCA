package dao;

import domain.Saida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class SaidaJDBCDao implements SaidaDao {

    Connection conexao = null;

    public void inserirSaida(int codUsuario, int codSaida, int codSolicitacao) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
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
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public int obterUltimaDsm() throws SQLException {
        int num = 0;
        String sql;
        PreparedStatement ps;
        conexao = FabricaConexao.obterConexao("JDBC");
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

    public void registrarSaida(Saida saida, int numLinhas) throws SQLException {
        MaterialDao materialDao = new MaterialJDBCDao();
        String qntAtendida = saida.getQuantidadeAtendida().toString().substring(1, saida.getQuantidadeAtendida().toString().length() - 1);
        double soma = 0;
        int n = 0;
        conexao = FabricaConexao.obterConexao("JDBC");
        String sql;
        PreparedStatement ps;
        Vector linhaRecebida = materialDao.carregarLotesMaterial(saida.getMaterial().get(0).getCodigo());
        boolean saidaOk = false;
        while (saidaOk == false) {
            if (soma + Integer.parseInt(((Vector) linhaRecebida.get(n)).get(1).toString()) >= saida.getQuantidadeAtendida().get(0)) {
                try {
                    sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida) " +
                            "VALUES (?,?,?,?)";
                    conexao = FabricaConexao.obterConexao("JDBC");
                    conexao.setAutoCommit(false);
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, saida.getCodigo());
                    ps.setInt(2, Integer.parseInt(((Vector) linhaRecebida.get(n)).get(0).toString()));
                    ps.setDouble(3, saida.getQuantidadeAtendida().get(0) - soma);
                    Date dataUtil = new Date();
                    java.sql.Date data = new java.sql.Date(dataUtil.getTime());
                    ps.setDate(4, data);
                    ps.executeUpdate();
                    conexao.commit();
                    soma = soma + (saida.getQuantidadeAtendida().get(0) - soma);
                    materialDao.alterarEstoqueMaterial(saida.getMaterial().get(0).getCodigo(), saida.getQuantidadeAtendida().get(0));
                    if (saida.getMaterial().get(0).getEstoqueAtual() - saida.getQuantidadeAtendida().get(0) < saida.getMaterial().get(0).getQntMinima()) {
                        materialDao.MaterialAbaixo(saida.getMaterial().get(0).getCodigo());
                    }
                    if (numLinhas == 1) {
                        sql = "UPDATE solicitacao SET estado = 'sa' WHERE cod_solicitacao = " + saida.getRequisicao().getCodigo();
                        ps = conexao.prepareStatement(sql);
                        ps.executeUpdate();
                        conexao.commit();
                        conexao.close();
                    }
                    JOptionPane.showMessageDialog(null, "Saída Registrada!!", "Saída Registrada!!", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new SQLException();
                }
                saidaOk = true;
            } else {
                try {
                    sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida) " +
                            "VALUES (?,?,?,?)";
                    conexao = FabricaConexao.obterConexao("JDBC");
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
                    ex.printStackTrace();
                    throw new SQLException();
                }
                n++;
            }
        }
        try {
            sql = "UPDATE item_solicitacao SET quantidade_atendida = " + Double.parseDouble(qntAtendida) +
                    " WHERE cod_solicitacao = " + saida.getRequisicao().getCodigo() +
                    " AND cod_material = " + saida.getMaterial().get(0).getCodigo();
            conexao = FabricaConexao.obterConexao("JDBC");
            // conexao.setAutoCommit(false);
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public void registrarSaidaOutrosMotivos(Saida saida) throws SQLException {
        //Vector linhaSaida = linha;
        MaterialDao materialDao = new MaterialJDBCDao();
        conexao = FabricaConexao.obterConexao("JDBC");
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
                        conexao = FabricaConexao.obterConexao("JDBC");
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
                        ex.printStackTrace();
                        throw new SQLException();
                    }
                    saidaOk = true;
                } else {
                    try {
                        sql = "INSERT INTO saida_material(cod_dsm,cod_lote,qnt_saida,data_saida,observacao) " +
                                "VALUES (?,?,?,?,?)";
                        conexao = FabricaConexao.obterConexao("JDBC");
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
                        ex.printStackTrace();
                        throw new SQLException();
                    }
                    n++;
                }
            }
        }
    }
}
