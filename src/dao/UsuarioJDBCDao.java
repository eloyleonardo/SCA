package dao;

import domain.Cargo;
import domain.Setor;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

public class UsuarioJDBCDao implements UsuarioDao {

    Connection conexao = null;
    int aux;

    public void inserirUsuario(Usuario usuario, boolean chefia) throws SQLException {
        //ControladoraSetor controladoraSetor = new ControladoraSetor();
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO usuario (nome_usuario,cod_cargo,cod_setor,login_usuario,senha,documento_usuario,estado,permissao) "
                    + "values (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setInt(2, (usuario.getCargo().getCodigo()));
            ps.setInt(3, (usuario.getSetor().getCodigo()));
            ps.setString(4, usuario.getLogin());
            ps.setString(5, usuario.getSenha());
            ps.setString(6, usuario.getDocumento());
            ps.setString(7, "a");
            ps.setString(8, usuario.getPermissao());
            ps.executeUpdate();
            conexao.commit();
            if (chefia == true) {
                int numUsuario = this.obterUltimoUsuario();
                usuario.setCodigo(numUsuario);
                this.inserirResponsavel(usuario);
            }
            conexao.close();
            JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso!!", "Usuário cadastrado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public void alterarUsuario(Usuario usuario, boolean tornarFuncionario, boolean chefia) throws SQLException {
        if (tornarFuncionario == true) {
            this.tornarFuncionario(usuario);
        }
        if (chefia == true) {
            this.inserirResponsavel(usuario);
        }
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = " UPDATE usuario SET "
                    + " nome_usuario = ?, "
                    + " cod_cargo = ?, "
                    + " cod_setor = ?,"
                    + " login_usuario = ?,"
                    + " senha = ?,"
                    + " documento_usuario = ?,"
                    + " permissao = ?"
                    + " WHERE cod_usuario = " + usuario.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setInt(2, (usuario.getCargo().getCodigo()));
            ps.setInt(3, (usuario.getSetor().getCodigo()));
            ps.setString(4, usuario.getLogin());
            ps.setString(5, usuario.getSenha());
            ps.setString(6, usuario.getDocumento());
            ps.setString(7, usuario.getPermissao());
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
            JOptionPane.showMessageDialog(null, "Usuário editado com sucesso!!", "Usuário editado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            conexao.rollback();
            conexao.close();
            throw new SQLException();
        }
    }

    public Vector<Usuario> obterUsuarios(String nome, String status) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            if (status.equals("Ativo")) {
                if (nome.equals("")) {
                    sql = "SELECT u.cod_usuario, u.nome_usuario, c.nome_cargo, s.nome_setor, u.login_usuario, u.senha, u.documento_usuario,u.permissao "
                            + "FROM usuario u, setor s, cargo c "
                            + "WHERE u.cod_setor = s.cod_setor AND u.cod_cargo = c.cod_cargo AND u.estado = 'a' ORDER BY u.cod_usuario";
                    ps = conexao.prepareStatement(sql);
                    //ps.setString(1, "1");
                } else {
                    sql = "SELECT u.cod_usuario, u.nome_usuario, c.nome_cargo, s.nome_setor, u.login_usuario, u.senha, u.documento_usuario,u.permissao "
                            + "FROM usuario u, setor s, cargo c "
                            + "WHERE u.cod_setor = s.cod_setor AND u.cod_cargo = c.cod_cargo AND u.estado = 'a' AND nome_usuario LIKE '" + nome + "%' ORDER BY u.cod_usuario";

                    ps = conexao.prepareStatement(sql);
                    //ps.setString(1, nome);
                }
            } else {
                if (nome.equals("")) {
                    sql = "SELECT u.cod_usuario, u.nome_usuario, c.nome_cargo, s.nome_setor, u.login_usuario, u.senha, u.documento_usuario,u.permissao "
                            + "FROM usuario u, setor s, cargo c "
                            + "WHERE u.cod_setor = s.cod_setor AND u.cod_cargo = c.cod_cargo AND u.estado = 'i' ORDER BY u.cod_usuario";
                    ps = conexao.prepareStatement(sql);
                    //ps.setString(1, "1");
                } else {
                    sql = "SELECT u.cod_usuario, u.nome_usuario, c.nome_cargo, s.nome_setor, u.login_usuario, u.senha, u.documento_usuario,u.permissao "
                            + "FROM usuario u, setor s, cargo c "
                            + "WHERE u.cod_setor = s.cod_setor AND u.cod_cargo = c.cod_cargo AND u.estado = 'i' AND nome_usuario LIKE '" + nome + "%' ORDER BY u.cod_usuario";
                    ps = conexao.prepareStatement(sql);
                    //ps.setString(1, nome);
                }

            }
            ResultSet res = ps.executeQuery();
            Vector<Usuario> usuarios = new Vector<Usuario>();

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setCodigo(res.getInt("cod_usuario"));
                usuario.setNome(res.getString("nome_usuario"));
                usuario.getCargo().setNome(res.getString("nome_cargo"));
                usuario.getSetor().setNome(res.getString("nome_setor"));
                usuario.setLogin(res.getString("login_usuario"));
                usuario.setSenha((res.getString("senha")));
                usuario.setDocumento(res.getString("documento_usuario"));
                usuario.setPermissao(res.getString("permissao"));
                usuarios.addElement(usuario);
            }
            conexao.close();
            return usuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public int obterUltimoUsuario() throws SQLException {
        int numUsuario = 0;
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT cod_usuario FROM usuario ORDER BY cod_usuario desc LIMIT 1";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                numUsuario = res.getInt("cod_usuario");
            }
            conexao.close();
            return numUsuario;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    private void inserirResponsavel(Usuario usuario) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            sql = "INSERT INTO responsavel(cod_usuario,cod_setor) VALUES (?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, usuario.getCodigo());
            ps.setInt(2, (usuario.getSetor().getCodigo()));
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    private void tornarFuncionario(Usuario usuario) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            sql = "DELETE FROM responsavel WHERE cod_usuario = " + usuario.getCodigo();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.executeUpdate();
            conexao.commit();
            conexao.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    public void alterarStatusUsuario(Usuario usuario, String motivo, Vector responsavel, String acao) throws SQLException {
        try {
            Date dataUtil = new Date();
            dataUtil = new java.sql.Date(dataUtil.getTime());
            java.sql.Date dataSql = (java.sql.Date) dataUtil;
            conexao = FabricaConexao.obterConexao("JDBC");
            conexao.setAutoCommit(false);
            String sql = "INSERT INTO log_atividade (tabela_modificada,elemento_modificado,usuario,data_modificacao,motivo,acao)"
                    + "VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, "cidade");
            ps.setInt(2, usuario.getCodigo());
            ps.setInt(3, Integer.parseInt(responsavel.get(0).toString()));
            ps.setDate(4, dataSql);
            ps.setString(5, motivo);
            if (acao.equals("Desativar")) {
                ps.setString(6, "d");
                ps.executeUpdate();
                sql = "UPDATE usuario SET "
                        + "estado = 'i' "
                        + "WHERE cod_usuario = " + usuario.getCodigo();
            } else {
                ps.setString(6, "a");
                ps.executeUpdate();
                sql = "UPDATE usuario SET "
                        + "estado = 'a' "
                        + "WHERE cod_usuario = " + usuario.getCodigo();
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

    public Usuario logar(String login, String senha) throws SQLException {
        try {
            conexao = FabricaConexao.obterConexao("JDBC");
            String sql;
            PreparedStatement ps;
            sql = "SELECT u.cod_usuario, "
                    + " u.cod_setor, "
                    + " u.cod_cargo, "
                    + " u.nome_usuario, "
                    + " u.login_usuario, "
                    + " u.senha, "
                    + " u.documento_usuario, "
                    + " u.estado, "
                    + " u.permissao, "
                    + " c.cod_cargo, "
                    + " c.nome_cargo, "
                    + " c.chefia, "
                    + " c.estado, "
                    + " s.cod_setor, "
                    + " s.nome_setor, "
                    + " s.estado "
                    + " FROM usuario u, "
                    + " cargo c, "
                    + " setor s "
                    + " WHERE u.cod_cargo = c.cod_cargo            AND "
                    + "u.cod_setor = s.cod_setor            AND "
                    + "u.cod_usuario != 0                   AND "
                    + "u.estado = 'a'                       AND "
                    + "u.login_usuario = '" + login + "'    AND "
                    + "u.senha = '" + senha + "'            AND "
                    + "u.estado = 'a' "
                    + "LIMIT 1";
            ps = conexao.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            Usuario u = new Usuario();
            if (res.next()) {
                u.setCodigo(res.getInt("cod_usuario"));
                u.setDocumento(res.getString("documento_usuario"));
                u.setLogin(res.getString("login_usuario"));
                u.setSenha(res.getString("senha"));
                u.setNome(res.getString("nome_usuario"));
                u.setPermissao(res.getString("permissao"));
                Cargo c = new Cargo();
                c.setCodigo(res.getInt("cod_cargo"));
                c.setNome(res.getString("nome_cargo"));
                c.setStatus(res.getString("estado"));
                if (res.getString("chefia").equals("s")) {
                    c.setChefia(true);
                } else {
                    c.setChefia(false);
                }
                u.setCargo(c);
                Setor s = new Setor();
                s.setCodigo(res.getInt("cod_setor"));
                s.setNome(res.getString("nome_setor"));
                u.setSetor(s);
            }
            conexao.close();
            return u;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
