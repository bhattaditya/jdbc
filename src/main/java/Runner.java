import java.sql.Connection;
import java.sql.DriverManager;

public class Runner {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/xyz";
        String user = "postgres";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            System.out.println(connection.isValid(5));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
