package com.example.ChowNaijaMobile.controller;

import com.example.ChowNaijaMobile.model.Restaurant;
import com.example.ChowNaijaMobile.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/a")
    public String hello(){
        return "Helloe";
    }

    @PostMapping("/restaurant")
    public ResponseEntity<?> addRestaurant(
            @RequestPart("restaurant") String restaurantJson,
            @RequestPart("image") MultipartFile image) {

        System.out.println("Raw JSON: " + restaurantJson);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Restaurant restaurant = mapper.readValue(restaurantJson, Restaurant.class);

            Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant, image);
            if (savedRestaurant == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please Make Sure All data is Submitted");
            }

            return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/restaurants")
    public ResponseEntity<?> getAllRestaurants(){
        List<Restaurant> restaurants = null;
        try {
            restaurants = restaurantService.getAllRestaurants();
            if(restaurants == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Restaurants found");
            }
            System.out.println(restaurants);
            return new ResponseEntity<>(restaurants,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping ("/restaurant/{id}")
    public ResponseEntity<?> getSingleProduct(@PathVariable int id){
        Restaurant restaurant = restaurantService.getASingleRestaurant(id);
        try {
            if(restaurant != null){
                return new ResponseEntity<>(restaurant,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
