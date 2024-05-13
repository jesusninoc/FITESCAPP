package Model;

import BBDD.DBConnection;
import BBDD.Esquema;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Chatbot {
    private String nombre, creador;

    public Chatbot() {
    }

    public Chatbot(String nombre, String creador) {
        this.nombre = nombre;
        this.creador = creador;
    }

    Scanner sc = new Scanner(System.in);
    public void calorias(String mensaje){
        if (mensaje.contains("calorias")){
            double calorias = 0;
            System.out.println("Claro, voy a calcular tus calorias de mantenimiento");
            System.out.println("Introduce tu peso");
            double peso = sc.nextDouble();
            System.out.println("Introduce tu altura (en cm)");
            double altura = sc.nextDouble();
            System.out.println("Introduce tu género (Hombre/Mujer)");
            String sexo = sc.next();
            System.out.println("Introduce tu edad");
            int edad = sc.nextInt();
            if (sexo.equalsIgnoreCase("Hombre")){
                calorias = ((10*peso)+(6.25*altura)-(5*edad)+5)*1.5;
            } else if (sexo.equalsIgnoreCase("Mujer")) {
                calorias = ((10*peso)+(6.25*altura)-(5*edad)-161)*1.5;
            }
            System.out.println("Sus calorias de mantenimiento son: "+calorias);
            System.out.println("¿Quiere que le muestre las calorias de ganancia de peso o pérdida de peso?");
            String contestacion = sc.next();
            if (contestacion.equalsIgnoreCase("Si")){
                System.out.println("¿Calorias de ganancia o de pérdida de peso?");
                String respuesta = sc.next();
                if (respuesta.contains("Ganancia")){
                    System.out.println("Las calorías que tiene que consumir para ganar peso son: "+(calorias + 300));
                } else if (respuesta.contains("Perdida")) {
                    System.out.println("Las calorías que tiene que consumir para perder peso son: "+(calorias - 300));
                }
            }
        }
    }

    public void entrenador(String mensaje){
        if (mensaje.contains("entrenador")){
            System.out.println("¿Con que entrenador quiere reservar?");
            System.out.println("Javi");
            System.out.println("Pepe");
            System.out.println("Pablo");
            String eleccionEntrenador = sc.nextLine();
            if (eleccionEntrenador.contains("Javi")){
                String entrenador = "Javi";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("javi",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            } else if (eleccionEntrenador.contains("Pepe")) {
                String entrenador = "Pepe";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("pepe",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            } else if (eleccionEntrenador.contains("Pablo")) {
                String entrenador = "Pablo";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("pablo",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            }
        }
    }

    public void clase(String mensaje){
        if (mensaje.contains("clase")){
            System.out.println("¿En qué clase quieres reservar?");
            System.out.println("Zumba");
            System.out.println("Aquagym");
            System.out.println("Bodycombat");
            String eleccionClase = sc.nextLine();
            if (eleccionClase.contains("Zumba")){
                String clase = "Zumba";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("zumba",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            } else if (eleccionClase.contains("Aquagym")) {
                String clase = "Aquagym";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("aquagym",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            } else if (eleccionClase.contains("Bodycombat")) {
                String entrenador = "Bodycombat";
                System.out.println("¿A nombre de quién?");
                String usuario = sc.next();
                int dia = diaCita();
                double hora = horaCita();
                if (confirmacion(dia,hora)){
                    System.out.println("Cita confirmada");
                    guardarBBDD("bodycombat",dia,hora,usuario);
                } else if (!confirmacion(dia,hora)) {
                    System.out.println("De acuerdo, anulo la cita");
                }
            }
        }
    }

    public int diaCita(){
        System.out.println("¿Qué día quiere reservar?");
        int dia = sc.nextInt();
        return dia;
    }
    public double horaCita(){
        System.out.println("¿A qué hora quiere reservar?");
        double hora = sc.nextDouble();
        return hora;
    }
    public boolean confirmacion(int dia, double hora){
        System.out.printf("Va a reservar el día %d a las %.2f horas, ¿está de acuerdo?\n",dia, hora);
        String respuesta = sc.next();
        if (respuesta.equalsIgnoreCase("Si")){
            return true;
        }
        return false;
    }
    public boolean comprobacion(String mensaje){
        if (mensaje.equalsIgnoreCase("Si")){
            return true;
        }
        System.out.println("Hasta pronto!!");
        return false;
    }
    private Connection connection;
    Statement statement = null;
    public void guardarBBDD(String base, int dia, double hora, String cliente){

        //Abrir la conexion
        connection = DBConnection.getConnection();

        try {

            statement = connection.createStatement();
            String query = String.format("INSERT INTO %s (%s,%s,%s) VALUES ('%s','%s','%s')",
                    base,
                    Esquema.COL_DIA,Esquema.COL_HORA,Esquema.COL_CLIENTE,
                    dia,hora,cliente);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }
}
