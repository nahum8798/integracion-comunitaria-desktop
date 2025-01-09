package view;

import javax.swing.*;

public class MainMenuView extends JFrame {
    public MainMenuView() {
        setTitle("Menú Principal");
        //setSize(400, 300); // Tamaño de la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Contenido del menú principal
        JLabel lblMenu = new JLabel("¡Bienvenido al Menú Principal!", SwingConstants.CENTER);
        lblMenu.setFont(lblMenu.getFont().deriveFont(18f)); // Ajustar tamaño de fuente

        add(lblMenu); // Agregar etiqueta al marco
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuView().setVisible(true));
    }
}
