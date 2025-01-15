package view;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import connection.DatabaseHelper;
import service.RoleService;

public class MainMenuView extends JFrame {
    private int userId;  // ID del usuario
    private RoleService roleService; // Servicio de roles

    // Constructor que recibe el ID del usuario
    public MainMenuView(int userId) {
        this.userId = userId;

        setTitle("Menú Principal - Usuario " + userId);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración del contenido del menú
        try {
            initializeMenu();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los módulos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeMenu() throws SQLException {
        // Obtener la conexión a la base de datos
        Connection connection = DatabaseHelper.getConnection();
        this.roleService = new RoleService(connection);

        // Obtener los módulos disponibles para el usuario
        List<String> modules = roleService.getUserModules(userId);

        System.out.println("Módulos obtenidos: " + modules.size());

        // Crear el diseño del menú principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // GridLayout con 2 columnas

        for (String module : modules) {
            // Crear un botón para cada módulo
            JButton moduleButton = new JButton(module);
            moduleButton.setFont(moduleButton.getFont().deriveFont(16f)); // Ajustar tamaño de fuente
            moduleButton.addActionListener(e -> {
                if ("ManageProviderProfile".equals(module)) {
                    // Abrir la vista de perfil del proveedor
                    SwingUtilities.invokeLater(() -> new ProviderView(userId).setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(this, "Abrir módulo: " + module,
                    "Módulo seleccionado", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            panel.add(moduleButton);
        }

        // Mostrar un mensaje si no hay módulos disponibles
        if (modules.isEmpty()) {
            JLabel noModulesLabel = new JLabel("No hay módulos disponibles para este usuario.", SwingConstants.CENTER);
            noModulesLabel.setFont(noModulesLabel.getFont().deriveFont(16f));
            panel.add(noModulesLabel);
        }

        // Agregar el panel al JFrame
        add(new JScrollPane(panel), BorderLayout.CENTER);
    }

    //public static void main(String[] args) {
    //    SwingUtilities.invokeLater(() -> {
      //      try {
        //        // Pasar el ID de usuario para la prueba (por ejemplo, 1)
           //     new MainMenuView(userId).setVisible(true);
          //  } catch (Exception e) {
            //    e.printStackTrace();
            //}
       // });
    //}
}
