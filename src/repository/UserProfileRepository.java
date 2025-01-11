package repository;

import connection.DataBase;
import connection.ResultDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserProfileRepository {
    private final DataBase db = DataBase.getInstance();

    public ResultDataBase createUserProfile(int userId, String role) {
        String query = "INSERT INTO user_profile (user_id, role_type) VALUES (?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, role);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultDataBase(true, "Perfil de usuario creado correctamente", null);
            }
            return new ResultDataBase(false, "Error al crear el perfil de usuario", null);
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al crear el perfil de usuario: " + e.getMessage(), null);
        }
    }
}
