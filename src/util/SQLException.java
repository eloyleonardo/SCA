package util;

import javax.swing.JOptionPane;

public class SQLException extends java.sql.SQLException {

    String mensagem = "Erro de acesso a banco,\n por favor contate o Suporte !";
    String titulo = "Erro";

    public void exibirMensagem() {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }
}
