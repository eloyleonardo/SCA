package dao;

import domain.Saida;
import java.sql.SQLException;

public interface SaidaDao {

    public void inserirSaida(int codUsuario, int codSaida, int codSolicitacao) throws SQLException;

    public int obterUltimaDsm() throws SQLException;

    public void registrarSaida(Saida saida, int numLinhas) throws SQLException;

    public void registrarSaidaOutrosMotivos(Saida saida) throws SQLException;
}
