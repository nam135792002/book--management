package vn.edu.likelion.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;

    @JsonProperty("date_order")
    private String dateOrder;

    private int quantity;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("customer_name")
    private String customerName;
}
