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
    public void updatingValueTest() {
        EmployeePayroll employeePayroll = new EmployeePayroll();
        double salary = 2000000.0;
        String name = "krishna";
        double salaryUpdated = employeePayroll.updateEmployeeData(salary, name);
        Assertions.assertEquals(salary, salaryUpdated);
    }
}
