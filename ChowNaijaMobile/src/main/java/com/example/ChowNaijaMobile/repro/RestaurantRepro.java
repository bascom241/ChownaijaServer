package com.example.ChowNaijaMobile.repro;

import com.example.ChowNaijaMobile.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepro extends JpaRepository<Restaurant, Integer> {
    Restaurant findByRestaurantName(String restaurantName);
}
