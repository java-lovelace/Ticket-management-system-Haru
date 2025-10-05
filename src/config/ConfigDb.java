package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDb {
    // Configuración de Supabase
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

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

    public static void closeConnection(){
        Connection objConnection = null;
        try{
            if (objConnection != null){
                objConnection.close();
                System.out.println("✅ Close succesfully");
            }
        }catch (SQLException error){
            System.out.println("❌ Error"+ error.getMessage());
        }
    }
}