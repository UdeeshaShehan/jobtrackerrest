package com.placetracker.PlaceTracker.controller;


import com.placetracker.PlaceTracker.configs.JwtTokenProvider;
import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import com.placetracker.PlaceTracker.domain.User;
import com.placetracker.PlaceTracker.repositories.UserRepository;
import com.placetracker.PlaceTracker.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	UserRepository users;

	@Autowired
	private CustomUserDetailsService userService;

	@GetMapping("/all")
	public List<User> getAll() {
		return userService.findAllUser();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthBody data) {
		try {
			String username = data.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
			User user = this.users.findByEmail(username);
			String token = jwtTokenProvider.createToken(username, user.getRoles());
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			model.put("id", user.getId());
			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid email/password supplied");
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user) {
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
		}
		userService.saveUser(user);
		Map<Object, Object> model = new HashMap<>();
		model.put("message", "User registered successfully");
		return ok(model);
	}

	@PostMapping("/updateregitration")
	public ResponseEntity updateregister(@RequestBody User user) {
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			user.setId(userExists.getId());
			userService.saveUser(user);
			Map<Object, Object> model = new HashMap<>();
			model.put("message", "User update successfully");
			return ok(model);
		}
		throw new BadCredentialsException("Can't update");
	}

	@GetMapping("/byid/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") String id) throws Exception{
		return ResponseEntity.ok().body(userService.getUserById(id).get());
	}

	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") String id) throws Exception {
		User user =
				userService.getUserById(id)
						.orElseThrow(() -> new Exception());
		userService.deleteUserById(user.getId());
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
