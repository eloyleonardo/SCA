package dao;

import domain.Cidade;
import domain.Fornecedor;
import domain.TipoEndereco;
import domain.Uf;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Leonardo
 */
public class FornecedorJDBCDao implements FornecedorDao {

    private Connection conexao;

    public void inserirFornecedor(Fornecedor fornecedor) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO fornecedor (id_fornecedor,cod_cidade,cod_tipo_endereco,nome_fornecedor,nome_fantasia,endereco,complemento,bairro,estado,telefone1,telefone2,fax,email,cep)"
                    + "VALUES ('" + fornecedor.getId() + "'," + fornecedor.getCidade().getCodigo() + "," + fornecedor.getTipoEndereco().getCodigo() + ",'" + fornecedor.getNome() + "','" + fornecedor.getNomeFantasia() + "','" + fornecedor.getEndereco() + "','" + fornecedor.getComplemento() + "','" + fornecedor.getBairro() + "','a','" + fornecedor.getTelefone1() + "','" + fornecedor.getTelefone2() + "','" + fornecedor.getFax() + "','" + fornecedor.getEmail() + "','" + fornecedor.getCep() + "');";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarFornecedor(Fornecedor fornecedor) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "UPDATE fornecedor SET "
                    + "id_fornecedor     = '" + fornecedor.getId() + "',"
                    + "cod_cidade        = " + fornecedor.getCidade().getCodigo() + ","
                    + "cod_tipo_endereco = " + fornecedor.getTipoEndereco().getCodigo() + ","
                    + "nome_fornecedor   = '" + fornecedor.getNome() + "',"
                    + "nome_fantasia     = '" + fornecedor.getNomeFantasia() + "',"
                    + "endereco          = '" + fornecedor.getEndereco() + "',"
                    + "complemento       = '" + fornecedor.getComplemento() + "',"
                    + "bairro            = '" + fornecedor.getBairro() + "',"
                    + "estado            = '" + fornecedor.getStatus() + "',"
                    + "telefone1         = '" + fornecedor.getTelefone1() + "',"
                    + "telefone2         = '" + fornecedor.getTelefone2() + "',"
                    + "fax               = '" + fornecedor.getFax() + "',"
                    + "email             = '" + fornecedor.getEmail() + "',"
                    + "cep               = '" + fornecedor.getCep() + "'"
                    + "WHERE id_fornecedor = '" + fornecedor.getId() + "';";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarStatusFornecedor(String id, String motivo, Usuario responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,cod_usuario,data_modificacao,motivo,acao)"
                    + "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "fornecedor");
            ps.setString(2, id);
            ps.setInt(3, responsavel.getCodigo());
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE fornecedor SET "
                        + "estado = 'i' "
                        + "WHERE id_fornecedor = '" + id + "'";
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE fornecedor SET "
                        + "estado = 'a' "
                        + "WHERE id_fornecedor = '" + id + "'";
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

    public Vector<Fornecedor> obterFornecedores(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                status = "a";
            } else {
                status = "i";
            }
            if (nome.equals("")) {
                sql = "SELECT f.id_fornecedor, "
                        + "f.cod_cidade, "
                        + "c.nome_cidade, "
                        + "c.sigla_uf, "
                        + "u.descricao_uf, "
                        + "f.cod_tipo_endereco, "
                        + "te.nome_tipo_endereco, "
                        + "f.nome_fornecedor, "
                        + "f.telefone1, "
                        + "f.telefone2, "
                        + "f.nome_fantasia, "
                        + "f.endereco, "
                        + "f.complemento, "
                        + "f.bairro, "
                        + "f.fax, "
                        + "f.email, "
                        + "f.estado, "
                        + "f.cep "
                        + "FROM fornecedor f, "
                        + "cidade c, "
                        + "uf u, "
                        + "tipo_endereco te "
                        + "WHERE f.cod_cidade = c.cod_cidade AND "
                        + "c.sigla_uf = u.sigla_uf     AND "
                        + "f.cod_tipo_endereco = te.cod_tipo_endereco AND "
                        + "f.estado = '" + status + "' "
                        + "ORDER BY f.id_fornecedor;";
            } else {
                sql = "SELECT f.id_fornecedor,"
                        + "f.cod_cidade,"
                        + "c.nome_cidade,"
                        + "c.sigla_uf,"
                        + "u.descricao_uf,"
                        + "f.cod_tipo_endereco,"
                        + "te.nome_tipo_endereco,"
                        + "f.nome_fornecedor,"
                        + "f.telefone1,"
                        + "f.telefone2,"
                        + "f.nome_fantasia,"
                        + "f.endereco,"
                        + "f.complemento,"
                        + "f.bairro,"
                        + "f.fax,"
                        + "f.email,"
                        + "f.estado, "
                        + "f.cep "
                        + "FROM fornecedor f,"
                        + "cidade c,"
                        + "uf u,"
                        + "tipo_endereco te"
                        + "WHERE f.cod_cidade = c.cod_cidade AND"
                        + "c.sigla_uf = u.sigla_uf     AND"
                        + "f.cod_tipo_endereco = te.cod_tipo_endereco AND"
                        + "f.nome_fantasia LIKE '" + nome + "%' AND "
                        + "f.estado = '" + status + "' "
                        + "ORDER BY f.id_fornecedor;";
            }
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Vector<Fornecedor> fornecedores = new Vector<Fornecedor>();

            while (res.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(res.getString("id_fornecedor"));
                Uf u = new Uf();
                u.setSigla(res.getString("sigla_uf"));
                u.setDescricao(res.getString("descricao_uf"));
                Cidade c = new Cidade();
                c.setCodigo(res.getInt("cod_cidade"));
                c.setNome(res.getString("nome_cidade"));
                c.setUf(u);
                f.setCidade(c);
                TipoEndereco te = new TipoEndereco();
                te.setCodigo(res.getInt("cod_tipo_endereco"));
                te.setNome(res.getString("nome_tipo_endereco"));
                f.setTipoEndereco(te);
                f.setNome(res.getString("nome_fornecedor"));
                f.setNomeFantasia(res.getString("nome_fantasia"));
                f.setTelefone1(res.getString("telefone1"));
                f.setTelefone2(res.getString("telefone2"));
                f.setEndereco(res.getString("endereco"));
                f.setComplemento(res.getString("complemento"));
                f.setBairro(res.getString("bairro"));
                f.setFax(res.getString("fax"));
                f.setEmail(res.getString("email"));
                f.setStatus(res.getString("estado"));
                f.setCep(res.getString("cep"));
                fornecedores.addElement(f);
            }
            conexao.close();
            return fornecedores;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
