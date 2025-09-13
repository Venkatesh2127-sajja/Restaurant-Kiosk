package com.Restaurant_kiosk.Restaurant.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.Restaurant_kiosk.Restaurant.Entity.MenuItem;

import java.util.List;
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
  List<MenuItem> findByCategoryIgnoreCase(String category);
  List<MenuItem> findByNameContainingIgnoreCase(String name);
  List<MenuItem> findByCategoryIgnoreCaseAndNameContainingIgnoreCase(String category, String name);
}


