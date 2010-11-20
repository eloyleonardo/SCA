package dao;

import domain.Saida;
import java.util.Vector;

public interface DsmDao {

    public void inserirDsm(Saida dsm);

    public Vector<Saida> obterDsms();
}
