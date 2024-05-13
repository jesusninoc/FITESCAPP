package Model;

import BBDD.DBConnection;
import BBDD.Esquema;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacion {
    Scanner sc = new Scanner(System.in);
    private Connection connection;
    Statement statement = null;
    private String nombre;
    private int tamanio;
    private ArrayList<Persona> listaUsuarios;

    public Aplicacion() {
        listaUsuarios = new ArrayList<Persona>();
    }

    public Aplicacion(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        listaUsuarios = new ArrayList<Persona>();
    }


    private Persona estaUsuario(String dni) {
        for (Persona persona : listaUsuarios) {
            if (persona.getDNI().equalsIgnoreCase(dni)) {
                // si está retorno la persona
                return persona;
            }
        }
        // si no está la persona retorno null
        return null;
    }

    private Persona estaUsuario2(String correo) {
        for (Persona persona : listaUsuarios) {
            if (persona.getCorreo().equalsIgnoreCase(correo)) {
                // si está retorno la persona
                return persona;
            }
        }
        // si no está la persona retorno null
        return null;
    }

    public void aniadirUsuario(Persona persona) throws IOException {
        guardarBBDD(persona);
    }


    public boolean iniciarSesion(String correo, String contrasena) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",Esquema.TABLA_USUARIOS,
                Esquema.COL_CORREO,correo,
                Esquema.COL_CONTRASEÑA,cifrarContrasenia(contrasena));

        connection = DBConnection.getConnection();

        try {
            statement = connection.createStatement();
            //ejecutan querys
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                System.out.println("Inicio de sesión correcto");
                return true;
            }
            System.out.println("ERROR EN EL INICIO DE SESION");
            return false;

        } catch (SQLException e) {
            System.err.println("error en el ejercicio sql");
            System.out.println(e.getMessage());
        }

        DBConnection.closeConnection();
        connection = null;
        return false;
    }
    public String cifrarContrasenia(String contrasenia){
        String[] letras = contrasenia.split((""));
        contrasenia = "";
        for (String letra:letras) {
            char letraChar = letra.charAt(0);
            contrasenia = contrasenia.concat(String.valueOf(letraChar*2));
        }
        return contrasenia;
    }
    public void guardarBBDD(Persona persona){

        //Abrir la conexion
        connection = DBConnection.getConnection();

        try {
            statement = connection.createStatement();
            String query = String.format("INSERT INTO usuarios (%s,%s,%s) VALUES ('%s','%s','%s')",
                    Esquema.COL_NOMBRE,Esquema.COL_CORREO,Esquema.COL_CONTRASEÑA,
                    persona.getNombre(),persona.getCorreo(),cifrarContrasenia(persona.getContrasena()));
            statement.execute(query);

        } catch (SQLException e) {
            System.out.println("ERROR");
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        //Cerrar la conexion
        DBConnection.closeConnection();
        connection = null;
    }
    private void guardarCuentaEnArchivo(String correo, String contraseña) throws IOException {
        FileWriter writer = new FileWriter("src/Resources/cuentasGimnasio.txt", true);
        writer.write("Correo: " + correo + ", Contraseña: " + contraseña + "\n");
        writer.close();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
}
