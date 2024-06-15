import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;

    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/xyz";
    private String user = "postgres";
    private String password = "password";

    private DatabaseConnectionManager()  {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DatabaseConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionManager();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
