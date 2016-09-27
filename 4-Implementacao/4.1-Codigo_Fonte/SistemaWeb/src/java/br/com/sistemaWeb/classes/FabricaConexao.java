package br.com.sistemaWeb.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static final String USUARIO = "postgres";
    private static final String SENHA = "conejo74";
    private static final String URL = "jdbc:postgresql://localhost:5432/sistemaWeb";
    //**private static Connection conexao  = null;

    public static Connection conectar() throws SQLException {
        //**if (conexao == null) {
            DriverManager.registerDriver(new org.postgresql.Driver());
            //**conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        //**}

        return conexao;
    }

}
