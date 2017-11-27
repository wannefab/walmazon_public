package ch.propulsion.walmazon.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.walmazon.domain.ErrorMessages;
import ch.propulsion.walmazon.domain.JsonViews;
import ch.propulsion.walmazon.domain.Role;
import ch.propulsion.walmazon.domain.Token;
import ch.propulsion.walmazon.domain.User;
import ch.propulsion.walmazon.service.TokenService;
import ch.propulsion.walmazon.service.UserService;

@RestController
@RequestMapping("/users")
public class RestUserController {
	
	private final UserService userService;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public RestUserController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// Get user by ID
	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}")
	public User retrieveUserById(@PathVariable long id) {
		return userService.findById(id);
	}

	// Delete
	@JsonView(JsonViews.Public.class)
	@DeleteMapping("/delete")
	public Object DeleteUser(@RequestBody User postedUser) {
		User databaseUser = userService.findById(postedUser.getId());
		if(databaseUser.equals(postedUser) && passwordEncoder.matches(postedUser.getPassword(), databaseUser.getPassword())) {
			userService.deleteById(databaseUser.getId());
			return databaseUser;
		}
		return new ErrorMessages("USER_DELETE_ERROR");
	}
	
	// Update
	@JsonView(JsonViews.Public.class)
	@PutMapping("/update")
	public Object UpdateUser(@RequestBody Map<String,String> json ) {
		User databaseUser = userService.findByEmail(json.get("email"));
		User postedUser = new User();
		
		
		// Set ID
		postedUser.setId(databaseUser.getId());
		
		// Set title
		if(json.containsKey("title")) postedUser.setTitle(json.get("title"));
		else postedUser.setTitle(databaseUser.getTitle());

		// Set first name
		if(json.containsKey("firstName")) postedUser.setFirstName(json.get("firstName"));
		else postedUser.setFirstName(databaseUser.getFirstName());
		
		// Set last name
		if(json.containsKey("lastName")) postedUser.setLastName(json.get("lastName"));
		else postedUser.setLastName(databaseUser.getLastName());
		
		// Set address
		if(json.containsKey("address")) postedUser.setAddress(json.get("address"));
		else postedUser.setAddress(databaseUser.getAddress());
		
		// Set city
		if(json.containsKey("city")) postedUser.setCity(json.get("city"));
		else postedUser.setCity(databaseUser.getCity());

		// Set city
		if(json.containsKey("city")) postedUser.setCity(json.get("city"));
		else postedUser.setCity(databaseUser.getCity());
		
		// Set birthDate
		postedUser.setBirthDate(databaseUser.getBirthDate());
		
		// Set userName
		if(json.containsKey("userName")) postedUser.setUsername(json.get("userName"));
		else postedUser.setUsername(databaseUser.getUsername());
		
		// Set password
		if(json.containsKey("newPassword")) postedUser.setPassword(passwordEncoder.encode(json.get("newPassword")));
		else postedUser.setPassword(databaseUser.getPassword());
		
		
		if(databaseUser.equals(postedUser) && passwordEncoder.matches(json.get("password"), databaseUser.getPassword())) {
			if(json.containsKey("newEmail")) postedUser.setEmail(json.get("newEmail"));
			userService.update(postedUser);
			return postedUser;
		}
		return new ErrorMessages("USER_UPDATE_ERROR");
	}
	
	// Sign in
	@JsonView(JsonViews.User.class)
	@PostMapping("/sign_in")
	public Object signInUser(@RequestBody User postedUser) {
		User databaseUser = userService.findByEmail(postedUser.getEmail());
	
		if(databaseUser.equals(postedUser) && passwordEncoder.matches(postedUser.getPassword(), databaseUser.getPassword())) {
			Token token = tokenService.generateNewToken(databaseUser);
			tokenService.registerNewToken(token);
			return token;
		}
		return new ErrorMessages("INCORRECT_PASSWORD");
	}
	
	// Sign in for Admins only
	@JsonView(JsonViews.User.class)
	@PostMapping("/admin/sign_in")
	public Object signInAdmin(@RequestBody User postedUser) {
		User databaseUser = userService.findByEmail(postedUser.getEmail());
	
		if(databaseUser.equals(postedUser) && passwordEncoder.matches(postedUser.getPassword(), databaseUser.getPassword())) {
			//Restrict access to Admins
			Role accessRestrictionToRole = new Role();
			accessRestrictionToRole.setName("ROLE_ADMIN");
			if(databaseUser.hasRole(accessRestrictionToRole)) {
				Token token = tokenService.generateNewToken(databaseUser);
				tokenService.registerNewToken(token);
				return token;
			}
			else {
				return new ErrorMessages("ACCESS_DENIED");
			}
		}
		return new ErrorMessages("INCORRECT_PASSWORD");
	}
	
	
	// Sign up
	@JsonView(JsonViews.NewUser.class)
	@PostMapping("/sign_up")
	public User registerNewUser(@RequestBody User postedUser) {
		User savedUser = userService.registerNewUser(postedUser);
		return savedUser;
	}

}
