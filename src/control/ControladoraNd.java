package control;

import dao.NdDao;
import dao.NdJDBCDao;
import domain.Nd;
import java.sql.SQLException;
import java.util.Vector;

public class ControladoraNd {

    private Vector<Nd> Nds;
    private NdDao nddao;

    public ControladoraNd() {
        this.nddao = new NdJDBCDao();
    }

    public Vector obterLinhasND() {
        try {
            Nds = nddao.obterNds();

        } catch (SQLException ex) {
        }
        Vector linha = new Vector();
        for (int i = 0; i < Nds.size(); i++) {
            Nd nd = Nds.get(i);
            linha.addElement(this.criarLinhaNd(nd));
        }
        return linha;
    }

    private Vector criarLinhaNd(Nd nd) {
        Vector linha = new Vector();
        linha.addElement(nd.getCodigo());
        linha.addElement(nd.getDescricao());
        return linha;
    }
}
