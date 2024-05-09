package lk.ijse.model;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Order {
    private String orderId;
    private String customerId;
    private Date date;


}
