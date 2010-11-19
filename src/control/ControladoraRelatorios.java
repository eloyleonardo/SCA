package control;

import dao.RelatorioDao;
import dao.RelatorioJDBCDao;
import domain.Entrada;
import domain.Saida;
import java.util.Date;

public class ControladoraRelatorios {

    private RelatorioDao dao;

    public ControladoraRelatorios() {
        this.dao = new RelatorioJDBCDao();
    }

    public void getBoletim(Date dataInicio, Date dataFim) {
        dao.getBoletim(dataInicio, dataFim);
    }

    public void getLog(String classe) {
        dao.getLog(classe);
    }

    public void getRCM(Date dataI, Date dataF, int codigoSetor) {
        dao.getRCM(dataI, dataF, codigoSetor);
    }

    public void getRME() {
        dao.getRME();
    }

    public void getRMM(Date dataInicio, Date dataFim) {
        dao.getRMM(dataInicio, dataFim);
    }

    public void getDsm(int codSaida) {
        Saida saida = new Saida();
        saida.setCodigo(codSaida);
        this.dao.getDsm(saida);
    }

    public void getDEM(int codigo) {
        Entrada dem = new Entrada();
        dem.setCodigo(codigo);
        this.dao.getDEM(dem);
    }
}
