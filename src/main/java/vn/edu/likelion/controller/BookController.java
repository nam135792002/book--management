package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.Utility.AppConstant;
import vn.edu.likelion.model.ClassResponse;
import vn.edu.likelion.model.book.BookRequest;
import vn.edu.likelion.model.book.BookResponse;
import vn.edu.likelion.model.book.BookResponseBySold;
import vn.edu.likelion.model.history.HistoryImportRequest;
import vn.edu.likelion.service.impl.BookServiceImpl;
import vn.edu.likelion.service.impl.HistoryImportServiceImpl;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired private BookServiceImpl bookService;
    @Autowired private HistoryImportServiceImpl historyImportService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody @Valid BookRequest bookRequest){
        return new ResponseEntity<>(bookService.addBook(bookRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Integer bookId){
        return ResponseEntity.ok(bookService.getABook(bookId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
                                    @RequestParam(value = "size", defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size){
        ClassResponse listBooks = bookService.getAllBook(page, size);
        if (listBooks.getContent().isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid BookRequest bookRequest){
        return ResponseEntity.ok(bookService.updateBook(bookRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer bookId){
        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }

    @PostMapping("/import")
    public ResponseEntity<?> importBookIntoWarehouse(@RequestBody HistoryImportRequest historyImportRequest){
        return new ResponseEntity<>(bookService.importBook(historyImportRequest), HttpStatus.CREATED);
    }

    @GetMapping("/sort-price")
    public ResponseEntity<?> sortByPrice(){
        List<BookResponse> listBooks = bookService.sortListByPrice();
        if (listBooks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }

    @GetMapping("/sort-sold")
    public ResponseEntity<?> sortByAmountSold(){
        List<BookResponseBySold> listBooks = bookService.sortListBySold();
        if (listBooks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }

    @GetMapping("/best-seller")
    public ResponseEntity<?> listAllByBestSeller(){
        List<BookResponseBySold> listBooks = bookService.getTopBestSeller();
        if (listBooks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBookByIdOrName(@RequestParam(value = "id", required = false) Integer id,
                                                  @RequestParam(value = "name", required = false) String nameBook){
        List<BookResponse> listBooks = bookService.searchByKeyword(id, nameBook);
        if (listBooks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<?> searchBookByDateImport(@RequestParam(value = "start") String startDate,
                                                  @RequestParam(value = "end") String endDate){
        List<BookResponse> listBooks = historyImportService.searchByDate(LocalDate.parse(startDate), LocalDate.parse(endDate));
        if (listBooks.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(listBooks);
    }
}
