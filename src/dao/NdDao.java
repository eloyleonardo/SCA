package dao;

import domain.*;
import java.sql.SQLException;
import java.util.Vector;

public interface NdDao {

    public Vector<Nd> obterNds() throws SQLException;
}
