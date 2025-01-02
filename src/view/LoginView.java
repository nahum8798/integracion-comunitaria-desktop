package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JLabel lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;

    public LoginView() {
        setTitle("Integración Comunitaria - Iniciar Sesión");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, 10, 20));  // Ajustado el espaciado entre los campos
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Ajustamos los márgenes del panel

        lblUsername = new JLabel("Nombre de Usuario:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(250, 30)); // Ajustamos el tamaño del campo
        formPanel.add(lblUsername);
        formPanel.add(txtUsername);

        lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(250, 30)); // Ajustamos el tamaño del campo
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Ajuste del espaciado de los botones

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 16));
        btnLogin.setBackground(new Color(72, 201, 176)); // Color suave
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(new RoundBorder(30)); // Bordes redondeados
        btnLogin.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(btnLogin);

        btnBack = new JButton("Volver");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBack.setBackground(new Color(220, 53, 69)); // Color suave
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(new RoundBorder(30)); // Bordes redondeados
        btnBack.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        // Acción para iniciar sesión
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí iría la lógica para verificar las credenciales
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                
                // Simulación de verificación
                if (username.equals("admin") && password.equals("admin123")) {
                    JOptionPane.showMessageDialog(null, "Sesión iniciada con éxito");
                    new MainView().setVisible(true);  // Cambiar a la vista principal
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
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
