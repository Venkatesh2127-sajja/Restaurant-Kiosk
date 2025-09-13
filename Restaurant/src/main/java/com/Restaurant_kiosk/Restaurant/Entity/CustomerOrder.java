package com.Restaurant_kiosk.Restaurant.Entity;





import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer_order")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class CustomerOrder {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  private String customerName;

  @Enumerated(EnumType.STRING) 
  private PaymentMethod paymentMethod;
  @Enumerated(EnumType.STRING) 
  private OrderStatus status;
  private OffsetDateTime createdAt;
  private Double totalAmount;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name="order_id")
  private List<OrderItem> items = new ArrayList<>();
}


