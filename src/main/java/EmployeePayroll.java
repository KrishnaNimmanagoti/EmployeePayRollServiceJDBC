import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayroll {
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeePayroll?useSSL=false";
    private static final String user = "root";
    private static final String password = "krishna";
    public List<EmployeePayrollData> employeePayrollData = new ArrayList<>();
    public Connection connection;
    public Statement statement = null;

    private void establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        listDrivers();
    }

    private void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println("Driver: " + driverClass.getClass().getName());
        }
    }

    public List<EmployeePayrollData> readData(String sql) {
        try {
            try {
                if (connection == null || connection.isClosed())
                    throw new CustomException("Connection is not established");
            }
            catch (CustomException e) {
                System.out.println(e);
                establishConnection();
            }
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                String gender = resultSet.getString("gender");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollData.add(new EmployeePayrollData(id, name, gender, startDate, salary));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        employeePayrollData.forEach(data -> System.out.println(data.id
                +" "+data.name+" "+data.salary+" "+data.start+" "+data.gender));
        return employeePayrollData;
    }

    public double updateEmployeeData(double salary, String name) {
        try {
            establishConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update employee_details set salary=? where name =?");
            preparedStatement.setDouble(1, salary);
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM employee_details;";
        this.readData(sql);
        for (EmployeePayrollData data : employeePayrollData) {
            if(data.name.equals(name)){
                return data.salary;
            }
        }
        return 0.0;
    }
}
