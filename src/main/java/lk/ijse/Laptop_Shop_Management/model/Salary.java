package lk.ijse.Laptop_Shop_Management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Salary {
    private double salary;
    private double tax;
    private double ETF;
    private double EPF;
}
