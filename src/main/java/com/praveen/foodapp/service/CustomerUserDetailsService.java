package com.praveen.foodapp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.praveen.foodapp.model.USER_ROLE;
import com.praveen.foodapp.model.User;
import com.praveen.foodapp.repository.UserRepository;


//This service used to stop auto generating the password by spring boot 
@Service
public class CustomerUserDetailsService implements UserDetailsService  {

	static Logger logger= LogManager.getLogger(CustomerUserDetailsService.class);
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByEmail(username);
		if (user==null) {
			throw new UsernameNotFoundException("User not found with email" + username);
		}
		
		USER_ROLE role = user.getRole();
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
