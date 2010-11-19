package dao;

import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface UsuarioDao {

    public void inserirUsuario(Usuario usuario, boolean chefia) throws SQLException;

    public void alterarUsuario(Usuario usuario, boolean tornarFuncionario, boolean chefia) throws SQLException;

    public Vector<Usuario> obterUsuarios(String nome, String status) throws SQLException;

    public int obterUltimoUsuario() throws SQLException;

    public void alterarStatusUsuario(Usuario usuario, String motivo, Vector r, String acao) throws SQLException;

    public Usuario logar(String login, String senha) throws SQLException;
}
