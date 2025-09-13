package com.Restaurant_kiosk.Restaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Restaurant_kiosk.Restaurant.Entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {}

