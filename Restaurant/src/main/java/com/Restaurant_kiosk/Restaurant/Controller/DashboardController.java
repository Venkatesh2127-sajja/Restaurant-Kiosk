package com.Restaurant_kiosk.Restaurant.Controller;



import org.springframework.web.bind.annotation.*;

import com.Restaurant_kiosk.Restaurant.Repository.CustomerOrderRepository;
import com.Restaurant_kiosk.Restaurant.Repository.MenuItemRepository;

import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
  private final CustomerOrderRepository orderRepo;
  private final MenuItemRepository menuRepo;

  @GetMapping("/stats")
  public Map<String, Object> stats() {
    Map<String,Object> m = new HashMap<>();
    long totalOrders = orderRepo.count();
    double totalSales = orderRepo.findAll().stream().mapToDouble(o -> o.getTotalAmount()==null?0:o.getTotalAmount()).sum();
    Map<String, Long> catCounts = menuRepo.findAll().stream().collect(Collectors.groupingBy(mi->mi.getCategory(), Collectors.counting()));
    m.put("totalOrders", totalOrders);
    m.put("totalSales", totalSales);
    m.put("menuCategories", catCounts);
    return m;
  }
}

