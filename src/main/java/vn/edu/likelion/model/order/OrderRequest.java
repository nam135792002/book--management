package vn.edu.likelion.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int quantity;

    @JsonProperty("book_id")
    @NotNull(message = "Id of book must not be empty")
    private Integer bookId;

    @JsonProperty("user_id")
    @NotNull(message = "Id of user must not be empty")
    private Integer userId;
}
