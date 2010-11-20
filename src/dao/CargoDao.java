package dao;

import domain.Cargo;
import domain.Usuario;
import java.sql.SQLException;
import java.util.Vector;

public interface CargoDao {

    public int inserirCargo(Cargo cargo) throws SQLException;

    public void alterarCargo(Cargo cargo) throws SQLException;

    public void alterarStatusCargo(int id, String motivo, Usuario responsavel, String acao) throws SQLException;

    public Vector<Cargo> obterCargos(String nome, String status) throws SQLException;
}
