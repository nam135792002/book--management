package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Book;
import vn.edu.likelion.entity.HistoryImport;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.ClassResponse;
import vn.edu.likelion.model.book.BookRequest;
import vn.edu.likelion.model.book.BookResponse;
import vn.edu.likelion.model.book.BookResponseBySold;
import vn.edu.likelion.model.book.BookResponseDetail;
import vn.edu.likelion.model.history.HistoryImportRequest;
import vn.edu.likelion.model.history.HistoryImportResponse;
import vn.edu.likelion.repository.BookRepository;
import vn.edu.likelion.repository.HistoryImportRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.BookInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class BookServiceImpl implements BookInterface {

    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private HistoryImportRepository historyImportRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        if(bookRepository.existsBookByName(bookRequest.getName())){
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "Name book: '" + bookRequest.getName() + "' is existed in system.");
        }
        Book book = modelMapper.map(bookRequest, Book.class);
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }

    @Override
    public BookResponseDetail getABook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        return convertToBookResponseDetail(book);
    }

    @Override
    public ClassResponse getAllBook(int page, int size) {
        List<Book> books = bookRepository.findAll();

        int start = page * size;
        int end = Math.min(start + size, books.size());

        List<BookResponse> paginatedBooks = books.subList(start, end).stream().map(book -> modelMapper.map(book, BookResponse.class)).toList();

        ClassResponse response = new ClassResponse();
        response.setContent(paginatedBooks);
        response.setPageNo(page);
        response.setPageSize(size);
        response.setTotalElements(books.size());
        response.setTotalPage((int) Math.ceil((double) books.size() / size));
        response.setLast(end >= books.size());

        return response;

    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest) {
        Book bookInDB = bookRepository.findById(bookRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookRequest.getId()));

        Book checkNameDuplicated = bookRepository.findBookByName(bookRequest.getName());
        if (checkNameDuplicated != null){
            if (!Objects.equals(bookInDB.getId(), checkNameDuplicated.getId())){
                throw new ApiException(HttpStatus.BAD_REQUEST,
                        "Name book: '" + bookRequest.getName() + "' is existed in system.");
            }
        }

        bookInDB.setName(bookRequest.getName());
        bookInDB.setAuthor(bookRequest.getAuthor());
        bookInDB.setPublisher(bookRequest.getPublisher());
        bookInDB.setPrice(bookRequest.getPrice());

        return modelMapper.map(bookRepository.save(bookInDB), BookResponse.class);
    }

    @Override
    public String deleteBook(Integer bookId) {
        Book bookInDB = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        bookRepository.delete(bookInDB);
        return "Delete book successfully";
    }

    @Override
    public HistoryImportResponse importBook(HistoryImportRequest historyImportRequest) {
        Book bookInDB = bookRepository.findById(historyImportRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", historyImportRequest.getBookId()));

        User userInDB = userRepository.findById(historyImportRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", historyImportRequest.getUserId()));

        int totalBook = bookInDB.getAmount() + historyImportRequest.getQuantity();

        HistoryImport historyImport = new HistoryImport(historyImportRequest.getQuantity(), LocalDate.now(), bookInDB, userInDB);
        HistoryImport savedImportBook = historyImportRepository.save(historyImport);

        bookRepository.updateBookByAmount(totalBook, bookInDB.getId());

        return convertToHistoryImportResponse(savedImportBook);
    }

    @Override
    public List<BookResponse> sortListByPrice() {
        List<Book> listBooks = bookRepository.findAll();
        for (int i = 0; i < listBooks.size() - 1; i++){
            for (int j = i + 1; j < listBooks.size(); j++){
                if (listBooks.get(i).getPrice() < listBooks.get(j).getPrice()){
                    Book book = listBooks.get(i);
                    listBooks.set(i, listBooks.get(j));
                    listBooks.set(j, book);

                }
            }
        }

        return listBooks.stream().map(book -> modelMapper.map(book, BookResponse.class)).toList();
    }

    @Override
    public List<BookResponseBySold> sortListBySold() {
        List<Object[]> results = bookRepository.findBooksOrderByQuantitySold();
        List<BookResponseBySold> listBookSold = new ArrayList<>();

        for (Object[] result : results) {
            Book book = (Book) result[0];
            int totalSold = ((Number) result[1]).intValue();
            listBookSold.add(convertToBookResponseSold(book,totalSold));
        }

        for (int i = 0; i < listBookSold.size() - 1; i++){
            for (int j = i + 1; j < listBookSold.size(); j++){
                if (listBookSold.get(i).getTotalSold() < listBookSold.get(j).getTotalSold()){
                    BookResponseBySold book = listBookSold.get(i);
                    listBookSold.set(i, listBookSold.get(j));
                    listBookSold.set(j, book);

                }
            }
        }

        return listBookSold;
    }

    @Override
    public List<BookResponseBySold> getTopBestSeller() {
        List<BookResponseBySold> listBooks = sortListBySold();
        List<BookResponseBySold> listBestSeller = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            listBestSeller.add(listBooks.get(i));
        }
        return listBestSeller;
    }

    @Override
    public List<BookResponse> searchByKeyword(Integer id, String nameBook) {
        List<Book> listBooks = bookRepository.findAll();
        return listBooks.stream()
                .filter(book -> ((id != null && id.equals(book.getId())) ||
                (nameBook != null && book.getName().toLowerCase().contains(nameBook.toLowerCase()))))
                .map(book -> modelMapper.map(book, BookResponse.class))
                .toList();

    }

    private HistoryImportResponse convertToHistoryImportResponse(HistoryImport historyImport){
        HistoryImportResponse historyImportResponse = modelMapper.map(historyImport, HistoryImportResponse.class);
        historyImportResponse.setDateImport(historyImport.getDateImport().toString());
        historyImportResponse.setUserImport(historyImport.getUser().getFullName());
        return historyImportResponse;
    }

    private BookResponseDetail convertToBookResponseDetail(Book book){
        BookResponseDetail bookResponseDetail = modelMapper.map(book, BookResponseDetail.class);
        for (HistoryImport historyImport : book.getListHistoryImports()){
            bookResponseDetail.getListHistoryImport().add(convertToHistoryImportResponse(historyImport));
        }
        return bookResponseDetail;
    }

    private BookResponseBySold convertToBookResponseSold(Book book, int totalSold){
        BookResponseBySold bookResponseBySold = modelMapper.map(book, BookResponseBySold.class);
        bookResponseBySold.setTotalSold(totalSold);
        return bookResponseBySold;
    }
}
