package ch.propulsion.walmazon.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
public class ErrorMessages {
	
	private String INCORRECT_PASSWORD = "The given password was not correct";
	private String ACCESS_DENIED = "Access denied!";
	
	private String USER_UPDATE_ERROR = "User could no get updated!";
	private String USER_DELETE_ERROR = "User could no get deleted!";
	private String TOKEN_ERROR = "Given Token is not valid!";
	
	private String REVIEW_DELETE_ERROR = "Review could not be deleted, Users can only delete their own reviews and the Restaurant id must match";
	private String REVIEW_UPDATE_ERROR = "Review could not be deleted, Users can only delete their own reviews and the Restaurant id must match";
	
	
	private String UNKNOWN = "Unknown Error";
	
	
	@JsonView(JsonViews.Public.class)
    public LocalDateTime timestamp;
	
	@JsonView(JsonViews.Public.class)
    public String message;
	
	
    public ErrorMessages(String type) {
        this.timestamp = LocalDateTime.now();
        switch (type) {
        	case "INCORRECT_PASSWORD":
        		this.message = INCORRECT_PASSWORD;
        		break;
        	case "ACCESS_DENIED":
        		this.message = ACCESS_DENIED;
        		break;
        	case "USER_UPDATE_ERROR":
        		this.message = USER_UPDATE_ERROR;
        		break;
        	case "USER_DELETE_ERROR":
        		this.message = USER_DELETE_ERROR;
        		break;
        	case "TOKEN_ERROR":
        		this.message = TOKEN_ERROR;
        		break;
        	case "REVIEW_DELETE_ERROR":
        		this.message = REVIEW_DELETE_ERROR;
        		break;
        	case "REVIEW_UPDATE_ERROR":
        		this.message = REVIEW_UPDATE_ERROR;
        		break;
    		default:
    			this.message = UNKNOWN;
        		break;
        }
    }	
    
}
