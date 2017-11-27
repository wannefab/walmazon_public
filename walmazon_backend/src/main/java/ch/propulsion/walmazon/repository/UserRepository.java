package ch.propulsion.walmazon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.propulsion.walmazon.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);
	
	@Modifying
	@Query(
		    value = "UPDATE User SET first_name=?2, last_name=?3, email=?4, password=?5 WHERE id = ?1",
		    nativeQuery = true
		  )
	void update(Long id, String fist_name, String lastName, String eMail, String password);
	
	@EntityGraph(User.ENTITY_GRAPH_USER_WITH_ROLES)
	@Query("from User u where u.email = ?1")
	Optional<User> findByEmailWithRolesEagerlyLoaded(String email);

}
