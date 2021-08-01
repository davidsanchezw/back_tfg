package com.uc3m.tfg.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uc3m.tfg.model.Group;
import com.uc3m.tfg.model.User;
import com.uc3m.tfg.service.GroupService;
import com.uc3m.tfg.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	// Get all users
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping
	public List<User> getAllUsers(){
		List<User> users = StreamSupport
				.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return users;
	}
	
	// Create user
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	
	// Get user by id
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		
		Optional<User> oUser = userService.findById(id);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
	}
	
	// Update user
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails){
		Optional<User> user = userService.findById(id);
		
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails,user.get());
		user.get().setFirstName(userDetails.getFirstName());
		user.get().setLastName(userDetails.getLastName());
		user.get().setEmail(userDetails.getEmail());
		user.get().setHash(userDetails.getHash());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
	}
	
	// Delete user
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id){
		if(!userService.findById(id).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	// Get users by group
		@CrossOrigin(origins = "http://localhost:4200")
		@GetMapping("/listByGrouId/{id}")
		public List<User> getUsersByGroupId(@PathVariable Long id){
			Optional<Group> group = groupService.findById(id);
			List<User> users = StreamSupport
					.stream(userService.findByGroup(group.get()).spliterator(), false)
					.collect(Collectors.toList());
			return users;
		}
		
		// login
		@CrossOrigin(origins = "http://localhost:4200")
		@PutMapping("/login")
		public ResponseEntity<?> login(@RequestBody User userDetails){
			Optional<User> user = userService.findByEmail(userDetails.getEmail());
			
			if(!user.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			
			if (user.get().getHash().compareTo(userDetails.getHash()) == 0) {
				return ResponseEntity.ok(user.get());
			}
			return ResponseEntity.notFound().build();
		}
		
//		private String getJWTToken(String username) {
//			String secretKey = "mySecretKey";
//			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
//					.commaSeparatedStringToAuthorityList("ROLE_USER");
//			
//			String token = Jwts
//					.builder()
//					.setId("softtekJWT")
//					.setSubject(username)
//					.claim("authorities",
//							grantedAuthorities.stream()
//									.map(GrantedAuthority::getAuthority)
//									.collect(Collectors.toList()))
//					.setIssuedAt(new Date(System.currentTimeMillis()))
//					.setExpiration(new Date(System.currentTimeMillis() + 600000))
//					.signWith(SignatureAlgorithm.HS512,
//							secretKey.getBytes()).compact();
//
//			return "Bearer " + token;
//		}
		
//		// Get users by token
//				@CrossOrigin(origins = "http://localhost:4200")
//				@GetMapping("/token")
//				public List<User> getUsersBToken(@PathVariable Long id){
//					Optional<Group> group = groupService.findById(id);
//					List<User> users = StreamSupport
//							.stream(userService.findByGroup(group.get()).spliterator(), false)
//							.collect(Collectors.toList());
//					return users;
//				}
	
}
