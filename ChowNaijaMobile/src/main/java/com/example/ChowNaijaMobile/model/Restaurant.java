package com.example.ChowNaijaMobile.model;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int restaurantId;
    private String restaurantName;
    private String restaurantDescription;
    private int restaurantRating;
    private String restaurantLocation;

    // Image Data
    private String restaurauntImageName;
    private String restaurantImageType;
    @Lob
    private byte[] restaurantImageData;

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDescription() {
        return restaurantDescription;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        this.restaurantDescription = restaurantDescription;
    }

    public int getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(int restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurauntImageName() {
        return restaurauntImageName;
    }

    public void setRestaurauntImageName(String restaurauntImageName) {
        this.restaurauntImageName = restaurauntImageName;
    }

    public String getRestaurantImageType() {
        return restaurantImageType;
    }

    public void setRestaurantImageType(String restaurantImageType) {
        this.restaurantImageType = restaurantImageType;
    }

    public byte[] getRestaurantImageData() {
        return restaurantImageData;
    }

    public void setRestaurantImageData(byte[] restaurantImageData) {
        this.restaurantImageData = restaurantImageData;
    }
}
