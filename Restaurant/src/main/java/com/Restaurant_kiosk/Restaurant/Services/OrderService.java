package com.Restaurant_kiosk.Restaurant.Services;




import com.Restaurant_kiosk.Restaurant.DTO.CreateOrderRequest;
import com.Restaurant_kiosk.Restaurant.DTO.OrderResponse;
import com.Restaurant_kiosk.Restaurant.Entity.OrderStatus;
import com.Restaurant_kiosk.Restaurant.Repository.CustomerOrderRepository;

import java.util.List;



import java.util.List;

public interface OrderService {
  OrderResponse create(CreateOrderRequest req);
  List<OrderResponse> list();
  OrderResponse get(Long id);
  OrderResponse updateStatus(Long id, OrderStatus status);
}


