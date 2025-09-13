package com.Restaurant_kiosk.Restaurant.Entity;



import jakarta.persistence.*;
import lombok.*;




import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_item")
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class OrderItem {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
  private Long id;

  @ManyToOne(optional=false) 
  private MenuItem menuItem;
  @Column(nullable=false) 
  private Integer quantity;
  @Column(nullable=false) 
  private Double priceEach;
}


