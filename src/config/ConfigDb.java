package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDb {
    // Configuración de Supabase
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:6543/postgres?sslmode=require";
    private static final String USER = "postgres.meaqmuvrwcdxmrywswdr";
    private static final String PASSWORD = "Qwe.123*";

    public static Connection openConnection() {
        Connection conn = null;

        try {
            // Cargar el driver
            Class.forName("org.postgresql.Driver");

            // Conectar a la base de datos
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("✅ Conexión establecida con Supabase");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC de PostgreSQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }

        return conn;
    }

}