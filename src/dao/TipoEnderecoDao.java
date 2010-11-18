package dao;

import domain.TipoEndereco;
import java.sql.SQLException;
import java.util.Vector;

public interface TipoEnderecoDao {

    public Vector<TipoEndereco> obterTiposEndereco() throws SQLException;
}
