package com.Restaurant_kiosk.Restaurant.Services;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.Restaurant_kiosk.Restaurant.DTO.CreateOrderRequest;
import com.Restaurant_kiosk.Restaurant.DTO.OrderResponse;
import com.Restaurant_kiosk.Restaurant.Entity.CustomerOrder;
import com.Restaurant_kiosk.Restaurant.Entity.MenuItem;
import com.Restaurant_kiosk.Restaurant.Entity.OrderItem;
import com.Restaurant_kiosk.Restaurant.Entity.OrderStatus;
import com.Restaurant_kiosk.Restaurant.Entity.PaymentMethod;
import com.Restaurant_kiosk.Restaurant.Repository.CustomerOrderRepository;
import com.Restaurant_kiosk.Restaurant.Repository.MenuItemRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final CustomerOrderRepository orderRepo;
  private final MenuItemRepository menuRepo;

  @Override
  public OrderResponse create(CreateOrderRequest req) {
    var order = new CustomerOrder();
    order.setCustomerName(req.customerName());
    order.setPaymentMethod(PaymentMethod.valueOf(req.paymentMethod().toUpperCase()));
    order.setStatus(OrderStatus.PENDING);
    order.setCreatedAt(OffsetDateTime.now());

    double total = 0.0;
    for (var it : req.items()) {
      MenuItem mi = menuRepo.findById(it.menuItemId())
          .orElseThrow(() -> new IllegalArgumentException("Menu item not found: " + it.menuItemId()));
      var oi = OrderItem.builder().menuItem(mi).quantity(it.quantity()).priceEach(mi.getPrice()).build();
      order.getItems().add(oi);
      total += mi.getPrice() * it.quantity();
    }
    order.setTotalAmount(total);
    var saved = orderRepo.save(order);
    return map(saved);
  }

  @Override
  public List<OrderResponse> list() { return orderRepo.findAll().stream().map(this::map).toList();}

  @Override
  public OrderResponse get(Long id) { return orderRepo.findById(id).map(this::map).orElseThrow(); }

  @Override
  public OrderResponse updateStatus(Long id, OrderStatus status) {
    var ord = orderRepo.findById(id).orElseThrow();
    ord.setStatus(status);
    return map(orderRepo.save(ord));
  }

  private OrderResponse map(CustomerOrder o) {
    var items = o.getItems().stream().map(oi -> new OrderResponse.Item(
        oi.getMenuItem().getId(),
        oi.getMenuItem().getName(),
        oi.getPriceEach(),
        oi.getQuantity())).toList();
    return new OrderResponse(o.getId(), o.getCustomerName(),
        o.getPaymentMethod() != null ? o.getPaymentMethod().name() : null,
        o.getStatus() != null ? o.getStatus().name() : null,
        o.getCreatedAt() != null ? o.getCreatedAt().toString() : null,
        o.getTotalAmount(), items);
  }
}

