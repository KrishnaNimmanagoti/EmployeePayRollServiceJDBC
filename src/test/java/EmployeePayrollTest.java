import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_details;";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assertions.assertEquals(4, employeePayrollDataList.size());
    }

    @Test
    public void updatingValueTest() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        double salary = 2000000.0;
        String name = "krishna";
        double salaryUpdated = employeePayroll.updateEmployeeData(salary, name);
        Assertions.assertEquals(salary, salaryUpdated);
    }

    @Test
    public void retrieveDataByDateRangeTest() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        String sql = "SELECT * FROM employee_details WHERE start BETWEEN CAST('2015-01-01' AS DATE) AND DATE(NOW());";
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData(sql);
        Assertions.assertEquals(2, employeePayrollDataList.size());

    }
}
