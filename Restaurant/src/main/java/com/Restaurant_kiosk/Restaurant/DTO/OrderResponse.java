package com.Restaurant_kiosk.Restaurant.DTO;


import java.util.List;
public record OrderResponse(
  Long id, String customerName, String paymentMethod, String status,
  String createdAt, Double totalAmount, List<Item> items
) {
  public record Item(Long menuItemId, String name, Double priceEach, int quantity) {}
}
