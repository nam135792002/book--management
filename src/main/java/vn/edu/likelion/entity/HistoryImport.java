package vn.edu.likelion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "history_import")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "date_import", nullable = false)
    private LocalDate dateImport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public HistoryImport(int quantity, LocalDate dateImport, Book book, User user) {
        this.quantity = quantity;
        this.dateImport = dateImport;
        this.book = book;
        this.user = user;
    }
}
