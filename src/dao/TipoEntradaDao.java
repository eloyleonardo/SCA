package dao;

import domain.TipoEntrada;
import java.sql.SQLException;
import java.util.Vector;

public interface TipoEntradaDao {

    public Vector<TipoEntrada> obterTipoEntrada() throws SQLException;
}
