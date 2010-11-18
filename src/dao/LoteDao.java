package dao;

import domain.Lote;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public interface LoteDao {

    public void inserirLotes(Vector<Lote> lotes, Connection conexao) throws SQLException;
}
