package com.praveen.foodapp.request;

import java.util.List;

import com.praveen.foodapp.model.Address;
import com.praveen.foodapp.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {

	private Long id;
	private String name;
	private String description;
	private Address address;
	private ContactInformation contactInformation;
	private String cusineType;
	private String openingHours;
	private List<String> images;
	
	
}
