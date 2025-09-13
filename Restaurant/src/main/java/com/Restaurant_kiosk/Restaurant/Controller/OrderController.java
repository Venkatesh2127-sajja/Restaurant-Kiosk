package com.Restaurant_kiosk.Restaurant.Controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.Restaurant_kiosk.Restaurant.DTO.CreateOrderRequest;
import com.Restaurant_kiosk.Restaurant.DTO.OrderResponse;
import com.Restaurant_kiosk.Restaurant.Entity.OrderStatus;
import com.Restaurant_kiosk.Restaurant.Services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService service;

  @GetMapping 
  public List<OrderResponse> list() {
	  return service.list(); 
	  }
  @GetMapping("/{id}") 
  public OrderResponse get(@PathVariable Long id) { 
	  return service.get(id);
	  }
  @PostMapping 
  public OrderResponse create(@Valid @RequestBody CreateOrderRequest req) { 
	  return service.create(req);
	  }
  @PutMapping("/{id}/status") 
  public OrderResponse updateStatus(@PathVariable Long id, @RequestParam String status) {
    return service.updateStatus(id, OrderStatus.valueOf(status.toUpperCase()));
  }
}
