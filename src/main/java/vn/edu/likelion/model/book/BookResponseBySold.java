package vn.edu.likelion.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseBySold extends BookResponse{

    @JsonProperty("total_sold")
    private int totalSold;
}
