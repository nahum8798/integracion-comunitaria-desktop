package repository;

import connection.DataBase;
import connection.ResultDataBase;
import model.User;
import model.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressRepository {
    private final DataBase db = DataBase.getInstance();

    public ResultDataBase createAddress(User user, String city, String departament, String province, String street, String streetNumber) {
        String query = "INSERT INTO address (street, number, id_city, id_departament, id_province, id_country, postal_code, id_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = db.openConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                // Obtener IDs y datos relacionados
                int id_city = getCityId(city);
                int id_department = getDepartamentId(departament);
                int id_province = getProviceId(province);
                int id_country = getCountryId("Argentina"); // Supongamos que Argentina es el país
                String postalCode = getPostalCode(city);
                
                // Establecer los parámetros
                ps.setString(1, street);
                ps.setString(2, streetNumber);
                ps.setInt(3, id_city);
                ps.setInt(4, id_department);
                ps.setInt(5, id_province);
                ps.setInt(6, id_country);
                ps.setString(7, postalCode);
                ps.setInt(8, user.getIdUser());
                
                // Ejecutar la inserción
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected > 0) {
                    return new ResultDataBase(true, "Dirección registrada correctamente", null);
                } else {
                    return new ResultDataBase(false, "No se pudo registrar la dirección", null);
                }
                
            } catch (SQLException e) {
                return new ResultDataBase(false, "Error al registrar la dirección: " + e.getMessage(), null);
            }
    }    

    private String getPostalCode(String cityName) throws SQLException {
        String query = "SELECT postal_code FROM city WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, cityName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("postal_code");
                }
            }
        }
        throw new SQLException("No se encontró la ciudad con nombre: " + cityName);
    }
    
    private int getCountryId(String countryName) throws SQLException {
        String query = "SELECT id_country FROM country WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, countryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_country");
                }
            }
        }
        throw new SQLException("No se encontró el país con nombre: " + countryName);
    }
    
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        String query = "SELECT name FROM city";
    
        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                cities.add(rs.getString("name")); // Agrega el nombre del tipo de jornal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    
    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String query = "SELECT name_departament FROM departament";
    
        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                departments.add(rs.getString("name_departament")); // Agrega el nombre del tipo de jornal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
    
    public List<String> getProvinces() {
        List<String> provinces = new ArrayList<>();
        String query = "SELECT name FROM province";
    
        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
    
            while (rs.next()) {
                provinces.add(rs.getString("name")); // Agrega el nombre del tipo de jornal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinces;
    }
    
    public int getCityId(String city) {
        String query = "SELECT id_city FROM city WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_city");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int getDepartamentId(String departament) {
        String query = "SELECT id_departament FROM departament WHERE name_departament = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, departament);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_departament");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public int getProviceId(String province) {
        String query = "SELECT id_province FROM province WHERE name = ?";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, province);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_province");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}


