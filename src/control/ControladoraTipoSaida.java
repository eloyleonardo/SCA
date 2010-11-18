package control;

import dao.TipoSaidaDao;
import dao.TipoSaidaJDBCDao;
import domain.TipoSaida;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControladoraTipoSaida {

    private TipoSaidaDao tipoSaidaDao;
    private Vector<TipoSaida> tiposSaida;

    public ControladoraTipoSaida() {
        this.tipoSaidaDao = new TipoSaidaJDBCDao();
    }

    public Vector<TipoSaida> obterTiposSaida() throws SQLException {
        tiposSaida = tipoSaidaDao.obterTiposSaida();
        return tiposSaida;
    }

    public Vector obterLinhasTipoSaida() {
        Vector<TipoSaida> m = new Vector<TipoSaida>();
        try {
            m = obterTiposSaida();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao acesso o banco de dados,\n" +
                    "por favor contate o Suporte", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        Vector linhas = new Vector();
        for (int i = 0; i < m.size(); i++) {
            TipoSaida tipoSaida = m.get(i);
            linhas.addElement(this.criarLinhaTipoSaida(tipoSaida));
        }
        return linhas;
    }

    protected Vector criarLinhaTipoSaida(TipoSaida tipoSaida) {
        Vector linha = new Vector();
        linha.addElement(tipoSaida.getCodigo());
        linha.addElement(tipoSaida.getNome());
        return linha;
    }
}
