package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Salary {
    private int employeeId;
    private double salary;
    private double tax;
    private double ETF;
    private double EPF;

    public Salary(double salary, double tax, double ETF, double EPF) {
        this.salary = salary;
        this.tax = tax;
        this.ETF = ETF;
        this.EPF = EPF;
    }
}
