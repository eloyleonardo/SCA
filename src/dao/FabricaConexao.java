package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FabricaConexao {

    public static Connection obterConexao(String tipo, String servidor) throws SQLException {
        if (servidor.equals("")) {
            servidor = "localhost";
        }
        try {
            if (tipo.equals("JDBC")) {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Driver JDBC-ODBC não encontrado");
                }
                Connection conexao = DriverManager.getConnection("jdbc:postgresql://"+servidor+":5432/sca", "sca", "Postgre2010");
                return conexao;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getCause());
        }
    }
}
