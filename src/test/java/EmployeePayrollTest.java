import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {

    @Test
    public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList = employeePayroll.readData();
        Assertions.assertEquals(4, employeePayrollDataList.size());
    }

    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_ShouldReturn1() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        employeePayroll.readData();
        double salary = 3000000.0;
        String name = "krishna";
        double salaryUpdated = employeePayroll.updateEmployeeData(3000000.0, name);
        Assertions.assertEquals(salary, salaryUpdated);
    }
}
