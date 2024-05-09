package lk.ijse.model.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString@EqualsAndHashCode

public class OrderDetailTm {
    private String orderId;
    private String code;
    private int qty;
    private double unitPrice;

}
