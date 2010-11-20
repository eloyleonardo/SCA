package dao;

import domain.Unidade;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface UnidadeDao {

    public int inserirUnidade(Unidade unidade) throws SQLException;

    public void alterarUnidade(Unidade unidade) throws SQLException;

    public void alterarStatusUnidade(int id, String motivo, Usuario responsavel, String acao) throws SQLException;

    public Vector<Unidade> obterUnidades(String nome, String status) throws SQLException;
}
