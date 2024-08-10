package vn.edu.likelion.model.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryImportRequest {
    private int quantity;

    @JsonProperty("book_id")
    private Integer bookId;

    @JsonProperty("user_id")
    private Integer userId;
}
