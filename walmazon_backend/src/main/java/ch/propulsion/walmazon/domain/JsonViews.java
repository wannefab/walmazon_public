package ch.propulsion.walmazon.domain;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Views for use with {@link JsonView @JsonView}.
 */
public interface JsonViews {

	interface Public {
	}
	
	interface User extends Public {
	}
	
	interface Admin extends User {
	}

	interface NewUser extends Public {
	}

}
