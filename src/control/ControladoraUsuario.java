package control;

import dao.UsuarioDao;
import dao.UsuarioJDBCDao;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControladoraUsuario implements ControladoraClasse {

    private Vector<Usuario> usuariosAtivos;
    private Vector<Usuario> usuariosInativos;
    private UsuarioDao usuarioDao;
    private int marcador;

    public int getMarcador() {
        return marcador;
    }

    public void setMarcador(int marcador) {
        this.marcador = marcador;
    }

    public ControladoraUsuario(String servidor) {
        this.usuarioDao = new UsuarioJDBCDao(servidor);
    }

    private Vector criarLinhaUsuario(Usuario usuario) {
        Vector linha = new Vector();
        linha.addElement(usuario.getCodigo());
        linha.addElement(usuario.getNome());
        linha.addElement(usuario.getCargo());
        linha.addElement(usuario.getSetor());
        linha.addElement(usuario.getLogin());
        linha.addElement(usuario.getSenha());
        linha.addElement(usuario.getDocumento());
        linha.addElement(usuario.getPermissao());
        return linha;
    }

    public Vector obterLinhas(String nome, String status) {
        Vector linhas = new Vector();
        try {
            if (status.equals("Ativo")) {
                usuariosAtivos = usuarioDao.obterUsuarios(nome, status);
                for (int i = 0; i < usuariosAtivos.size(); i++) {
                    Usuario usuario = usuariosAtivos.get(i);
                    linhas.addElement(this.criarLinhaUsuario(usuario));
                }
            } else {
                usuariosInativos = usuarioDao.obterUsuarios(nome, status);
                for (int i = 0; i < usuariosInativos.size(); i++) {
                    Usuario usuario = usuariosInativos.get(i);
                    linhas.addElement(this.criarLinhaUsuario(usuario));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return linhas;
    }

    private void atualizarUsuario(Usuario usuario, Vector linha) {
        usuario.setCodigo(Integer.parseInt(linha.get(0).toString()));
        usuario.setNome(linha.get(1).toString());
        usuario.getCargo().setCodigo(Integer.parseInt(linha.get(2).toString()));
        usuario.getSetor().setCodigo(Integer.parseInt(linha.get(3).toString()));
        usuario.setLogin(linha.get(4).toString());
        usuario.setSenha(linha.get(5).toString());
        usuario.setDocumento(linha.get(6).toString());
        usuario.setPermissao(linha.get(7).toString());
    }

    public void inserir(Vector linha, boolean chefia) {
        Usuario usuario = new Usuario();
        this.atualizarUsuario(usuario, linha);
        try {
            usuarioDao.inserirUsuario(usuario, chefia);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void alterar(Vector linha, boolean tornarFuncionario, boolean chefia) {
        //Usuario usuario = obterUsuarioId(id);
        Usuario usuario = new Usuario();
        // if (usuario != null) {
        this.atualizarUsuario(usuario, linha);
        try {
            usuarioDao.alterarUsuario(usuario, tornarFuncionario, chefia);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao) {
        Usuario usuario = new Usuario();
        atualizarUsuario(usuario, linha);
        Vector r = responsavel;
        try {
            usuarioDao.alterarStatusUsuario(usuario, motivo, r, acao);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean inserir(Vector linha) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean alterar(Vector linha, String status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector pesquisar(String nome, String status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Vector logar(String nome, String senha) {
        Vector linha = new Vector();
        try {
            Usuario u = usuarioDao.logar(nome, senha);
            if (u != null) {
                linha.addElement("" + u.getCodigo());
                linha.addElement("" + u.getNome());
                linha.addElement("" + u.getPermissao());
                linha.addElement("" + u.getDocumento());
                linha.addElement("" + u.getLogin());
                linha.addElement("" + u.getSenha());
                linha.addElement("" + u.getCargo().getCodigo());
                linha.addElement("" + u.getCargo().getNome());
                linha.addElement("" + u.getCargo().getStatus());
                linha.addElement("" + u.getCargo().isChefia());
                linha.addElement("" + u.getSetor().getCodigo());
                linha.addElement("" + u.getSetor().getNome());
                linha.addElement("" + u.getSetor().getDescricao());
            } else {
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" + "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return linha;

    }
}
