package com.placetracker.PlaceTracker.services;

import com.placetracker.PlaceTracker.domain.Role;
import com.placetracker.PlaceTracker.domain.User;
import com.placetracker.PlaceTracker.repositories.RoleRepository;
import com.placetracker.PlaceTracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	public User findUserByMobileNumber(String mobileNumber) {
	    return userRepository.findByMobileNumber(mobileNumber);
	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public List<User> findByCriteriaUser(User user) {
		List<User> users = userRepository.findByFullnameRegexAndEmailRegexAndMobileNumberRegexAndAddressRegex(user.getFullname()+".*",
				user.getEmail()+".*", user.getMobileNumber()+".*", user.getAddress()+".*");
		return users;
	}
	
	public void saveUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setEnabled(true);
	    //Role userRole = roleRepository.findByRole("USER");
		if(user.getRole() == null || user.getRole().isEmpty())
	    	user.setRole("USER");
		if(user.getOrganization() == null || user.getOrganization().isEmpty())
			user.setOrganization("DEFAULT");
	    userRepository.save(user);
	}

	/*public void updateUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		//Role userRole = roleRepository.findByRole("USER");
		if(user.getRole() == null || user.getRole().isEmpty())
			user.setRole("USER");
		userRepository.save(user);
	}*/

	/*public void saveAdminUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	public void updateAdminUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		userRepository.save(user);
	}*/
	
	@Override
	public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {

	    User user = userRepository.findByMobileNumber(mobileNumber);
	    if(user != null) {
	    	Set<Role> roles = new HashSet();
	    	roles.add(roleRepository.findByRole(user.getRole()));
	        List<GrantedAuthority> authorities = getUserAuthority(roles);
	        return buildUserForAuthentication(user, authorities);
	    } else {
	        throw new UsernameNotFoundException("username not found");
	    }
	}

	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}

	public void deleteUserById(String id) {
		 userRepository.deleteById(id);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    userRoles.forEach((role) -> {
	        roles.add(new SimpleGrantedAuthority(role.getRole()));
	    });

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
	    return grantedAuthorities;
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
	    return new org.springframework.security.core.userdetails.User(user.getMobileNumber(), user.getPassword(), authorities);
	}
}
