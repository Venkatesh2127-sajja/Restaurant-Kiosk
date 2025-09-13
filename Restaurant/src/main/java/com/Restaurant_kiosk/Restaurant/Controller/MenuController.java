package com.Restaurant_kiosk.Restaurant.Controller;





import org.springframework.web.bind.annotation.*;

import com.Restaurant_kiosk.Restaurant.Entity.MenuItem;
import com.Restaurant_kiosk.Restaurant.Repository.MenuItemRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
  private final MenuItemRepository repo;

  @GetMapping
  public List<MenuItem> list(@RequestParam(required = false) String category,
                             @RequestParam(required = false) String search) {
    if (category != null && !category.equalsIgnoreCase("All") && search != null && !search.isEmpty())
      return repo.findByCategoryIgnoreCaseAndNameContainingIgnoreCase(category, search);
    if (category != null && !category.equalsIgnoreCase("All"))
      return repo.findByCategoryIgnoreCase(category);
    if (search != null && !search.isEmpty())
      return repo.findByNameContainingIgnoreCase(search);
    return repo.findAll();
  }

  @PostMapping public MenuItem create(@RequestBody MenuItem m) { return repo.save(m); }
}


