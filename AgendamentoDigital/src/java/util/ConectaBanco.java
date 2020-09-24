package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import modelos.Pessoa;

public class ConectaBanco {

    public static Connection getConexao() {
        Connection conexao = null;
       
        try {
            //driver que será utilizado
            Class.forName("org.postgresql.Driver");
            //cria um objeto de conexao com um banco especificado no caminho...
            conexao = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/agendamentoDigital", "postgres", "postgres");
        } catch (ClassNotFoundException | SQLException erro1) {
            throw new RuntimeException(erro1);
        }
        return conexao;
    }

    public String begin(Connection conexao) {
        
        String sqlReturnCode = "0";

        try {
            PreparedStatement pstmt = conexao.prepareStatement("BEGIN;");
            pstmt.execute();
            return sqlReturnCode;
        } catch (SQLException sqlErro) {
            sqlReturnCode = sqlErro.getSQLState();
            return sqlReturnCode;
        }
    }

    public String commit() {

        Connection conexao = getConexao();
        String sqlReturnCode = "0";

        try {
            PreparedStatement pstmt = conexao.prepareStatement("COMMIT;");
            pstmt.execute();
            return sqlReturnCode;
        } catch (SQLException sqlErro) {
            sqlReturnCode = sqlErro.getSQLState();
            return sqlReturnCode;
        }
    }

    public String rollback() {

        Connection conexao = getConexao();
        String sqlReturnCode = "0";

        try {
            PreparedStatement pstmt = conexao.prepareStatement("ROLLBACK;");
            pstmt.execute();
            return sqlReturnCode;
        } catch (SQLException sqlErro) {
            sqlReturnCode = sqlErro.getSQLState();
            return sqlReturnCode;
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
