package ch.propulsion.walmazon.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.propulsion.walmazon.domain.Role;
import ch.propulsion.walmazon.domain.User;
import ch.propulsion.walmazon.repository.RoleRepository;
import ch.propulsion.walmazon.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class DefaultUserService implements UserService, UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public DefaultUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	// Register new user
	@Transactional(readOnly = false)
	@Override
	public User registerNewUser(User user) {
		user.setId(null); // Make sure we are saving a new user and not accidentally updating an existing user.
		user.getReviews().clear(); // A new user cannot have reviews before the account exists.
		user.getRoles().clear();// Make sure every new user is assigned the USER role.
		Role userRole = roleRepository.findByName(Role.ROLE_USER).orElseThrow(
			() -> new EntityNotFoundException("Could not find Role with name [" + Role.ROLE_USER + "]"));
		user.getRoles().add(userRole);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		logger.trace("Registering new user [{}].", user);
		return userRepository.save(user);
	}

	// update user
	@Transactional(readOnly = false)
	@Override
	public void update(User user) {
		logger.trace("Updating user [{}].", user);
		userRepository.update(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
	}
	
	// delete user
	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) {
		logger.trace("Deleting user with ID [{}].", id);
		userRepository.delete(id);
	}

	// find user by id
	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {
		logger.trace("Finding user by ID [{}].", id);
		return userRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Could not find User with ID [" + id + "]"));
	}

	// find user by email
	@Transactional(readOnly = true)
	@Override
	public User findByEmail(String email) {
		logger.trace("Finding user by email [{}].", email);
		return userRepository.findByEmail(email).orElseThrow(
			() -> new EntityNotFoundException("Could not find User with email [" + email + "]"));
	}

	// find all users
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		logger.trace("Finding all users.");
		return userRepository.findAll();
	}

	// -- Spring Security: UserDetailsService ----------------------------------

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.trace("Loading user by username [{}] for Spring Security.", username);
		return userRepository.findByEmailWithRolesEagerlyLoaded(username).orElseThrow(
			() -> new UsernameNotFoundException("Could not find user with username [" + username + "]"));
	}
	
}
