package ch.propulsion.walmazon.service;

import java.util.List;

import ch.propulsion.walmazon.domain.Product;


public interface ProductService {
	
	Product registerNewProduct(Product product);
	
	void addGeneratedNewProduct(int numberOfProducts);

	Product findById(Long id);
	
	List<Product> findAll();
	
	List<Product> findByNameContaining(String param);
	
	List<Product> findNumberById(long id, long number);
	
	List<Product> findNumberByIdAndNameContaining(long id, long number, String filter);
	
	List<Product> findNumberByIdAndTagsContaining(long id, long number, String filter);
	
}
