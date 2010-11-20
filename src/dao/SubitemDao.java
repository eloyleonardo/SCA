package dao;
import domain.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.net.ssl.SSLEngineResult.Status;


public interface SubitemDao {
    public Vector<SubItem> obterSubitens() throws SQLException;
}

