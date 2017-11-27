package ch.propulsion.walmazon.domain;

import static java.util.stream.Collectors.toSet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table( name = "user" )
@NamedEntityGraph(name = User.ENTITY_GRAPH_USER_WITH_ROLES, attributeNodes = @NamedAttributeNode("roles"))
@Data
@EqualsAndHashCode(of = "email")
@ToString(exclude = { "password", "reviews", "tokens", "purchases" })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	public static final String ENTITY_GRAPH_USER_WITH_ROLES = "UserWithRoles";
	
	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY COLUMN DEFINITION ------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// ID
	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	// Title
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 10)
	private String title;

	// First Name
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String firstName;
	
	// Last Name
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String lastName;
	
	// Address Name
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 100)
	private String address;
	
	// City Name
	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String city;
	
	// Birth Date
	@JsonView(JsonViews.Public.class)
	@Column(name = "birth_date", updatable = false, nullable = false)
	private LocalDateTime birthDate;
	
	// User username
	@JsonView(JsonViews.Public.class)
	@Column(unique = true, nullable = false, length = 50)
	private String username;

	// E-Mail
	@JsonView(JsonViews.User.class)
	@Column(unique = true, nullable = false, length = 50)
	private String email;

	// Password (Encrypted)
	@Column(nullable = false, length = 76) // BCrypt encoded passwords can need 50-76 characters.
	private String password;
	
	// Date Created
	@JsonView(JsonViews.Public.class)
	@Column(name = "date_created", updatable = false, nullable = false)
	private LocalDateTime dateCreated = LocalDateTime.now();

	
	//--------------------------------------------------------------------------------------------
	//------ ENTITY RELATIONS --------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@OrderBy("dateCreated")
	private List<Review> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@OrderBy("dateCreated")
	private List<Token> tokens = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@OrderBy("dateCreated")
	private List<Purchase> purchases = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "userRole")
	private Set<Role> roles = new HashSet<>();
	
	
	//--------------------------------------------------------------------------------------------
	//------ CONSTRUCTORS ------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	public User() {
		/* default constructor: required by JPA */
	}

	public User(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String firstName, String lastName) {
		this(null, firstName, lastName);
	}

	public User(String title, String firstName, String lastName, String address, String city, LocalDateTime birthDate, String username, String email, String password) {
		this(null, firstName, lastName);
		this.title = title;
		this.address= address;
		this.city = city;
		this.birthDate = birthDate;
		this.username = username;
		this.email = email;
        this.password = password;
	}
	
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	@PrePersist
	protected void prePersist() {
		this.dateCreated = LocalDateTime.now();
	}

	public void addReview(Review review) {
		getReviews().add(review);
		review.setUser(this);
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	public boolean hasRole(Role role) {
		for (Role r : this.roles) {
		    if(r.equals(role)) {
		    	return true;
		    }
		}
		return false;
	}
	
	// -- Spring Security: UserDetails -----------------------------------------

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (getRoles() == null) {
			return Collections.emptySet();
		}

		// @formatter:off
		return getRoles().stream()
				.map(Role::getName)
				.map(String::toUpperCase)
				.map(SimpleGrantedAuthority::new)
				.collect(toSet());
		// @formatter:on
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

}
