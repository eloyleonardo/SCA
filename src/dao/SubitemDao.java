/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;
import domain.*;
import java.sql.SQLException;
import java.util.Vector;
import javax.net.ssl.SSLEngineResult.Status;

/**
 *
 * @author aluno-info7
 */
public interface SubitemDao {
    public Vector<SubItem> obterSubitens() throws SQLException;
}
