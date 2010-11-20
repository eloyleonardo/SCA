package dao;

import domain.Entrada;
import domain.Lote;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public interface DemDao {

    public void inserirDem(Entrada dem, Vector<Lote> lotes) throws SQLException;

    public Entrada obterCodigoUltimo() throws SQLException;

    public Vector<Entrada> obterTodosDemEntre(Date dataInicial, Date dataFinal) throws SQLException;
}
