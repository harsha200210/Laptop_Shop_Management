package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalaryDTO {
    private double salary;
    private double tax;
    private double ETF;
    private double EPF;
}
