package dao;

import domain.Setor;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface SetorDao {

    public int inserirSetor(Setor setor) throws SQLException;

    public void alterarSetor(Setor setor) throws SQLException;

    public void alterarStatusSetor(int id, String motivo, Usuario responsavel, String acao) throws SQLException;

    public Vector<Setor> obterSetores(String nome, String status) throws SQLException;
}
