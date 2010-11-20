package dao;

import domain.TipoSaida;
import java.sql.SQLException;
import java.util.Vector;

public interface TipoSaidaDao {

    public Vector<TipoSaida> obterTiposSaida() throws SQLException;
}
