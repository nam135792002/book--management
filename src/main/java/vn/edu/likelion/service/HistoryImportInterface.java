package vn.edu.likelion.service;

import vn.edu.likelion.entity.Book;
import vn.edu.likelion.model.book.BookResponse;

import java.time.LocalDate;
import java.util.List;

public interface HistoryImportInterface {
    List<BookResponse> searchByDate(LocalDate startDate, LocalDate endDate);
}
