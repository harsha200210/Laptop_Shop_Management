package lk.ijse.Laptop_Shop_Management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    private int id;
    private String paymentType;
    private Date paymentDate;
    private String orderId;

    public Payment(String paymentType, Date paymentDate, String orderId) {
        this.paymentType = paymentType;
        this.paymentDate = paymentDate;
        this.orderId = orderId;
    }
}
