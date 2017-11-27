package ch.propulsion.walmazon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table( name = "token" )
@Data
@EqualsAndHashCode(of = {"token", "dateCreated", "user"})
@ToString(exclude = { "dateCreated" })
public class Token implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY COLUMN DEFINITION ------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// Token (Encrypted)
	@JsonView(JsonViews.Public.class)
	@Id
	@Column(nullable = false, length = 76) // BCrypt encoded passwords can need 50-76 characters.
	private String token;
	
	// Date Created
	@Column(name = "date_created", updatable = false, nullable = false)
	private LocalDateTime dateCreated = LocalDateTime.now();

	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY RELATIONS --------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	@JsonView(JsonViews.Public.class)
	@ManyToOne(optional = false)
	private User user;
	
	
	//--------------------------------------------------------------------------------------------
	//------ CONSTRUCTORS ------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public Token() {
		/* default constructor: required by JPA */
	}

	public Token(User user, String token) {
		this.user = user;
		this.token = token;
	}
	
}
