package lk.ijse.Laptop_Shop_Management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO {
    private String paymentType;
    private Date paymentDate;
    private String orderId;
}
