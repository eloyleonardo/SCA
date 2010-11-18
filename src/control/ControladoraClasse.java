package control;

import java.util.Vector;

public interface ControladoraClasse {

    public boolean alterarStatus(String motivo, Vector responsavel, Vector linha, String acao);

    public boolean inserir(Vector linha);

    public boolean alterar(Vector linha, String status);

    public Vector pesquisar(String nome, String status);

    public Vector obterLinhas(String nome, String status);
}
