import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayroll {
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeePayroll?useSSL=false";
    private static final String user = "root";
    private static final String password = "krishna";
    List<EmployeePayrollData> employeePayrollData = new ArrayList<>();

    private Connection establishConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver found!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        listDrivers();
        try {
            System.out.println("\nConnecting to database: " + URL);
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Connection established with: " + connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }

    public List<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_details;";
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(id, name, gender, startDate, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employeePayrollData.forEach(data -> System.out.println(data.id
                +" "+data.name+" "+data.salary+" "+data.start+" "+data.gender));
        return employeePayrollData;
    }

    public double updateEmployeeData(double salary, String name) {
        String sql = String.format("update employee_details set salary = %.2f where name = '%s';", salary, name);
        try (Connection connection = this.establishConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (EmployeePayrollData data : employeePayrollData) {
            if(data.name.equals(name)){
                return data.salary;
            }
        }
        return 0.0;
    }
}
