package com.Restaurant_kiosk.Restaurant.DTO;

import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(
  @NotBlank String customerName,
  @NotBlank String paymentMethod,
  @NotEmpty List<Item> items
) {
  public record Item(@NotNull Long menuItemId, @Min(1) int quantity) {}
}

