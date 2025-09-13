package com.Restaurant_kiosk.Restaurant.Entity;




import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu_item")
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class MenuItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false) 
  private String name;
  @Column(length=600) 
  private String description;
  @Column(nullable=false) 
  private Double price;
  @Column(nullable=false) 
  private String category;
  @Column(name="image_url") 
  private String imageUrl;
}


