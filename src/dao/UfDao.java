package dao;

import domain.Uf;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface UfDao {

    public void inserirUf(Uf uf) throws SQLException;

    public void alterarUf(Uf uf) throws SQLException;

    public Vector<Uf> obterUfs(String nome, String status) throws SQLException;

    public Uf obterUf(String nome) throws SQLException;

    public void alterarStatusUf(String id, String motivo, Usuario responsavel, String acao) throws SQLException;
}
