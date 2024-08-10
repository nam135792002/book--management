package vn.edu.likelion.model.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    protected Integer id;
    protected String name;
    protected long price;
    protected String publisher;
    protected String author;
    protected int amount;
}
