package view;

import javax.swing.*;
import connection.ResultDataBase;
import controller.UserController;
import model.User;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterProviderView extends JFrame {
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtStreet;
    private JTextField txtStreetNumber;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnRegister;
    private JButton btnBack;
    private JComboBox<String> cmbTypeProvider;
    private JComboBox<String> cmbCategory;
    private JComboBox<String> cmbTypeJornal;
    private JComboBox<String> cmbProfession;
    private JComboBox<String> cmbCity;
    private JComboBox<String> cmbDepartment;
    private JComboBox<String> cmbProvince;

    public RegisterProviderView() {
        setTitle("Registro de Proveedor");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createTitledBorder("Nombre del Proveedor"));
        add(txtName);

        txtLastName = new JTextField();
        txtLastName.setBorder(BorderFactory.createTitledBorder("Apellido del Proveedor"));
        add(txtLastName);

        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Correo Electrónico"));
        add(txtEmail);

        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        add(txtPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBorder(BorderFactory.createTitledBorder("Confirmar Contraseña"));
        add(txtConfirmPassword);

        cmbCategory = new JComboBox<>();
        cmbCategory.setBorder(BorderFactory.createTitledBorder("Categoría"));
        add(cmbCategory);

        cmbProfession = new JComboBox<>();
        cmbProfession.setBorder(BorderFactory.createTitledBorder("Profesión"));
        add(cmbProfession);

        cmbTypeProvider = new JComboBox<>();
        cmbTypeProvider.setBorder(BorderFactory.createTitledBorder("Tipo de proveedor"));
        add(cmbTypeProvider);

        cmbTypeJornal = new JComboBox<>();
        cmbTypeJornal.setBorder(BorderFactory.createTitledBorder("Tipo de jornada"));
        add(cmbTypeJornal);

        cmbCity = new JComboBox<>();
        cmbCity.setBorder(BorderFactory.createTitledBorder("Ciudad"));
        add(cmbCity);

        cmbDepartment = new JComboBox<>();
        cmbDepartment.setBorder(BorderFactory.createTitledBorder("Departamento"));
        add(cmbDepartment);

        cmbProvince = new JComboBox<>();
        cmbProvince.setBorder(BorderFactory.createTitledBorder("Provincia"));
        add(cmbProvince);

        txtStreet = new JTextField();
        txtStreet.setBorder(BorderFactory.createTitledBorder("Calle"));
        add(txtStreet);

        txtStreetNumber = new JTextField();
        txtStreetNumber.setBorder(BorderFactory.createTitledBorder("Número"));
        add(txtStreetNumber);

        UserController userController = new UserController();
        userController.getCategories().forEach(cmbCategory::addItem);
        userController.getProfessions().forEach(cmbProfession::addItem);
        userController.getTypeProviders().forEach(cmbTypeProvider::addItem);
        userController.getTypeJornals().forEach(cmbTypeJornal::addItem);
        userController.getCities().forEach(cmbCity::addItem);
        userController.getDepartments().forEach(cmbDepartment::addItem);
        userController.getProvinces().forEach(cmbProvince::addItem);

        btnRegister = new JButton("Registrar");
        btnRegister.setBackground(new Color(72, 201, 176));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        add(btnRegister);

        btnBack = new JButton("Volver");
        btnBack.setBackground(new Color(231, 76, 60));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        add(btnBack);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText().trim();
                String lastName = txtLastName.getText().trim();
                String email = txtEmail.getText().trim();
                String password = new String(txtPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());
                String street = txtStreet.getText().trim();
                String streetNumber = txtStreetNumber.getText().trim();

                String selectedCategory = (String) cmbCategory.getSelectedItem();
                String selectedProfession = (String) cmbProfession.getSelectedItem();
                String selectedTypeProvider = (String) cmbTypeProvider.getSelectedItem();
                String selectedTypeJornal = (String) cmbTypeJornal.getSelectedItem();
                String selectedCity = (String) cmbCity.getSelectedItem();
                String selectedDepartament = (String) cmbDepartment.getSelectedItem();
                String selectedProvince = (String) cmbProvince.getSelectedItem();

                if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || street.isEmpty() || streetNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                    return;
                }

                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Correo electrónico inválido.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                    return;
                }

                User user = new User(name, lastName, email, password);
                ResultDataBase result = userController.registerUser(user, "proveedor", selectedCategory, selectedProfession, selectedTypeProvider, selectedTypeJornal, selectedCity, selectedDepartament, selectedProvince, street, streetNumber);

                JOptionPane.showMessageDialog(null, result.getMessage());
            }
        });

        btnBack.addActionListener(e -> {
            new MainView().setVisible(true);
            dispose();
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }


}

