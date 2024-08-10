package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsBookByName(String name);
    Book findBookByName(String name);
    @Modifying
    @Transactional
    @Query("update Book b set b.amount = ?1 where b.id = ?2")
    void updateBookByAmount(int amount, Integer bookId);
    @Query("SELECT b, COALESCE(SUM(o.quantity), 0) FROM Book b LEFT JOIN b.listOrders o GROUP BY b.id, b.amount, b.author, b.name, b.price, b.publisher")
    List<Object[]> findBooksOrderByQuantitySold();

}
