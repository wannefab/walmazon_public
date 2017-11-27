package ch.propulsion.walmazon.service;

import ch.propulsion.walmazon.domain.Token;
import ch.propulsion.walmazon.domain.User;

public interface TokenService {
	
	Token registerNewToken(Token token);
	
	void deleteByToken(String token);
	
	Token generateNewToken(User user);
	
	User validateToken(String token);

}
