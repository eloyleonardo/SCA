package dao;

import domain.Setor;
import domain.Solicitacao;
import java.sql.SQLException;
import java.util.Vector;

public interface SolicitacaoDao {

    public void inserirSolicitacao(Solicitacao solicitacao) throws SQLException;

    public void aprovarSolicitacao(Solicitacao solicitacao) throws SQLException;

    public void rejeitarSolicitacao(Solicitacao solicitacao) throws SQLException;

    public Vector<Solicitacao> obteSolicitacoes(Setor setor) throws SQLException;

    public Vector obterMateriaisPorSolicitacao(int cod) throws SQLException;

    public Vector<Solicitacao> obterSolicitacoesAprovadas(String nome) throws SQLException;
}
