package ch.propulsion.walmazon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.propulsion.walmazon.domain.Token;
import ch.propulsion.walmazon.domain.User;
import ch.propulsion.walmazon.repository.TokenRepository;

@Transactional(readOnly = true)
@Service
public class DefaultTokenService implements TokenService{
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);
	
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public DefaultTokenService(TokenRepository tokenRepository, PasswordEncoder passwordEncoder ) {
		this.tokenRepository = tokenRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	@Override
	public Token generateNewToken(User user) {
		String token = UUID.randomUUID().toString() +  user.getId().toString();
		Token newToken = new Token(user, token);
		return newToken;
	}
	
	@Transactional(readOnly = false)
	@Override
	public Token registerNewToken(Token token) {
		Token encryptedToken = new Token();
		
		encryptedToken.setDateCreated(token.getDateCreated());
		encryptedToken.setUser(token.getUser());
		
		// Encrypt token before storing in database.
		encryptedToken.setToken(passwordEncoder.encode(token.getToken()));
		
		//Delete all old existing tokens
		tokenRepository.deleteByUserId(token.getUser().getId());
		
		logger.trace("Registering new token [{}].", encryptedToken);
		return tokenRepository.save(encryptedToken);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteByToken(String token) {
		logger.trace("Deleting token with token [{}].", token);
		tokenRepository.delete(token);
	}

	@Override
	public User validateToken(String token) {
		String UserID = token.substring(36);

		List<Token> tokens = tokenRepository.findByUserId(Long.parseLong(UserID));
		
		for(Token tokenFromDB:tokens) {
			if( tokenFromDB.getDateCreated().plusMinutes(30).isAfter(LocalDateTime.now())  ) {
				if(passwordEncoder.matches(token, tokenFromDB.getToken())) {
					return tokenFromDB.getUser();
				}
			}
			else {
				tokenRepository.deleteByToken(tokenFromDB.getToken());
			}
		}
		return null;
	}
	
}
