package com.placetracker.PlaceTracker.controller;


import com.placetracker.PlaceTracker.configs.JwtTokenProvider;
import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import com.placetracker.PlaceTracker.domain.Role;
import com.placetracker.PlaceTracker.domain.User;
import com.placetracker.PlaceTracker.repositories.RoleRepository;
import com.placetracker.PlaceTracker.repositories.UserRepository;
import com.placetracker.PlaceTracker.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200",
		"http://jobtrackerusidtest-app.s3-website-ap-southeast-1.amazonaws.com" })
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
	private RoleRepository roleRepository;

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
			String mobileNumber = data.getMobileNumber();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobileNumber, data.getPassword()));
			User user = this.users.findByMobileNumber(mobileNumber);
			Set <Role> roles = new HashSet<>();
			roles.add(roleRepository.findByRole(user.getRole()));
			String token = jwtTokenProvider.createToken(mobileNumber, roles);
			Map<Object, Object> model = new HashMap<>();
			model.put("mobileNumber", mobileNumber);
			model.put("username", user.getFullname());
			model.put("token", token);
			model.put("id", user.getId());
			model.put("roles", user.getRole());
			model.put("organization", user.getOrganization());
			model.put("mentorMobileNumber", user.getMentorMobileNumber());
			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid email/password supplied");
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user) {
		User userExists = userService.findUserByMobileNumber(user.getMobileNumber());
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
		User userExists = userService.findUserByMobileNumber(user.getMobileNumber());
		if (userExists != null) {
			user.setId(userExists.getId());
			if(user.getPassword()== null || user.getPassword().isEmpty()) {
				user.setPassword(userExists.getPassword());
			}
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

	@GetMapping("/bycriteria")
	public List<User> getUsersByCriteria( @RequestParam("fullname")String fullname,
													@RequestParam("email")String email,
													@RequestParam("mobileNumber")String mobileNumber,
													@RequestParam("address")String address) throws Exception{
		return userService.findByCriteriaUser(new User(email, address, mobileNumber, fullname, ""));
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
