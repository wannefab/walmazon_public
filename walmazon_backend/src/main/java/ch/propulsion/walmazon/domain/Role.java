package ch.propulsion.walmazon.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table( name = "role" )
@Data
@EqualsAndHashCode(of = "name")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String ROLE_USER = "ROLE_USER";

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Name
	@Column(unique = true, nullable = false, length = 50)
	private String name;

	public Role() {
		/* default constructor: required by JPA */
	}
	
	public Role(String name) {
		this.name=name;
	}

}
