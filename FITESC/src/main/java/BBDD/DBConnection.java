package BBDD;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static Connection connection;
    //plantear un patron singleton

    //getConnection -> retorna la conexion
    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }

        return connection;
    }

    //createConnection -> crea la conexion
    public static void createConnection() {
        //Cargar el Driver Class.forName("")
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String uri = String.format("jdbc:mysql://%s/%s", Esquema.HOST,Esquema.DB_NAME);

            connection = DriverManager.getConnection(uri,"root","1234");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encuentra el driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Error en la conexion a la base de datos");

        }

        //DriverManaget.connect(uri, user, pass)
    }

    public static void closeConnection(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al cerrar la base de datos");
        }
    }
}
