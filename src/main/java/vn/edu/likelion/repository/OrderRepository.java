package vn.edu.likelion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.likelion.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
