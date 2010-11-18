package dao;

import domain.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class NdJDBCDao implements NdDao {

    Connection conexao = null;

    public Vector<Nd> obterNds() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
