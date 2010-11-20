package dao;

import domain.Cidade;
import domain.Uf;
import java.sql.SQLException;
import java.util.Vector;

public interface CidadeDao {

    public void inserirCidade(Cidade cidade) throws SQLException;

    public void alterarCidade(Cidade cidade) throws SQLException;

    public Vector<Cidade> obterCidades(String nome, String status) throws SQLException;

    public void alterarStatusCidade(Cidade cidade, String motivo, Vector responsavel, String acao) throws SQLException;

    public Vector<Cidade> obterCidades(Uf uf) throws SQLException;
}
