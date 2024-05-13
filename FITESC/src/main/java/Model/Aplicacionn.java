package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.Scanner;

class Interfaz extends JFrame {

    private Aplicacion fitesc;
    private JTextField nombreField, apellido1Field, apellido2Field, edadField, correoField, contrasenaField;

    public Interfaz() {
        fitesc = new Aplicacion("Fitesc", 1);

        // Configuración del JFrame
        setTitle("Fitesc");
        ImageIcon icono = new ImageIcon("C:\\Users\\hugoe\\Pictures\\FITESC.png");

        setIconImage(icono.getImage());
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));



        // Campos de entrada
        add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        add(nombreField);

        add(new JLabel("Primer Apellido:"));
        apellido1Field = new JTextField();
        add(apellido1Field);

        add(new JLabel("Segundo Apellido:"));
        apellido2Field = new JTextField();
        add(apellido2Field);

        add(new JLabel("Edad:"));
        edadField = new JTextField();
        add(edadField);


        add(new JLabel("Correo Electrónico:"));
        correoField = new JTextField();
        add(correoField);

        add(new JLabel("Contraseña:"));
        contrasenaField = new JTextField();
        add(contrasenaField);

        // Botones
        JButton registrarBtn = new JButton("Registrar usuario");
        registrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
        add(registrarBtn);

        JButton iniciarSesionBtn = new JButton("Iniciar sesión");
        iniciarSesionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        add(iniciarSesionBtn);

        JButton salirBtn = new JButton("Salir");
        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("----HASTA PRONTO----");
                System.exit(0);
            }
        });
        add(salirBtn);

        // Mostrar el JFrame
        setVisible(true);
    }

    private void registrarUsuario() {
        try {
            Persona usuario = new Persona(nombreField.getText(), apellido1Field.getText(),
                    apellido2Field.getText(), correoField.getText(),
                    contrasenaField.getText());

            fitesc.aniadirUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
        } catch (NumberFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Introduce valores válidos en los campos numéricos.");
        }
    }

    private void iniciarSesion() {
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();

        if (fitesc.iniciarSesion(correo, contrasena)) {
            JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
            funcionamiento();
        } else {
            JOptionPane.showMessageDialog(this, "Inicio de sesión fallido. Verifique sus credenciales.");
        }
    }
    private void funcionamiento(){
        Scanner sc = new Scanner(System.in);
        Chatbot chatbot = new Chatbot("Fitesc", "Hugo");

        boolean comprobacion;
        System.out.println("Bienvenido a la aplicación FITESC");

        do {
            System.out.printf("¿En qué puedo ayudarle %s?\n",nombreField.getText());
            String mensaje = sc.nextLine();

            chatbot.calorias(mensaje);
            chatbot.entrenador(mensaje);
            chatbot.clase(mensaje);

            System.out.println("¿Puedo ayudarle en algo más?");
            //sc.nextLine();
            mensaje = sc.nextLine();
            comprobacion = chatbot.comprobacion(mensaje);
        } while (comprobacion);
    }

        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Interfaz();
            }
        });*/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Interfaz();
            }
        });
    }
}