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
@Table( name = "purchase" )
@Data
@EqualsAndHashCode(of = {"id", "status", "dateCreated", "user", "product"})
public class Purchase implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY COLUMN DEFINITION ------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// ID
	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// Status
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 20)
	private String status;
	
	// Quantity
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false)
	private int quantity;
	
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
	@JsonView(JsonViews.Public.class)
	private Product product;
	
	//--------------------------------------------------------------------------------------------
	//------ CONSTRUCTORS ------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public Purchase() {
		/* default constructor: required by JPA */
	}

	public Purchase(User user, Product product, String status, int quantity) {
		this.user = user;
		this.product = product;
		this.status = status;
		this.quantity = quantity;
	}

	public Purchase(Long id,  User user, Product product, String status, int quantity) {
		this(user, product, status, quantity);
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




	
	