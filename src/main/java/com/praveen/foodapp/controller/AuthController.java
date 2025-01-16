package com.praveen.foodapp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.praveen.foodapp.configuration.JwtProvider;
import com.praveen.foodapp.model.Cart;
import com.praveen.foodapp.model.USER_ROLE;
import com.praveen.foodapp.model.User;
import com.praveen.foodapp.repository.CartRepository;
import com.praveen.foodapp.repository.UserRepository;
import com.praveen.foodapp.request.LoginRequest;
import com.praveen.foodapp.response.AuthRepsonse;
import com.praveen.foodapp.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	//This method is used for a new account creation through login page
	@PostMapping("/signup")
	public ResponseEntity<AuthRepsonse> createUserHandler(@RequestBody User user) throws Exception{
		
		User isEmailExist = userRepository.findByEmail(user.getEmail());
		
		if (isEmailExist!=null) {
			throw new Exception("Account already exists with this email!!");
		}
		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		//here beCrypt the password
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(createdUser);
		
		Cart cart= new Cart();
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication= new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//here we generate jwt token from authentication details 
		String jwt=jwtProvider.generateToken(authentication);
		AuthRepsonse authRepsonse= new AuthRepsonse();
		authRepsonse.setJwt(jwt);
		authRepsonse.setMessage("register Success");
		authRepsonse.setRole(savedUser.getRole());
		
		return new ResponseEntity<AuthRepsonse>(authRepsonse, HttpStatus.CREATED);
	}
	
	//this method is used for the signin where we've already have an account
	@PostMapping("/signin")
	public ResponseEntity<AuthRepsonse> signIn(@RequestBody LoginRequest req) throws Exception{
        String useranme = req.getEmail();
        String password = req.getPassword();
        
        Authentication authentication = authenticate(useranme, password);
        Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
        String role = authorities.isEmpty()? null : authorities.iterator().next().getAuthority();
      //here we generate jwt token from authentication details 
      		String jwt=jwtProvider.generateToken(authentication);
      		AuthRepsonse authRepsonse= new AuthRepsonse();
      		authRepsonse.setJwt(jwt);
      		authRepsonse.setMessage("login Success");
      		authRepsonse.setRole(USER_ROLE.valueOf(role));
      		
      		return new ResponseEntity<AuthRepsonse>(authRepsonse, HttpStatus.OK);
		
	}

	//this method is used to check the mail and password are matching from the database
	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
		
		if(!userDetails.getUsername().equals(username)||userDetails == null) {
			throw new BadCredentialsException("invalid username!!");
		}
		if(!passwordEncoder.matches(password,userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password!!");
		}
		
		return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
	}
	
	
	
}
