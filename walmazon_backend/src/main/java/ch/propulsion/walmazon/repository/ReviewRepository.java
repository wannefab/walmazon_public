package ch.propulsion.walmazon.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.propulsion.walmazon.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Optional<Review> findById(Long id);

	List<Review> findByUserId(Long user_id);
	
	@Modifying
	@Query(
		    value = "UPDATE Review SET text=?2, rating=?3, date_created=?4 WHERE id = ?1",
		    nativeQuery = true
		  )
	void update(Long id, String text, int rating, LocalDateTime dateCreated);
	
}