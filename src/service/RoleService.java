package service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private Connection connection;

    public RoleService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Obtiene todos los módulos a los que puede acceder un usuario basado en su rol.
     * 
     * @param userId ID del usuario.
     * @return Una lista de nombres de módulos accesibles para el usuario.
     * @throws SQLException
     */
    public List<String> getUserModules(int userId) throws SQLException {
        List<String> modules = new ArrayList<>();
    
        // Paso 1: Obtener role_type
        String roleTypeQuery = "SELECT role_type FROM user_profile WHERE user_id = ?";
        String roleType = null;
        try (PreparedStatement stmt = connection.prepareStatement(roleTypeQuery)) {
            stmt.setInt(1, userId); 
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roleType = rs.getString("role_type");
            }
        }
    
        // Paso 2: Validar roleType y obtener roleId
        if (roleType != null) {
            String roleIdQuery = "SELECT idrole FROM role WHERE name = ?";
            int roleId = -1;
            try (PreparedStatement stmt = connection.prepareStatement(roleIdQuery)) {
                stmt.setString(1, roleType);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    roleId = rs.getInt("idrole");
                }
            }
    
            // Paso 3: Validar roleId y obtener módulos
            if (roleId != -1) {
                String moduleQuery = """
                    SELECT m.name AS module_name
                    FROM modules m
                    INNER JOIN role_module rm ON m.idmodule = rm.idmodule
                    WHERE rm.idrole = ?
                """;
                try (PreparedStatement stmt = connection.prepareStatement(moduleQuery)) {
                    stmt.setInt(1, roleId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        modules.add(rs.getString("module_name"));
                    }
                }
            }
        }
    
        return modules;
    }
    
}
