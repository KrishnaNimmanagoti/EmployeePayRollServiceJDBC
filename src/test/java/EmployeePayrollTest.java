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

    @Test
    public void calculatingSumByGenderTest() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        double sum = employeePayroll.calculatingSumByGender();
        double avg = employeePayroll.calculatingAverageByGender();
        double max = employeePayroll.calculatingMaxByGender();
        double min = employeePayroll.calculatingMinByGender();
        double count = employeePayroll.calculatingCountByGender();
        Assertions.assertEquals(2400000, sum);
        Assertions.assertEquals(1200000.0, avg);
        Assertions.assertEquals(2000000.0, max);
        Assertions.assertEquals(400000.0, min);
        Assertions.assertEquals(2.0, count);
    }
}
