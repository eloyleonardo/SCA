package dao;

import domain.Material;
import domain.Nd;
import domain.Setor;
import domain.Solicitacao;
import domain.SubItem;
import domain.Unidade;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class SolicitacaoJDBCDao implements SolicitacaoDao {

    private Connection conexao = null;
    private String servidor;

    public SolicitacaoJDBCDao(String servidor) {
        this.servidor = servidor;
    }

    public void inserirSolicitacao(Solicitacao solicitacao) throws SQLException {
        int numeroReq;
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO solicitacao (cod_usuario,data_solicitacao,cod_setor,estado) values (?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, solicitacao.getResponsavel().getCodigo());
            Date dataUtil = new Date();
            java.sql.Date data = new java.sql.Date(dataUtil.getTime());
            ps.setDate(2, data);
            ps.setInt(3, solicitacao.getResponsavel().getSetor().getCodigo());
            ps.setString(4, "aa");
            ps.executeUpdate();
            conexao.commit();
            numeroReq = obterNumRequisicao();
            for (int i = 0; i < solicitacao.getMateriais().size(); ++i) {
                sql = "INSERT INTO item_solicitacao(cod_solicitacao,cod_material,quantidade,quantidade_aprovada) VALUES(?,?,?,?)";
                ps = conexao.prepareStatement(sql);
                ps.setInt(1, numeroReq);
                ps.setInt(2, solicitacao.getMateriais().get(i).getCodigo());
                ps.setDouble(3, Double.valueOf(solicitacao.getQuantidade().get(i).toString()));
                ps.setInt(4, 0);
                ps.executeUpdate();
                conexao.commit();
            }
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException(ex.getCause());
        }
    }

    private int obterNumRequisicao() throws SQLException {
        int num = 0;
        String sql;
        PreparedStatement ps;
        sql = "select cod_solicitacao from solicitacao order by cod_solicitacao desc LIMIT 1";
        ps = conexao.prepareStatement(sql);
        ResultSet res = ps.executeQuery();
        conexao.commit();
        while (res.next()) {
            num = (res.getInt("cod_solicitacao"));
        }
        return num;
    }

    public Vector obterMateriaisPorSolicitacao(int cod) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT ma.cod_material, ma.descricao_material, its.quantidade_aprovada, estoque AS total, ma.quant_minima " +
                    "FROM material ma, " +
                    "item_solicitacao its " +
                    "WHERE ma.cod_material = its.cod_material AND " +
                    "its.quantidade_atendida is null AND " +
                    "its.cod_solicitacao = " + cod;
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector linhaInserida = new Vector();
            while (res.next()) {
                Vector linha = new Vector();
                linha.add(res.getInt("cod_material"));
                linha.add(res.getString("descricao_material"));
                linha.add(res.getInt("quantidade_aprovada"));
                linha.add(res.getDouble("total"));
                linha.add(res.getString("quant_minima"));
                linhaInserida.addElement(linha);
            }
            conexao.close();
            return linhaInserida;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }

    public Vector<Solicitacao> obteSolicitacoes(Setor setor) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT r.cod_solicitacao, " +
                    "r.cod_usuario, " +
                    "r.data_solicitacao, " +
                    "u.nome_usuario " +
                    "FROM solicitacao r, " +
                    "usuario u " +
                    "WHERE r.cod_usuario = u.cod_usuario AND " +
                    "r.cod_solicitacao != 0        AND " +
                    "u.cod_setor = " + setor.getCodigo() + " AND " +
                    "r.estado = 'aa' " +
                    "ORDER BY r.cod_solicitacao ASC;";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Solicitacao> solicitacoes = new Vector<Solicitacao>();

            while (res.next()) {
                Solicitacao s = new Solicitacao();
                s.setCodigo(res.getInt("cod_solicitacao"));
                s.setDataRequisicao(res.getDate("data_solicitacao"));
                Usuario u = new Usuario();
                u.setNome(res.getString("nome_usuario"));
                s.setResponsavel(u);
                ArrayList qnt = new ArrayList();
                s.setMaterial(obterMateriais(res.getInt("cod_solicitacao"), qnt));
                s.setQuantidade(qnt);
                solicitacoes.add(s);
            }
            conexao.close();
            return solicitacoes;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }

    private ArrayList<Material> obterMateriais(int id, ArrayList qnt) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            String sql;
            PreparedStatement ps;
            sql = "SELECT m.cod_material," +
                    "m.cod_subitem, " +
                    "m.cod_unidade, " +
                    "m.cod_nd, " +
                    "m.cod_unidade, " +
                    "u.descricao_unidade, " +
                    "m.descricao_material, " +
                    "ir.quantidade " +
                    "FROM material m, " +
                    "unidade u, " +
                    "solicitacao r, " +
                    "item_solicitacao ir " +
                    "WHERE m.cod_unidade = u.cod_unidade AND " +
                    "ir.cod_material = m.cod_material AND " +
                    "r.cod_solicitacao = ir.cod_solicitacao AND " +
                    "r.cod_solicitacao = " + id + ";";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            ArrayList<Material> materiais = new ArrayList<Material>();
            while (res.next()) {
                Material m = new Material();
                m.setCodigo(res.getInt("cod_material"));
                SubItem s = new SubItem();
                s.setCodigo(res.getInt("cod_subitem"));
                m.setSubitem(s);
                Unidade u = new Unidade();
                u.setCodigo(res.getInt("cod_unidade"));
                u.setNome(res.getString("descricao_unidade"));
                m.setUnidade(u);
                Nd n = new Nd();
                n.setCodigo(res.getInt("cod_nd"));
                m.setNd(n);
                m.setDescricao(res.getString("descricao_material"));
                materiais.add(m);
                qnt.add(res.getDouble("quantidade"));
            }
            conexao.close();
            return materiais;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }

    public void aprovarSolicitacao(Solicitacao solicitacao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            conexao.setAutoCommit(false);
            PreparedStatement ps;
            String sql = "UPDATE solicitacao " +
                    "SET estado = 'ap', " +
                    "data_aprovacao = '" + dataSql +
                    "' WHERE cod_solicitacao =" + solicitacao.getCodigo() + ";";
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();

            for (int i = 0; i < solicitacao.getMateriais().size(); i++) {
                sql = "UPDATE item_solicitacao " +
                        " SET quantidade_aprovada = " + solicitacao.getQuantidadeAprovada().get(i) +
                        " WHERE cod_solicitacao = " + solicitacao.getCodigo() + " AND " +
                        " cod_material = " + solicitacao.getMateriais().get(i).getCodigo();
                ps = conexao.prepareStatement(sql);
                ps.executeUpdate();
            }
            conexao.commit();
        } catch (SQLException ex) {
            conexao.rollback();
            throw new SQLException(ex.getCause());
        }
    }

    public void rejeitarSolicitacao(Solicitacao solicitacao) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            PreparedStatement ps;
            String sql = "UPDATE solicitacao " +
                    "SET estado = 'rj' " +
                    "WHERE cod_solicitacao =" + solicitacao.getCodigo() + ";";
            ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }

    public Vector<Solicitacao> obterSolicitacoesAprovadas(String nome) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC",this.servidor);
            String sql;
            PreparedStatement ps;
            if (nome.equals("")) {
                sql = "select so.cod_solicitacao, " +
                        " s.nome_setor, " +
                        "so.data_solicitacao, " +
                        "so.data_aprovacao, " +
                        "count(*) " +
                        "FROM solicitacao so, " +
                        "usuario u, " +
                        "setor s, " +
                        "item_solicitacao its " +
                        "WHERE so.cod_usuario = u.cod_usuario AND " +
                        "u.cod_setor = s.cod_setor AND " +
                        "so.cod_solicitacao = its.cod_solicitacao AND  " +
                        "so.estado = 'ap' AND " +
                        " its.quantidade_atendida is null " +
                        "GROUP BY   so.cod_solicitacao, s.nome_setor, so.data_solicitacao, so.data_aprovacao " +
                        "ORDER BY so.cod_solicitacao; ";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "select so.cod_solicitacao, " +
                        " s.nome_setor, " +
                        "so.data_solicitacao, " +
                        "so.data_aprovacao, " +
                        "count(*) " +
                        "FROM solicitacao so, " +
                        "usuario u, " +
                        "setor s, " +
                        "item_solicitacao its " +
                        "WHERE so.cod_usuario = u.cod_usuario AND " +
                        "u.cod_setor = s.cod_setor AND " +
                        "so.cod_solicitacao = its.cod_solicitacao AND  " +
                        "so.estado = 'ap' AND s.nome_setor = '" + nome + "' AND " +
                        "its.quantidade_atendida is null " +
                        " GROUP BY   so.cod_solicitacao, s.nome_setor, so.data_solicitacao, so.data_aprovacao " +
                        "ORDER BY so.cod_solicitacao; ";
                ps = conexao.prepareStatement(sql);
            }
            ResultSet res = ps.executeQuery();
            Vector<Solicitacao> solicitacoesAprovadas = new Vector<Solicitacao>();
            while (res.next()) {
                ArrayList qnt = new ArrayList();
                Solicitacao s = new Solicitacao();
                Usuario u = new Usuario();
                u.getSetor().setNome(res.getString("nome_setor"));
                s.setCodigo(res.getInt("cod_solicitacao"));
                s.setResponsavel(u);
                s.setDataRequisicao(res.getDate("data_solicitacao"));
                s.setDataAprovacao(res.getDate("data_aprovacao"));
                s.setQuantidade(qnt);
                solicitacoesAprovadas.addElement(s);
            }
            conexao.close();
            return solicitacoesAprovadas;
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
