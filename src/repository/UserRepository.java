package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connection.DataBase;
import connection.ResultDataBase;
import model.User;
import model.Provider;
import model.Customer;

public class UserRepository {
    private DataBase db = DataBase.getInstance();

    public ResultDataBase registerUser(User user, String role, String category, String profession, String typeProvider, String typeJornal,
                                       String city, String departament, String province, String street, String streetNumber) {
        try (Connection connection = db.openConnection()) {
            connection.setAutoCommit(false);

            ResultDataBase userResult = createUser(user);
            if (!userResult.getSuccess()) {
                connection.rollback();
                return userResult;
            }

            int userId = user.getIdUser();

            ResultDataBase resultProfile = createUserProfile(userId, role);
            if (!resultProfile.getSuccess()) {
                connection.rollback();
                return resultProfile;
            }

            if (role.equalsIgnoreCase("proveedor")) {
                ResultDataBase resultProvider = createProvider(user, category, profession, typeProvider, typeJornal);
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

            ResultDataBase addressResult = createAddress(user, city, departament, province, street, streetNumber);
            if (!addressResult.getSuccess()) {
                connection.rollback();
                return addressResult;
            }

            connection.commit();
            return new ResultDataBase(true, "Usuario registrado con éxito", null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultDataBase(false, "Error durante el registro del usuario: " + e.getMessage(), null);
        }
    }

    private ResultDataBase createUser(User user) {
        String query = "INSERT INTO user (name, last_name, email, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setIdUser(rs.getInt(1));
                        return new ResultDataBase(true, "Usuario creado correctamente", null);
                    }
                }
            }
            return new ResultDataBase(false, "Error al crear el usuario", null);
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al crear el usuario: " + e.getMessage(), null);
        }
    }

    private ResultDataBase createUserProfile(int userId, String role) {
        String query = "INSERT INTO user_profile (user_id, role_type) VALUES (?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);
            ps.setString(2, role);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultDataBase(true, "Perfil creado correctamente", null);
            }
            return new ResultDataBase(false, "No se pudo crear el perfil", null);
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al crear el perfil: " + e.getMessage(), null);
        }
    }

    private ResultDataBase createProvider(User user, String category, String profession, String typeProvider, String typeJornal) {
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
    

    //private ResultDataBase createCustomer(Customer customer) {
        // Implementación similar al código original
    //}

    private ResultDataBase createAddress(User user, String city, String departament, String province, String street, String streetNumber) {
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
    
    
    
    private ResultDataBase createCustomer(Customer customer) {
        String query = "INSERT INTO customer (id_user, phone) VALUES (?, ?)";
        boolean success = db.executeUpdate(query, customer.getIdUser(), customer.getPhone());
    
        if (success) {
            return new ResultDataBase(true, "Cliente registrado correctamente", null);
        } else {
            return new ResultDataBase(false, "Error al registrar el cliente", null);
        }
    }

    public List<String> getTypeProviders() {
        List<String> typeProviders = new ArrayList<>();
        String query = "SELECT type FROM type_provider";

        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                typeProviders.add(rs.getString("type")); // Agrega el nombre del tipo de proveedor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeProviders;
    }

    public List<String> getProfessions() {
        List<String> professions = new ArrayList<>();
        String query = "SELECT name FROM profession";

        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    professions.add(rs.getString("name")); // Agrega nombre de la profesion
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return professions;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT name FROM category";

        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("name")); // Agrega el nombre de la categoría
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<String> getTypeJornals() {
        List<String> typeJornals = new ArrayList<>();
        String query = "SELECT name FROM type_jornal";

        try (Connection conn = db.openConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                typeJornals.add(rs.getString("name")); // Agrega el nombre del tipo de jornal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeJornals;
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


};



