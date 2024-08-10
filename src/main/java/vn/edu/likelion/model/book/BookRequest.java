package vn.edu.likelion.model.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    private Integer id;

    @NotEmpty(message = "Name must not empty")
    @Size(min = 5, max = 80, message = "Name book must have 5-60 characters")
    private String name;

    private long price;

    @NotEmpty(message = "Publisher must not empty")
    private String publisher;

    @NotEmpty(message = "Author must not empty")
    private String author;
}
