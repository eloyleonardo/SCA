package control;

import dao.SubitemDao;
import dao.SubitemJDBCDao;
import domain.SubItem;
import java.sql.SQLException;
import java.util.Vector;

public class ControladoraSubitem {

    private Vector<SubItem> subitens;
    private SubitemDao subitemdao;

    public ControladoraSubitem(String servidor) {
        this.subitemdao = new SubitemJDBCDao(servidor);
    }

    public Vector obterLinhasSubitem() {
        try {
            subitens = subitemdao.obterSubitens();

        } catch (SQLException ex) {
        }
        Vector linha = new Vector();
        for (int i = 0; i < subitens.size(); i++) {
            SubItem subitem = subitens.get(i);
            linha.addElement(this.criarLinhaSubitem(subitem));
        }
        return linha;
    }

    private Vector criarLinhaSubitem(SubItem subitem) {
        Vector linha = new Vector();
        linha.addElement(subitem.getCodigo());
        linha.addElement(subitem.getDescricao());
        return linha;
    }
}
