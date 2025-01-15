package connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // Información de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/integracion_comunitaria";
    private static final String USER = "root";
    private static final String PASSWORD = "nM1258menMa";

    // Método para obtener una conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Obtiene el rol del usuario desde la base de datos
    public static String getUserRole(int userId) {
        String role = null;
        String query = "SELECT role FROM user_profile WHERE iduser = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
    }

    // Obtiene los módulos accesibles según el rol
    public static List<String> getAccessibleModules(String role) {
        List<String> modules = new ArrayList<>();
        String query = "SELECT m.name_module " +
                       "FROM role_module rm " +
                       "JOIN module m ON rm.idmodule = m.idmodule " +
                       "WHERE rm.idrole = (SELECT idrole FROM role WHERE name = ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    modules.add(rs.getString("name_module"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }
}
