package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FabricaConexao {

    public static Connection obterConexao(String tipo) throws SQLException {
        try {
            if (tipo.equals("JDBC")) {
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "Driver JDBC-ODBC não encontrado");
                }
                Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sca", "postgres", "Postgre2010");
                return conexao;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
