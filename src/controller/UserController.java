package controller;
import java.sql.*;
import java.util.List;

import connection.DataBase;
import connection.ResultDataBase;
import model.User;
import model.UserProfile;
import model.Provider;
import model.Customer;

public class UserController {

    DataBase db = DataBase.getInstance();  // Usa la instancia única

    public ResultDataBase registerUser(User user, String role) {
        try (Connection connection = db.openConnection()) {  // Abre la conexión aquí
            connection.setAutoCommit(false);  // Empieza una transacción

            ResultDataBase userResult = createUser(user);
            if (!userResult.getSuccess()) {
                connection.rollback();  // Si algo falla, deshace los cambios
                return userResult;
            }

            int userId = user.getIdUser();

            ResultDataBase resultProfile = createUserProfile(userId, role);
            if (!resultProfile.getSuccess()) {
                connection.rollback();
                return resultProfile;
            }

            if (role.equalsIgnoreCase("proveedor")) {
                Provider provider = new Provider(userId);
                ResultDataBase resultProvider = createProvider(user);
                if (!resultProvider.getSuccess()) {
                    connection.rollback();
                    return resultProvider;
                }
            } else if (role.equalsIgnoreCase("cliente")) {
                Customer customer = new Customer(userId);
                ResultDataBase resultCustomer = createCustomer(customer);
                if (!resultCustomer.getSuccess()) {
                    connection.rollback();
                    return resultCustomer;
                }
            }

            connection.commit();  // Si todo va bien, guarda los cambios
            return new ResultDataBase(true, "Usuario registrado con éxito", null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultDataBase(false, "Error durante el registro del usuario: " + e.getMessage(), null);
        }
    }

    private ResultDataBase createUser(User user) {
        String query = "INSERT INTO user (name, last_name, email, password) VALUES (?, ?, ?, ?)";
        
        // Usamos PreparedStatement con auto-generación de claves
        try (Connection conn = db.openConnection(); // Suponiendo que db.openConnection() devuelve una conexión a la base de datos
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Establecemos los parámetros
            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            
            // Ejecutamos la inserción
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obtenemos el ID generado automáticamente
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int userId = rs.getInt(1); // Obtiene el ID generado
                        user.setIdUser(userId);  // Asignamos el ID al objeto user
                        return new ResultDataBase(true, "Usuario creado correctamente", null);
                    } else {
                        return new ResultDataBase(false, "No se pudo obtener el ID del usuario", null);
                    }
                }
            } else {
                return new ResultDataBase(false, "Error al crear el usuario", null);
            }

        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al crear el usuario: " + e.getMessage(), null);
        }
    }

    
    private ResultDataBase createUserProfile(int userId, String role) {
        String query = "INSERT INTO user_profile (user_id, role_type) VALUES (?, ?)";
        
        // Usamos PreparedStatement para insertar los datos del perfil
        try (Connection conn = db.openConnection();  // Asegúrate de que db.openConnection() retorna una conexión válida
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            // Establecemos los parámetros
            ps.setInt(1, userId);
            ps.setString(2, role);
            
            // Ejecutamos la inserción
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                return new ResultDataBase(true, "Perfil creado correctamente", null);
            } else {
                return new ResultDataBase(false, "No se pudo crear el perfil", null);
            }
    
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al crear el perfil: " + e.getMessage(), null);
        }
    }
    
    
    private ResultDataBase createProvider(User user) {
        String query = "INSERT INTO provider (id_user, name) VALUES (?, ?)";
        
        // Usamos PreparedStatement para insertar los datos del proveedor
        try (Connection conn = db.openConnection();  // Asegúrate de que db.openConnection() retorna una conexión válida
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            // Establecemos los parámetros
            ps.setInt(1, user.getIdUser());
            ps.setString(2, user.getName());
            //ps.setInt(3, provider.getIdTypeProvider());
            //ps.setInt(4, provider.getIdCategory());
            
            // Ejecutamos la inserción
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                return new ResultDataBase(true, "Proveedor registrado correctamente", null);
            } else {
                return new ResultDataBase(false, "No se pudo registrar el proveedor", null);
            }
    
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al registrar el proveedor: " + e.getMessage(), null);
        }
    }
    
    
    private ResultDataBase createCustomer(Customer customer) {
        String query = "INSERT INTO customer (id_user, phone) VALUES (?, ?)";
        boolean success = db.executeUpdate(query, customer.getIdUser(), customer.getPhone());
    
        if (success) {
            return new ResultDataBase(true, "Cliente registrado correctamente", null);
        } else {
            return new ResultDataBase(false, "Error al registrar el cliente", null);
        }
    }
};

