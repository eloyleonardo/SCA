package control;

import dao.RelatorioDao;
import dao.RelatorioJDBCDao;
import domain.Entrada;
import domain.Saida;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;

public class ControladoraRelatorios {

    private RelatorioDao dao;

    public ControladoraRelatorios() {
        this.dao = new RelatorioJDBCDao();
    }

    public void getBoletim(Date dataInicio, Date dataFim) {
        try {
            dao.getBoletim(dataInicio, dataFim);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getLog(String classe) {
        try {
            dao.getLog(classe);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getRCM(Date dataI, Date dataF, int codigoSetor) {
        try {
            dao.getRCM(dataI, dataF, codigoSetor);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getRME() {
        try {
            dao.getRME();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getRMM(Date dataInicio, Date dataFim) {
        try {
            dao.getRMM(dataInicio, dataFim);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getDsm(int codSaida) {
        Saida saida = new Saida();
        saida.setCodigo(codSaida);
        try {
            this.dao.getDsm(saida);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getDEM(int codigo) {
        Entrada dem = new Entrada();
        dem.setCodigo(codigo);
        try {
            this.dao.getDEM(dem);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
