package com.praveen.foodapp.service;

import java.util.List;

import com.praveen.foodapp.dto.RestaurantDto;
import com.praveen.foodapp.model.Restaurant;
import com.praveen.foodapp.model.User;
import com.praveen.foodapp.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);
	public Restaurant updateRestaurant(Long restauarantId, CreateRestaurantRequest updatedRestaurant) throws Exception;
	public void deleteRestaurant(Long restaurantId) throws Exception;
	public List<Restaurant> getAllRestauratns();
	public List<Restaurant> searchRestaurant();
	public Restaurant findRestaurantById(Long id) throws Exception;
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
	public Restaurant updateRestaurantStatus(Long id) throws Exception;

}
