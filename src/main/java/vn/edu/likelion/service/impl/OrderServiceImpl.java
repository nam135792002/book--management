package vn.edu.likelion.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.Book;
import vn.edu.likelion.entity.Order;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.model.order.OrderResponse;
import vn.edu.likelion.repository.BookRepository;
import vn.edu.likelion.repository.OrderRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.OrderInterface;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements OrderInterface {

    @Autowired private OrderRepository orderRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public OrderResponse buy(OrderRequest orderRequest) {
        Book bookInDB = bookRepository.findById(orderRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", orderRequest.getBookId()));

        User userInDB = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", orderRequest.getUserId()));

        int totalBook = bookInDB.getAmount() - orderRequest.getQuantity();
        if (totalBook < 0){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Amount book doesn't enough to buy.");
        }

        Order order = new Order(LocalDate.now(),orderRequest.getQuantity(), bookInDB, userInDB);
        Order savedOrder = orderRepository.save(order);
        bookRepository.updateBookByAmount(totalBook, bookInDB.getId());

        return convertToOrderResponse(savedOrder);
    }

    private OrderResponse convertToOrderResponse(Order order){
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setDateOrder(order.getDateOrder().toString());
        orderResponse.setBookName(order.getBook().getName());
        orderResponse.setCustomerName(order.getUser().getFullName());
        return orderResponse;
    }
}
