package connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static DataBase instance;
    private Connection connection;
    
    private static final String USER = "root";
    private static final String PASSWORD = "nM1258menMa";
    private static final String DATABASE = "integracion_comunitaria";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String CONNECTION_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    // Constructor privado para patrón Singleton
    private DataBase() {}

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    // Abrir conexión
    public Connection openConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/integracion_comunitaria"; // Cambia a tu configuración
        String user = "root";
        String password = "nM1258menMa";
    
        return DriverManager.getConnection(url, user, password);
    }
    

    // Cerrar conexión
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
            }
        }
    }

    // Método para ejecutar consultas de escritura (INSERT, UPDATE, DELETE)
    public boolean executeUpdate(String sql, Object... params) {
        try (Connection conn = openConnection();  // Obtén una nueva conexión
             PreparedStatement ps = conn.prepareStatement(sql)) {  // Usa la conexión para preparar la consulta
    
            // Configura los parámetros en el PreparedStatement
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
    
            // Ejecuta la consulta
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Retorna true si se afectaron filas
        } catch (SQLException e) {
            // Manejo de errores
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "Error en executeUpdate", e);
            return false;
        }
    }
    
    

    // Método para ejecutar consultas de lectura (SELECT)
    public List<List<Object>> executeQuery(String query, Object... params) {
        List<List<Object>> result = new ArrayList<>();
        try (Connection conn = openConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            setParameters(statement, params);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, "Error en executeQuery", ex);
        }
        return result;
    }

    // Método privado para asignar parámetros al PreparedStatement
    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
