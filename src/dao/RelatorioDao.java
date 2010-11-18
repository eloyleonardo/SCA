package dao;

import java.util.Date;

public interface RelatorioDao {

    public void getBoletim(Date dataInicio, Date dataFim);

    public void getLog(String classe);

    public void getRCM(Date dataI, Date dataF, int codigoSetor);

    public void getRME();

    public void getRMM(Date dataInicio, Date dataFim);
}
