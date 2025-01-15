package controller;

import java.sql.*;
import model.User;
import connection.DataBase;
import connection.ResultDataBase;
import session.UserSession;
import view.MainMenuView;

public class LoginController {

    DataBase db = DataBase.getInstance();

    // En LoginController.java
    public ResultDataBase loginUser(String email, String password) {
        String query = "SELECT id_user, name, last_name, email, password FROM user WHERE email = ?";
        
        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");

                    if (storedPassword.equals(password)) {
                        int userId = rs.getInt("id_user");
                        String name = rs.getString("name");
                        String lastName = rs.getString("last_name");

                        User user = new User(name, lastName, email, password);

                        // Iniciar sesión para el usuario
                        UserSession.startSession(user);

                        // Devolver el ID del usuario junto con el resultado
                        System.out.println("El Id del usuario logeado es: " + userId);
                        // Vamos a probar lanzar la vista de menu principal aca
                        new MainMenuView(userId).setVisible(true);
                        return new ResultDataBase(true, "Login exitoso", userId);
                        
                    } else {
                        return new ResultDataBase(false, "Contraseña incorrecta", -1);
                    }
                } else {
                    return new ResultDataBase(false, "Usuario no encontrado", -1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultDataBase(false, "Error durante el login: " + e.getMessage(), -1);
        }
    }



}