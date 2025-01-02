package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JLabel lblUsername, lblPassword, lblEmail;
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public RegisterView() {
        setTitle("Integración Comunitaria - Registro");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Ventana completa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 20));  // Ajustado el espaciado entre los campos
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

        lblEmail = new JLabel("Correo Electrónico:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(250, 30)); // Ajustamos el tamaño del campo
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);

        add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Ajuste del espaciado de los botones

        btnRegister = new JButton("Registrar");
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 16));
        btnRegister.setBackground(new Color(72, 201, 176));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(new RoundBorder(30)); // Bordes redondeados
        btnRegister.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(btnRegister);

        btnBack = new JButton("Volver");
        btnBack.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBack.setBackground(new Color(220, 53, 69));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorder(new RoundBorder(30)); // Bordes redondeados
        btnBack.setPreferredSize(new Dimension(150, 50));
        buttonPanel.add(btnBack);

        add(buttonPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí iría la lógica para registrar el usuario
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");
                new MainView().setVisible(true);
                dispose();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainView().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterView().setVisible(true));
    }
}
