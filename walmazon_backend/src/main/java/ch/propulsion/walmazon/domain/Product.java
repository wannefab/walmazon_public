

package ch.propulsion.walmazon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table( name = "product" )
@Data
@EqualsAndHashCode(of = {"name", "id"})
@ToString(exclude = { "reviews", "purchases" })
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	//--------------------------------------------------------------------------------------------
	//------ ENTITY COLUMN DEFINITION ------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// ID
	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// Name
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 80)
	private String name;
	
	// Description
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 120)
	private String description;
	
	// Tags
	@JsonView(JsonViews.Public.class)
	@Column(nullable = true, length = 120)
	private String tags;
	
	// Specifications 
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false)
	private String specifications ;
	
	// Price
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false)
	private double price;
	
	// NumberInStock
	@JsonView(JsonViews.Public.class)
	@Column(name = "number_in_stock", nullable = true)
	private int numberInStock;
	
	// Images
	@JsonView(JsonViews.Public.class)
	@Column(nullable = true)
	private String images;
	
	// ExternalLink
	@JsonView(JsonViews.Public.class)
	@Column(name = "external_links", nullable = false, length = 100)
	private String externalLinks;
	
	// Date Updated
	@JsonView(JsonViews.Public.class)
	@Column(name = "date_updated", updatable = false, nullable = false)
	private LocalDateTime dateUpdated = LocalDateTime.now();
	
	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY RELATIONS --------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	@JsonView(JsonViews.Public.class)
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@OrderBy("dateCreated")
	private List<Review> reviews = new ArrayList<>();
	
	@JsonView(JsonViews.Admin.class)
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@OrderBy("dateCreated")
	private List<Purchase> purchases = new ArrayList<>();
	
	
	//--------------------------------------------------------------------------------------------
	//------ CONSTRUCTORS ------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public Product() {
		/* default constructor: required by JPA */
	}

	public Product(String name, String description, String tags, String specifications, double price, int numberInStock, String images, String externalLinks) {
		this.name = name;
		this.description = description;
		this.tags =  tags;
		this.specifications  = specifications ;
		this.price = price;
		this.numberInStock = numberInStock;
		this.images = images;
		this.externalLinks = externalLinks;	
	}

	public Product(Long id, String name, String description, String tags, String specifications, double price, int numberInStock, String images, String externalLinks) {
		this(name, description, tags, specifications, price, numberInStock, images , externalLinks);
		this.id = id;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	@PrePersist
	protected void prePersist() {
		this.dateUpdated = LocalDateTime.now();
	}

}
