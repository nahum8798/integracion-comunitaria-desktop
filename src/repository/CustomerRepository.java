package repository;

import connection.DataBase;
import connection.ResultDataBase;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRepository {
    private final DataBase db = DataBase.getInstance();

    public ResultDataBase createCustomer(Customer customer) {
        String query = "INSERT INTO customer (id_user, phone) VALUES (?, ?)";
        try (Connection conn = db.openConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, customer.getIdUser());
            ps.setString(2, customer.getPhone());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultDataBase(true, "Cliente registrado correctamente", null);
            }
            return new ResultDataBase(false, "No se pudo registrar el cliente", null);
        } catch (SQLException e) {
            return new ResultDataBase(false, "Error al registrar el cliente: " + e.getMessage(), null);
        }
    }
}
