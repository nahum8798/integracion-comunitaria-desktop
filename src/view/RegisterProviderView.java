package view;

import javax.swing.*;

import connection.ResultDataBase;
import controller.UserController;
import model.User;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterProviderView extends JFrame {
    private JTextField txtName; // Campo para el nombre del proveedor
    private JTextField txtLastName; // Campo para el apellido del cliente
    private JTextField txtEmail; // Campo para el email del proveedor
    private JTextField txtStreet;
    private JTextField txtStreetNumber;
    //private JTextField txtPhone; // Campo para el teléfono del proveedor
    private JPasswordField txtPassword;
    private JButton btnRegister; // Botón para registrar al proveedor
    private JButton btnBack; // Botón para volver a la vista principal

    // campos para seleccionar opciones
    private JComboBox<String> cmbTypeProvider;
    private JComboBox<String> cmbCategory;
    private JComboBox<String> cmbTypeJornal;
    private JComboBox<String> cmbProfession;
    private JComboBox<String> cmbCity;
    private JComboBox<String> cmbDepartment;
    private JComboBox<String> cmbProvince;


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
        
        // Seleccion de categoria
        cmbCategory = new JComboBox<>();
        cmbCategory.setBorder(BorderFactory.createTitledBorder("Categoría"));
        add(cmbCategory);

        // Seleccion de profesion
        cmbProfession = new JComboBox<>();
        cmbProfession.setBorder(BorderFactory.createTitledBorder("Profesión"));
        add(cmbProfession);

        // Seleccion de type-provider
        cmbTypeProvider = new JComboBox<>();
        cmbTypeProvider.setBorder(BorderFactory.createTitledBorder("Tipo de proveedor"));
        add(cmbTypeProvider);

        // Seleccion de tipo de jornada
        cmbTypeJornal = new JComboBox<>();
        cmbTypeJornal.setBorder(BorderFactory.createTitledBorder("Tipo de jornada"));
        add(cmbTypeJornal);

        // Selecciona ciudad
        cmbCity = new JComboBox<>();
        cmbCity.setBorder(BorderFactory.createTitledBorder("Ciudad"));
        add(cmbCity);

        // Selecciona departamento
        cmbDepartment = new JComboBox<>();
        cmbDepartment.setBorder(BorderFactory.createTitledBorder("Departamento"));
        add(cmbDepartment);

        // Selecciona provincia
        cmbProvince = new JComboBox<>();
        cmbProvince.setBorder(BorderFactory.createTitledBorder("Provincia"));
        add(cmbProvince);

        // Campo para calle
        txtStreet = new JTextField();
        txtStreet.setBorder(BorderFactory.createTitledBorder("Calle"));
        add(txtStreet);

        txtStreetNumber = new JTextField();
        txtStreetNumber.setBorder(BorderFactory.createTitledBorder("Número"));
        add(txtStreetNumber);
        
        // Cargar opciones desde la base de datos
        UserController userController = new UserController();
        List<String> categories = userController.getCategories();
        List<String> professions = userController.getProfessions();
        List<String> typeProviders = userController.getTypeProviders();
        List<String> typeJornals = userController.getTypeJornals();

        List<String> cities = userController.getCities();
        List<String> departaments = userController.getDepartments();
        List<String> provinces = userController.getProvinces();



        categories.forEach(cmbCategory::addItem);
        professions.forEach(cmbProfession::addItem);
        typeProviders.forEach(cmbTypeProvider::addItem);
        typeJornals.forEach(cmbTypeJornal::addItem);

        cities.forEach(cmbCity::addItem);
        departaments.forEach(cmbDepartment::addItem);
        provinces.forEach(cmbProvince::addItem);

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
                String street = new String(txtStreet.getText());
                String streetNumber = new String(txtStreetNumber.getText());
                //String phone = txtPhone.getText();

                // Obtener las opciones seleccionadas
                String selectedCategory = (String) cmbCategory.getSelectedItem();
                String selectedProfession = (String) cmbProfession.getSelectedItem();
                String selectedTypeProvider = (String) cmbTypeProvider.getSelectedItem();
                String selectedTypeJornal = (String) cmbTypeJornal.getSelectedItem();

                // Obtener direcciones seleccionadas
                String selectedCity = (String) cmbCity.getSelectedItem();
                String selectedDepartament = (String) cmbDepartment.getSelectedItem();
                String selectedProvince = (String) cmbProvince.getSelectedItem();

                // Crear el usuario
                User user = new User(name, lastName ,email, password); // Asegúrate de que el constructor de User esté preparado para esto.

                // Crear el controlador de usuarios y registrar el usuario
                UserController userController = new UserController();
                ResultDataBase result = userController.registerUser(user, 
                                                                    "proveedor", 
                                                                    selectedCategory, 
                                                                    selectedProfession, 
                                                                    selectedTypeProvider, 
                                                                    selectedTypeJornal,
                                                                    selectedCity,
                                                                    selectedDepartament,
                                                                    selectedProvince,
                                                                    street,
                                                                    streetNumber);

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
