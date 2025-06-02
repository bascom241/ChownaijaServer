package com.example.ChowNaijaMobile.service;

import com.example.ChowNaijaMobile.model.Restaurant;
import com.example.ChowNaijaMobile.repro.RestaurantRepro;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepro restaurantRepro;

    public Restaurant addRestaurant(Restaurant restaurant, MultipartFile image) throws Exception{

        if(image.getSize() > 5 * 1024 * 1024){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(image.getInputStream())
                    .size(1024,1024)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream);
            byte[] resizedImage = outputStream.toByteArray();
            restaurant.setRestaurantImageData(resizedImage);
        }else{
            restaurant.setRestaurantImageData(image.getBytes());
        }
        restaurant.setRestaurauntImageName(image.getOriginalFilename());
        restaurant.setRestaurantImageType(image.getContentType());

        return restaurantRepro.save(restaurant);

    }


    public List<Restaurant> getAllRestaurants(){
        return restaurantRepro.findAll();
    }


    public Restaurant getASingleRestaurant(int id){
        return restaurantRepro.findById(id).orElse(null);
    }
}
