package dao;

import domain.Entrada;
import domain.Lote;
import domain.Saida;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public interface LoteDao {

    public void inserirLotes(Vector<Lote> lotes, Connection conexao) throws SQLException;

    public Vector<Lote> obterLotesDem(Entrada dem) throws SQLException;

    public Vector<Lote> obterLotesSaida(Saida saida) throws SQLException;
}
