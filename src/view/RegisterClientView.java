package view;

import javax.swing.*;

import connection.ResultDataBase;
import controller.UserController;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterClientView extends JFrame {
    private JTextField txtName; // Campo para el nombre del cliente
    private JTextField txtLastName; // Campo para el apellido del cliente
    private JTextField txtEmail; // Campo para el email del cliente
    private JPasswordField txtPassword;
    private JTextField txtAddress; // Campo para la dirección del cliente
    private JButton btnRegister; // Botón para registrar al cliente
    private JButton btnBack; // Botón para volver a la vista principal

    public RegisterClientView() {
        // Configuración principal de la ventana
        setTitle("Registro de Cliente"); // Título de la ventana
        //setSize(400, 400); // Tamaño de la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir la ventana en pantalla completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al salir
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new GridLayout(5, 1, 10, 10)); // Usamos un GridLayout con 5 filas y espacio entre componentes

        // Campo para el nombre
        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createTitledBorder("Nombre del Cliente")); // Título sobre el campo
        add(txtName); // Añadir al diseño

        // Campo para el apelldido
        txtLastName = new JTextField();
        txtLastName.setBorder(BorderFactory.createTitledBorder("Nombre del Cliente")); // Título sobre el campo
        add(txtLastName); // Añadir al diseño

        // Campo para el email
        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Correo Electrónico")); // Título sobre el campo
        add(txtEmail); // Añadir al diseño

        // Campo para la contraseña
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        add(txtPassword);

        // Campo para la dirección
        //txtAddress = new JTextField();
        //txtAddress.setBorder(BorderFactory.createTitledBorder("Dirección")); // Título sobre el campo
        //add(txtAddress); // Añadir al diseño

        // Botón de registro
        btnRegister = new JButton("Registrar");
        btnRegister.setBackground(new Color(72, 201, 176)); // Color de fondo
        btnRegister.setForeground(Color.WHITE); // Color del texto
        btnRegister.setFocusPainted(false); // Elimina el borde de enfoque
        add(btnRegister); // Añadir al diseño

        // Botón para volver
        btnBack = new JButton("Volver");
        btnBack.setBackground(new Color(231, 76, 60)); // Color rojo suave
        btnBack.setForeground(Color.WHITE); // Color del texto
        btnBack.setFocusPainted(false); // Elimina el borde de enfoque
        add(btnBack); // Añadir al diseño

        // Acción para registrar al cliente
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos del formulario
                String name = txtName.getText();
                String lastname = txtLastName.getText();
                String email = txtEmail.getText();
                String password = txtPassword.toString();
                //String address = txtAddress.getText();

                // Crear el usuario
                User user = new User(name, lastname, email, password); // Asegúrate de que el constructor de User esté preparado para esto.

                // Crear el controlador de usuarios y registrar el usuario
                UserController userController = new UserController();
                ResultDataBase result = userController.registerUser(user, "cliente");

                // Mostrar el resultado
                if (result.getSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                } else {
                    JOptionPane.showMessageDialog(null, result.getMessage());
                }
            }
        });


        // Acción para volver a la vista principal
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainView().setVisible(true); // Muestra la vista principal
                dispose(); // Cierra la ventana actual
            }
        });
    }
}
