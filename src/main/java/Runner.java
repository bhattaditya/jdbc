import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {

        Scanner scanner = SingletonScanner.getInstance().getScanner();
        boolean run = false;

        while(!run) {
            Runner.menu();

            try {

                int input = scanner.nextInt();

                if (input == 1) {
                    Runner.initialize();
                } else if (input == 2) {
                    Runner.saveEmployee();
                } else if (input == 3) {
                    Runner.viewEmployees();
                } else if (input == 4) {
                    run = true;
                    scanner.close();
                    System.exit(1);

                } else{
                    System.out.println("Provide input in given range");
                }

            } catch(Exception ex) {
                System.out.println("Numbers are allowed only");
                scanner.nextLine();
            }

            System.out.println("\n\nPlease enter choice ...\n\n");
        }

    }

    private static void menu() {
        System.out.println("---Employee Management---");
        System.out.println("1. Initialize");
        System.out.println("2. Save Employee");
        System.out.println("3. View Employee List");
        System.out.println("4. Exit");
    }

    private static void saveEmployee() {

        Scanner scanner = SingletonScanner.getInstance().getScanner();
        System.out.println("Enter employee details");

        // Consume newline character left from previous input or prompt
        scanner.nextLine();

        // Ensure name input is read correctly
        System.out.print("Name: ");
        String name = scanner.nextLine();

        // Ensure age input is read correctly
        System.out.print("Age: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer for age.");
            scanner.next(); // discard the invalid input
        }
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume the leftover newline

        // Ensure gender input is read correctly
        System.out.print("Gender: ");
        String gender = scanner.nextLine();

        // Ensure department input is read correctly
        System.out.print("Department: ");
        String department = scanner.nextLine();

        // Ensure date of joining input is read correctly
        System.out.print("Date of joining: ");
        String dateOfJoining = scanner.nextLine();

        // Ensure salary input is read correctly
        System.out.print("Salary: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number for salary.");
            scanner.next(); // discard the invalid input
        }
        double salary = scanner.nextDouble();
        scanner.nextLine();  // Consume the leftover newline


        Employee employee = new Employee();
        employee.setName(name);
        employee.setAge(age);
        employee.setGender(gender);
        employee.setDepartment(department);
        employee.setDateOfJoining(LocalDate.now());
        employee.setSalary(salary);

        System.out.println(employee);


        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();

            String query = "INSERT into dbo.employee(name, age, gender, department, date_of_joining, salary) values(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setInt(2, employee.getAge());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setDate(5, Date.valueOf(employee.getDateOfJoining()));
            preparedStatement.setDouble(6, employee.getSalary());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                System.out.println("Inserted Successfully!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void viewEmployees() {

        try {
            Connection connection = DatabaseConnectionManager.getInstance().getConnection();

            String query = "SELECT * from dbo.employee";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                System.out.println("Name: " + name + " works in Department: " +  department);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Deprecated
    private static void previousCode() {
        String url = "jdbc:postgresql://localhost:5432/xyz";
        String user = "postgres";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            if (connection.isValid(5)) {
                System.out.println("Connection is valid.");
                List<Employee> employees = Runner.initialize();

                // Inserting
                String insertQuery = "INSERT into dbo.employee (name, age, gender, department, date_of_joining, salary) values (?,?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                for (Employee employee : employees) {
                    preparedStatement.setString(1, employee.getName());
                    preparedStatement.setInt(2, employee.getAge());
                    preparedStatement.setString(3, employee.getGender());
                    preparedStatement.setString(4, employee.getDepartment());
                    preparedStatement.setDate(5, Date.valueOf(employee.getDateOfJoining()));
                    preparedStatement.setDouble(6, employee.getSalary());

                    int rows = preparedStatement.executeUpdate();

                    if (rows == 1) {
                        System.out.println("Inserted Successfully: " + employee.getName());
                    }
                }

                // retrieving
                String selectQuery = "SELECT * from dbo.employee";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectQuery);

                while(resultSet.next()) {
                    String name = resultSet.getString("name");
                    String department = resultSet.getString("department");
                    System.out.println("Name: " + name + " works in Department: " +  department);
                }


            } else {
                System.out.println("Failed to validate the connection.");
            }

        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    private static List<Employee> initialize() {
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", LocalDate.of(2024, 1, 20), 25000.0));
        employeeList.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", LocalDate.of(2023, 12, 2), 13500.0));
        employeeList.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", LocalDate.of(2024, 3, 10), 18000.0));
        employeeList.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", LocalDate.of(2022, 4, 26), 32500.0));
        employeeList.add(new Employee(155, "Nima Roy", 27, "Female", "HR", LocalDate.of(2023, 5, 19), 22700.0));
        employeeList.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", LocalDate.of(2023, 1, 16), 10500.0));
        employeeList.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", LocalDate.of(2023, 10, 9), 27000.0));
        employeeList.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", LocalDate.of(2021, 6, 12), 34500.0));
        employeeList.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", LocalDate.of(2024, 5, 1), 11500.0));
        employeeList.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", LocalDate.of(2024, 2, 20), 11000.5));
        employeeList.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", LocalDate.of(2023, 8, 10), 15700.0));
        employeeList.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", LocalDate.of(2023, 5, 12), 28200.0));
        employeeList.add(new Employee(233, "Jyothi Reddy", 27, "Female", "Account And Finance", LocalDate.of(2023, 5, 12), 21300.0));
        employeeList.add(new Employee(244, "Nicolus Den", 24, "Male", "Sales And Marketing", LocalDate.of(2024, 6, 1), 10700.5));
        employeeList.add(new Employee(255, "Ali Baig", 23, "Male", "Infrastructure", LocalDate.of(2024, 1, 15), 12700.0));
        employeeList.add(new Employee(266, "Sanvi Pandey", 26, "Female", "Product Development", LocalDate.of(2022, 10, 19), 28900.0));
        employeeList.add(new Employee(277, "Anuj Chettiar", 31, "Male", "Product Development", LocalDate.of(2024, 6, 10), 35700.0));

        return employeeList;
    }
}
