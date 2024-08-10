package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderServiceImpl orderService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody @Valid OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.buy(orderRequest), HttpStatus.CREATED);
    }
}
