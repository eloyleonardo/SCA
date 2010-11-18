package dao;

import domain.Fornecedor;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface FornecedorDao {

    public void inserirFornecedor(Fornecedor fornecedor) throws SQLException;

    public void alterarFornecedor(Fornecedor fornecedor) throws SQLException;

    public void alterarStatusFornecedor(String id, String motivo, Usuario responsavel, String acao) throws SQLException;

    public Vector<Fornecedor> obterFornecedores(String nome, String status) throws SQLException;
}
