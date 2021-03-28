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

    public double calculatingSumByGender() {
        establishConnection();
        ResultSet resultSet;
        double sum = 0;
        try {
            resultSet = statement.executeQuery("SELECT SUM(salary) FROM employee_details WHERE gender='M' GROUP BY gender;");
            resultSet.next();
            sum = resultSet.getDouble("SUM(salary)");
            System.out.println(sum);
            resultSet = statement.executeQuery("SELECT SUM(salary) FROM employee_details WHERE gender='F' GROUP BY gender;");
            resultSet.next();
            System.out.println(resultSet.getDouble("SUM(salary)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sum;
    }

    public double calculatingAverageByGender() {
        establishConnection();
        ResultSet resultSet;
        double avg = 0;
        try {
            resultSet = statement.executeQuery("SELECT AVG (salary) FROM employee_details WHERE gender='M' GROUP BY gender;");
            resultSet.next();
            avg = resultSet.getDouble("AVG (salary)");
            System.out.println(avg);
            resultSet = statement.executeQuery("SELECT AVG (salary) FROM employee_details WHERE gender='F' GROUP BY gender;");
            resultSet.next();
            System.out.println(resultSet.getDouble("AVG (salary)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return avg;
    }

    public double calculatingMaxByGender() {
        establishConnection();
        ResultSet resultSet;
        double max = 0;
        try {
            resultSet = statement.executeQuery("SELECT MAX(salary) FROM employee_details WHERE gender='M' GROUP BY gender;");
            resultSet.next();
            max = resultSet.getDouble("MAX(salary)");
            System.out.println(max);
            resultSet = statement.executeQuery("SELECT MAX(salary) FROM employee_details WHERE gender='F' GROUP BY gender;");
            resultSet.next();
            System.out.println(resultSet.getDouble("MAX(salary)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return max;
    }

    public double calculatingMinByGender() {
        establishConnection();
        ResultSet resultSet;
        double min = 0;
        try {
            resultSet = statement.executeQuery("SELECT MIN(salary) FROM employee_details WHERE gender='M' GROUP BY gender;");
            resultSet.next();
            min = resultSet.getDouble("MIN(salary)");
            System.out.println(min);
            resultSet = statement.executeQuery("SELECT MIN(salary) FROM employee_details WHERE gender='F' GROUP BY gender;");
            resultSet.next();
            System.out.println(resultSet.getDouble("MIN(salary)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return min;
    }

    public double calculatingCountByGender() {
        establishConnection();
        ResultSet resultSet;
        double count = 0;
        try {
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM employee_details WHERE gender='M' GROUP BY gender;");
            resultSet.next();
            count = resultSet.getDouble("COUNT(*)");
            System.out.println(count);
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM employee_details WHERE gender='F' GROUP BY gender;");
            resultSet.next();
            System.out.println(resultSet.getDouble("COUNT(*)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
}
