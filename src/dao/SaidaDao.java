package dao;

import domain.Saida;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

public interface SaidaDao {

    public void inserirSaida(int codUsuario, int codSaida, int codSolicitacao) throws SQLException;

    public int obterUltimaDsm() throws SQLException;

    public void registrarSaida(Saida saida) throws SQLException;

    public void registrarSaidaOutrosMotivos(Saida saida) throws SQLException;

    public Vector<Saida> obterTodasSaidasEntre(Date dataInicial, Date dataFinal) throws SQLException;
}
