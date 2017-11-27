package ch.propulsion.walmazon.service;

import java.util.List;

import ch.propulsion.walmazon.domain.User;

public interface UserService {
	
	User registerNewUser(User user);

	void update(User user);

	void deleteById(Long id);
	
	User findById(Long id);
	
	User findByEmail(String email);
	
	List<User> findAll();

}
