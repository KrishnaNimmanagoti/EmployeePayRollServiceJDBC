import java.sql.*;
import java.util.Enumeration;

public class EmployeePayroll {
    private static final String URL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private static final String user = "root";
    private static final String password = "krishna";

    private void establishConnection() {
        Connection connection;
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
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }

    public static void main(String[] args) {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.establishConnection();
    }
}