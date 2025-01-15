package repository;

import connection.DataBase;
import connection.ResultDataBase;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProviderRepository {
    private final DataBase db = DataBase.getInstance();

    public ResultDataBase createProvider(User user, String category, String profession, String typeProvider, String typeJornal) {
        String query = "INSERT INTO provider (id_user, name, id_type_provider, id_category, id_profession, id_type_jornal) VALUES (?, ?, ?, ?, ?, ?)";
        

        // Usamos PreparedStatement para insertar los datos del proveedor
        try (Connection conn = db.openConnection();  // Asegúrate de que db.openConnection() retorna una conexión válida
             PreparedStatement ps = conn.prepareStatement(query)) {
    
            int typeProviderId = getTypeProviderId(typeProvider);
            int categoryId = getCategoryId(category);
            int typeJornalId  = getTypeJornalId(typeJornal);
            int professionId = getProfessionId(profession);

            

            // Establecemos los parámetros
            ps.setInt(1, user.getIdUser());
            ps.setString(2, user.getName());
            ps.setInt(3, typeProviderId);
            ps.setInt(4, categoryId);
            ps.setInt(5, professionId);
            ps.setInt(6, typeJornalId);
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

    public int getTypeProviderId(String typeProvider) {
            String query = "SELECT id_type_provider FROM type_provider WHERE type = ?";
            try (Connection conn = db.openConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, typeProvider);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_type_provider");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
        }
    
    public int getCategoryId(String category) {
        String query = "SELECT id_category FROM category WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_category");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getProfessionId(String profession) {
        String query = "SELECT id_profession FROM profession WHERE name = ?";
        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, profession);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_profession");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return -1;
    }   
    
    public int getTypeJornalId(String typeJornal) {
        String query = "SELECT id_type_jornal FROM type_jornal WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, typeJornal);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_type_jornal");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getProviderCategory(int id_user) {

        String categoryName = null;

        // Consulta para obtener el id_category desde la tabla provider
        String queryProvider = "SELECT id_category from provider WHERE id_user = ?";

        // Consulta para obtener el nombre de la categoria desde la tabla category
        String queryCategory = "SELECT name FROM category WHERE id_category = ?";

        try (Connection conn = db.openConnection()) {
            // Paso 1: Obtener el id_category desde la tabla provider
            int id_category = -1;
            try (PreparedStatement psProvider = conn.prepareStatement(queryProvider)) {
                psProvider.setInt(1, id_user);
                try (ResultSet rs = psProvider.executeQuery()) {
                    if (rs.next()) {
                        id_category = rs.getInt("id_category");
                    }
                }
            }

            // Paso 2: Si se encontró el id_Category, buscar el nombre de la categoría
            if (id_category != -1) {
                try (PreparedStatement psCategory = conn.prepareStatement(queryCategory)) {
                    psCategory.setInt(1, id_category);
                    try (ResultSet rs = psCategory.executeQuery()) {
                        if (rs.next()) {
                            categoryName = rs.getString("name");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryName;
        
    }


    /* -- UPDATES QUERY  -- */
    public void updateProviderCategory(int userId, int categoriId) {
        String query = "UPDATE provider SET id_category =  ? WHERE id_user = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, categoriId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}

