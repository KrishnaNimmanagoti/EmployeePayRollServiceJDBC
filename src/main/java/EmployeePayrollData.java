import java.time.LocalDate;

public class EmployeePayrollData {
    public int id;
    public String name;
    public String gender;
    public LocalDate start;

    public EmployeePayrollData(int id, String name, String gender, LocalDate start) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.start = start;
    }
}