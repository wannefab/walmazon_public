package ch.propulsion.walmazon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ch.propulsion.walmazon.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findById(Long id);
	
	List<Product> findByNameContaining(String param);
	
	@Modifying
	@Query(
		    value = "SELECT * FROM Product p WHERE p.id > ?1 ORDER BY p.id ASC LIMIT 0,?2",
		    nativeQuery = true
		  )
	List<Product> findNumberById(long id, long number);

	@Modifying
	@Query(
		    value = "SELECT * FROM Product p WHERE p.id > ?1 AND LOWER(p.name)  LIKE LOWER(?3) ORDER BY p.id ASC LIMIT 0,?2",
		    nativeQuery = true
		  )
	List<Product> findNumberByIdAndNameContaining(long id, long number, String filter);
	
	@Modifying
	@Query(
		    value = "SELECT * FROM Product p WHERE p.id > ?1 AND LOWER(p.tags) LIKE LOWER(?3) ORDER BY p.id ASC LIMIT 0,?2",
		    nativeQuery = true
		  )
	List<Product> findNumberByIdAndTagsContaining(long id, long number, String filter);
}