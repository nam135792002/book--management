package vn.edu.likelion.service;

import vn.edu.likelion.model.ClassResponse;
import vn.edu.likelion.model.book.BookRequest;
import vn.edu.likelion.model.book.BookResponse;
import vn.edu.likelion.model.book.BookResponseBySold;
import vn.edu.likelion.model.book.BookResponseDetail;
import vn.edu.likelion.model.history.HistoryImportRequest;
import vn.edu.likelion.model.history.HistoryImportResponse;

import java.util.List;

public interface BookInterface {
    BookResponse addBook(BookRequest bookRequest);
    BookResponseDetail getABook(Integer bookId);
    ClassResponse getAllBook(int page, int size);
    BookResponse updateBook(BookRequest bookRequest);
    String deleteBook(Integer bookId);
    HistoryImportResponse importBook(HistoryImportRequest historyImportRequest);
    List<BookResponse> sortListByPrice();
    List<BookResponseBySold> sortListBySold();
    List<BookResponseBySold> getTopBestSeller();
    List<BookResponse> searchByKeyword(Integer id, String nameBook);
}
