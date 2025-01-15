package view;

import controller.LoginController;
import connection.ResultDataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JLabel lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;

    private LoginController loginController;

    public LoginView() {
        setTitle("Integración Comunitaria - Iniciar Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loginController = new LoginController(); // Inicializar el controlador

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        lblUsername = new JLabel("Email:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername = new JTextField();
        formPanel.add(lblUsername);
        formPanel.add(txtUsername);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword = new JPasswordField();
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(btnLogin);

        btnBack = new JButton("Volver");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        // Acción para iniciar sesión
        // En LoginView.java
btnLogin.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String email = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        ResultDataBase result = loginController.loginUser(email, password);

        if (result.getSuccess()) {
            JOptionPane.showMessageDialog(null, result.getMessage());

            // Obtener el ID del usuario desde el resultado de la base de datos
            int userId = result.getUserId(); // Obtener el ID del usuario
            
            //System.out.println("El Id del usuario logeado es: " + userId);

            // Pasar el ID del usuario al menú principal
            //new MainMenuView(userId).setVisible(true);  // Pasamos el ID a MainMenuView
            dispose();  // Cerrar la ventana de login
        } else {
            JOptionPane.showMessageDialog(null, result.getMessage());
        }
    }
});



        // Acción para volver a la vista principal
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainView().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}