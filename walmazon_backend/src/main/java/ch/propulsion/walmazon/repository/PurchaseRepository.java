package ch.propulsion.walmazon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.propulsion.walmazon.domain.Purchase;
import ch.propulsion.walmazon.domain.Review;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>  {
	
	Optional<Purchase> findById(Long id);

	List<Purchase> findByUserId(Long user_id);
	
	@Modifying
	@Query(
		    value = "UPDATE Purchase SET status=?2 WHERE id = ?1",
		    nativeQuery = true
		  )
	void update(Long id, String status);
	
}