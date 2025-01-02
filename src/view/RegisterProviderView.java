package view;

import javax.swing.*;

import connection.ResultDataBase;
import controller.UserController;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterProviderView extends JFrame {
    private JTextField txtName; // Campo para el nombre del proveedor
    private JTextField txtLastName; // Campo para el apellido del cliente
    private JTextField txtEmail; // Campo para el email del proveedor
    //private JTextField txtPhone; // Campo para el teléfono del proveedor
    private JPasswordField txtPassword;
    private JButton btnRegister; // Botón para registrar al proveedor
    private JButton btnBack; // Botón para volver a la vista principal

    public RegisterProviderView() {
        // Configuración principal de la ventana
        setTitle("Registro de Proveedor"); // Título de la ventana
        //setSize(400, 400); // Tamaño de la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir la ventana en pantalla completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al salir
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new GridLayout(5, 1, 10, 10)); // Usamos un GridLayout con 5 filas y espacio entre componentes

        // Campo para el nombre
        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createTitledBorder("Nombre del Proveedor")); // Título sobre el campo
        add(txtName); // Añadir al diseño

        // Campo para el apellido
        txtLastName = new JTextField();
        txtLastName.setBorder(BorderFactory.createTitledBorder("Nombre del Proveedor")); // Título sobre el campo
        add(txtLastName); // Añadir al diseño

        // Campo para el email
        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Correo Electrónico")); // Título sobre el campo
        add(txtEmail); // Añadir al diseño

        // Campo para la contraseña
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        add(txtPassword);

        // Campo para el teléfono
        //txtPhone = new JTextField();
        //txtPhone.setBorder(BorderFactory.createTitledBorder("Teléfono")); // Título sobre el campo
        //add(txtPhone); // Añadir al diseño

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

        // Acción para registrar al proveedor
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los datos del formulario
                String name = txtName.getText();
                String lastName = txtLastName.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                //String phone = txtPhone.getText();

                // Crear el usuario
                User user = new User(name, lastName ,email, password); // Asegúrate de que el constructor de User esté preparado para esto.

                // Crear el controlador de usuarios y registrar el usuario
                UserController userController = new UserController();
                ResultDataBase result = userController.registerUser(user, "proveedor");

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
