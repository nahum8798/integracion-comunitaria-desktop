package repository;

import connection.DataBase;
import connection.ResultDataBase;
import model.Customer;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final DataBase db = DataBase.getInstance();

    // Repositorios adicionales
    private final UserProfileRepository userProfileRepository = new UserProfileRepository();
    private final ProviderRepository providerRepository = new ProviderRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final AddressRepository addressRepository = new AddressRepository();

    public ResultDataBase createUser(User user) {
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

    public ResultDataBase registerUser(User user, String role, String category, String profession, String typeProvider, String typeJornal,
                                       String city, String departament, String province, String street, String streetNumber) {
        try (Connection connection = db.openConnection()) {
            connection.setAutoCommit(false);

            // Crear usuario
            ResultDataBase userResult = createUser(user);
            if (!userResult.getSuccess()) {
                connection.rollback();
                return userResult;
            }
            int userId = user.getIdUser();

            // Crear perfil de usuario
            ResultDataBase resultProfile = userProfileRepository.createUserProfile(userId, role);
            if (!resultProfile.getSuccess()) {
                connection.rollback();
                return resultProfile;
            }

            // Registrar detalles del proveedor o cliente
            if (role.equalsIgnoreCase("proveedor")) {
                ResultDataBase resultProvider = providerRepository.createProvider(user, category, profession, typeProvider, typeJornal);
                if (!resultProvider.getSuccess()) {
                    connection.rollback();
                    return resultProvider;
                }
            } else if (role.equalsIgnoreCase("cliente")) {
                Customer customer = new Customer(userId);
                ResultDataBase resultCustomer = customerRepository.createCustomer(customer);
                if (!resultCustomer.getSuccess()) {
                    connection.rollback();
                    return resultCustomer;
                }
            }

            // Registrar dirección
            ResultDataBase addressResult = addressRepository.createAddress(user, city, departament, province, street, streetNumber);
            if (!addressResult.getSuccess()) {
                connection.rollback();
                return addressResult;
            }

            // Confirmar transacción
            connection.commit();
            return new ResultDataBase(true, "Usuario registrado con éxito", null);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultDataBase(false, "Error durante el registro del usuario: " + e.getMessage(), null);
        }
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

}
