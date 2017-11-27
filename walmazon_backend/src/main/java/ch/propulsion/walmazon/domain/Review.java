package ch.propulsion.walmazon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table( name = "review" )
@Data
@EqualsAndHashCode(of = {"text", "dateCreated", "rating", "user", "product" })
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;


	//--------------------------------------------------------------------------------------------
	//------ ENTITY COLUMN DEFINITION ------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// ID
	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// Review Text
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 140)
	private String text;
	
	// Review Rating
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false)
	private int rating;

	// Date Created
	@JsonView(JsonViews.Public.class)
	@Column(name = "date_created", nullable = false)
	private LocalDateTime dateCreated = LocalDateTime.now();
	
	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY RELATIONS --------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	@ManyToOne(optional = false)
	@JsonView(JsonViews.Public.class)
	private User user;
	
	@ManyToOne(optional = false)
	private Product product;
	
	
	//--------------------------------------------------------------------------------------------
	//------ CONSTRUCTORS ------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	

	public Review() {
		/* default constructor: required by JPA */
	}

	public Review(User user, Product product, String text, int rating) {
		this.user = user;
		this.product = product;
		this.text = text;
		this.rating = rating;
	}

	public Review(Long id, Product product, User user, String text, int rating) {
		this(user, product, text, rating);
		this.id = id;
	}
	
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	@PrePersist
	protected void prePersist() {
		this.dateCreated = LocalDateTime.now();
	}
	
}
