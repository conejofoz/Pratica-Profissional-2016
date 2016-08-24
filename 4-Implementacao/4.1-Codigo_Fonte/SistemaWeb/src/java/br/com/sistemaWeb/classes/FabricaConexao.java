package br.com.sistemaWeb.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    private static final String USUARIO = "postgres";
    private static final String SENHA = "conejo74";
    private static final String URL = "jdbc:postgresql://localhost:5432/sistemaWeb";
    //**private static Connection conexao  = null;

    
    private static final String USUARIOF = "SYSDBA";
    private static final String SENHAF = "masterkey";
    private static final String URLF = "jdbc:firebirdsql:192.168.0.100/3050:C:/guri/dados/inf.pbr";
    
    //jdbc:firebirdsql:localhost/3050:C:/firebird_dados/horas/horas.gdb
    
    
    
    public static Connection conectar() throws SQLException {
        //**if (conexao == null) {
            DriverManager.registerDriver(new org.postgresql.Driver());
            //**conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        //**}

        return conexao;
    }
    
    
     public static Connection conectarF() throws SQLException {
        //**if (conexao == null) {
            DriverManager.registerDriver(new org.firebirdsql.jdbc.FBDriver());
            //**conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            Connection conexaoF = DriverManager.getConnection(URLF, USUARIOF, SENHAF);
        //**}

        return conexaoF;
    }

}
