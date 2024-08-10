package vn.edu.likelion.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.likelion.model.history.HistoryImportResponse;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDetail extends BookResponse{

    @JsonProperty("list_history_imports")
    private List<HistoryImportResponse> listHistoryImport = new ArrayList<>();
}
