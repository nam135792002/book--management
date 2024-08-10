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
public class HistoryImportResponse {
    private Integer id;

    private int quantity;

    @JsonProperty("date_import")
    private String dateImport;

    @JsonProperty("user_import")
    private String userImport;
}
