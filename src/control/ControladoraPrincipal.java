package control;

import boundary.FrmLogin;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ControladoraPrincipal {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.yesButtonText", "Sim");

//        System.setProperty("http.proxyHost", "172.16.48.1");
//        System.setProperty("http.proxyPort", "3128");
//        System.setProperty("java.net.socks.username", "alunp-info7");
//        System.setProperty("java.net.socks.password", "a12345!");


        JFrame janela = new FrmLogin();
        janela.setVisible(true);
    }
}
