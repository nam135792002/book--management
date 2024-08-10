package vn.edu.likelion.service;

import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.model.order.OrderResponse;

public interface OrderInterface {
    OrderResponse buy(OrderRequest orderRequest);
}
