package dao;

import domain.Material;
import domain.Nd;
import domain.SubItem;
import domain.Unidade;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;

public class MaterialJDBCDao implements MaterialDao {

    Connection conexao = null;

    public void inserirMaterial(Material material) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO material (descricao_material, quant_minima, cod_nd , cod_subitem,cod_unidade,estado ) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, material.getDescricao());
            ps.setDouble(2, (material.getQntMinima()));
            ps.setInt(3, material.getNd().getCodigo());
            ps.setInt(4, material.getSubitem().getCodigo());
            ps.setInt(5, material.getUnidade().getCodigo());
            ps.setString(6, "a");
            ps.executeUpdate();
            conexao.commit();
            JOptionPane.showMessageDialog(null, "Material inserido com sucesso!!", "Material cadastrado", JOptionPane.INFORMATION_MESSAGE);
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarMaterial(Material material) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE material SET " +
                    " descricao_material = ?, " +
                    " quant_minima = ?, " +
                    " cod_nd = ?, " +
                    " cod_subitem = ?, " +
                    " cod_unidade = ? " +
                    " WHERE cod_material = " + material.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, ((material.getDescricao().toString())));
            ps.setDouble(2, (material.getQntMinima()));
            ps.setInt(3, (material.getNd().getCodigo()));
            ps.setInt(4, (material.getSubitem().getCodigo()));
            ps.setInt(5, material.getUnidade().getCodigo());
            ps.executeUpdate();
            conexao.commit();
            JOptionPane.showMessageDialog(null, "Material alterado com sucesso!!", "Material alterado", JOptionPane.INFORMATION_MESSAGE);
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Material> obterMateriais(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql = "";
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " +
                            " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'a' ORDER BY ma.cod_material";

                } else {
                    sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " +
                            " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'a' AND descricao_material LIKE '" + nome + "%' ORDER BY ma.cod_material";

                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " +
                            " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'i' ORDER BY ma.cod_material";

                } else {
                    sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " +
                            " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'i' AND descricao_material LIKE '" + nome + "%' ORDER BY ma.cod_material";

                }
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                Material material = new Material();
                material.setCodigo(res.getInt("cod_material"));
                material.setDescricao(res.getString("descricao_material"));
                material.setQntMinima(res.getDouble("quant_minima"));
                material.getNd().setCodigo(res.getInt("cod_nd"));
                material.getSubitem().setCodigo(res.getInt("cod_subitem"));
                material.getUnidade().setNome(res.getString("descricao_unidade"));
                materiais.addElement(material);
            }
            conexao.close();
            return materiais;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public void alterarStatusMaterial(Material m, String motivo, String acao, Vector r) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" +
                    "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "material");
            ps.setInt(2, m.getCodigo());
            ps.setInt(3, Integer.parseInt(r.get(0).toString()));
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE material SET " +
                        "estado = 'i' " +
                        "WHERE cod_material = " + m.getCodigo();
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE material SET " +
                        "estado = 'a' " +
                        "WHERE cod_material = " + m.getCodigo();
            }
            ps = conexao.prepareStatement(sql);
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

    public java.sql.Date converteDataUtilToSql(Date data) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataUtil = data;
        java.sql.Date dataSql = null;
        dataUtil = new java.sql.Date(dataUtil.getTime());
        dataSql = (java.sql.Date) dataUtil;
        return dataSql;
    }

    public Vector carregarLotesMaterial(int codMaterial) throws SQLException {
        conexao = FabricaConexao.obterConexao("JDBC");
        String sql;
        PreparedStatement ps;
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
        return linhaRecebida;
    }

    public void alterarEstoqueMaterial(int id, Double soma) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE material SET " +
                    " estoque = estoque - ? " +
                    " WHERE cod_material = " + id;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDouble(1, soma);
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

    public void MaterialAbaixo(int codMaterial) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO estoque_baixo (cod_material) values (?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codMaterial);
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

    public void alterarQuantidadeMaterial(Material material, Connection conexao) throws SQLException {
        try {
            String sql = "UPDATE material " + "SET estoque = estoque + ? " + "WHERE cod_material = ? ";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setDouble(1, (material.getEstoqueAtual()));
            ps.setInt(2, (material.getCodigo()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public boolean excluirMaterial(Connection conexao) throws SQLException {
        String sql;
        PreparedStatement ps;
        sql = "DELETE FROM material_estoque_baixo meb" +
                " USING material m" +
                "  WHERE meb.cod_material = m.cod_material AND" +
                "        m.estoque > m.quant_minima";
        try {
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void alterarStatusMaterial(int id, String motivo, String acao, Usuario usuario) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)" + "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "material");
            ps.setInt(2, id);
            ps.setInt(3, usuario.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE material SET " + "estado = 'i' " + "WHERE cod_material = " + id;
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE material SET " + "estado = 'a' " + "WHERE cod_material = " + id;
            }
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            JOptionPane.showMessageDialog(null, "Status do Material alterado!!", "Material alterado", JOptionPane.INFORMATION_MESSAGE);
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void darCiencia(Material material) throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            this.conexao.setAutoCommit(false);
            String sql = " UPDATE material_estoque_baixo " +
                    "SET ciente = 'c' " +
                    "WHERE cod_material = " + material.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            this.conexao.commit();
            this.conexao.close();
        } catch (SQLException ex) {
            this.conexao.rollback();
            this.conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Material> obterMateriaisAbaixoEstoque() throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT mae.cod_material AS codigo," +
                    " ma.descricao_material AS descricao, " +
                    " ma.quant_minima AS qnt_minima," +
                    " ma.estoque AS estoqueAtual " +
                    "FROM material_estoque_baixo mae," +
                    "     material ma " +
                    "WHERE mae.cod_material = ma.cod_material AND" +
                    "      mae.ciente != 'c' " +
                    "ORDER BY mae.cod_material";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                Material material = new Material();
                material.setCodigo(res.getInt("codigo"));
                material.setDescricao(res.getString("descricao"));
                material.setQntMinima(res.getDouble("qnt_minima"));
                material.setEstoqueAtual(res.getInt("estoqueAtual"));
                materiais.add(material);
            }
            this.conexao.close();
            return materiais;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public Vector<Material> obterMateriaisAtivos(String nome) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (nome.equals("")) {
                sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade,ma.estoque " + " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'a'";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade,ma.estoque " + " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'a' AND descricao_material LIKE '" + nome + "%'";
                ps = conexao.prepareStatement(sql);
            }
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                Material material = new Material();
                material.setCodigo(res.getInt("cod_material"));
                material.setDescricao(res.getString("descricao_material"));
                material.setQntMinima(res.getDouble("quant_minima"));
                Nd nd = new Nd();
                nd.setCodigo(res.getInt("cod_nd"));
                material.setNd(nd);
                SubItem s = new SubItem();
                s.setCodigo(res.getInt("cod_subitem"));
                material.setSubitem(s);
                Unidade u = new Unidade();
                u.setNome(res.getString("descricao_unidade"));
                material.setUnidade(u);
                material.setEstoqueAtual(res.getDouble("estoque"));
                materiais.addElement(material);
            }
            conexao.close();
            return materiais;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public Vector<Material> obterMateriaisInativos(String nome) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (nome.equals("")) {
                sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " + " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'i'";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT ma.cod_material, ma.descricao_material, ma.cod_nd, ma.quant_minima, ma.cod_subitem, un.descricao_unidade " + " FROM material ma, unidade un WHERE ma.cod_unidade = un.cod_unidade AND ma.estado = 'i' AND descricao_material LIKE '" + nome + "%'";
                ps = conexao.prepareStatement(sql);
            }
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                Material material = new Material();
                material.setCodigo(res.getInt("cod_material"));
                material.setDescricao(res.getString("descricao_material"));
                material.setQntMinima(res.getDouble("quant_minima"));
                material.getNd().setCodigo(res.getInt("cod_nd"));
                material.getSubitem().setCodigo(res.getInt("cod_subitem"));
                material.getUnidade().setNome(res.getString("descricao_unidade"));
                materiais.addElement(material);
            }
            conexao.close();
            return materiais;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public Vector<Material> obterMaterialAtivoCodigo(String codigo) throws SQLException {
        try {
            this.conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT ma.cod_material, ma.descricao_material,ma.estoque" + " FROM material ma WHERE ma.estado = 'a' AND ma.cod_material = " + codigo + "";
            ps = this.conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Material> materiais = new Vector<Material>();

            while (res.next()) {
                Material material = new Material();
                material.setCodigo(res.getInt("cod_material"));
                material.setDescricao(res.getString("descricao_material"));
                material.setEstoqueAtual(res.getInt("estoque"));
                materiais.addElement(material);
            }
            this.conexao.close();
            return materiais;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new SQLException();
        }
    }

    public void alterarMaterial(Material material, int Id) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE material SET " + " descricao_material = ?, " + " quant_minima = ?, " + " cod_nd = ?, " + " cod_subitem = ?, " + " cod_unidade = ? " + " WHERE cod_material = " + Id;
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, ((material.getDescricao().toString())));
            ps.setDouble(2, (material.getQntMinima()));
            ps.setInt(3, (material.getNd().getCodigo()));
            ps.setInt(4, (material.getSubitem().getCodigo()));
            ps.setInt(5, material.getUnidade().getCodigo());
            ps.executeUpdate();
            conexao.commit();
            JOptionPane.showMessageDialog(null, "Material alterado com sucesso!!", "Material alterado", JOptionPane.INFORMATION_MESSAGE);
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }
}