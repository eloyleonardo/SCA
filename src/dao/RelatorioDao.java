package dao;

import domain.Entrada;
import domain.Saida;
import java.sql.SQLException;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;

public interface RelatorioDao {

    public void getBoletim(Date dataInicio, Date dataFim) throws SQLException, JRException;

    public void getLog(String classe) throws SQLException, JRException;

    public void getRCM(Date dataI, Date dataF, int codigoSetor) throws SQLException, JRException;

    public void getRME() throws SQLException, JRException;

    public void getRMM(Date dataInicio, Date dataFim) throws SQLException, JRException;

    public void getDEM(Entrada dem) throws SQLException, JRException;

    public void getDsm(Saida saida) throws SQLException, JRException;
}
