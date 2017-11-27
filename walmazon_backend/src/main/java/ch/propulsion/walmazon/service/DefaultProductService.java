package ch.propulsion.walmazon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.propulsion.walmazon.domain.Product;
import ch.propulsion.walmazon.domain.Token;
import ch.propulsion.walmazon.helpers.Product_DataGenerator;
import ch.propulsion.walmazon.repository.ProductRepository;


@Transactional(readOnly = true)
@Service
public class DefaultProductService implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);
	private final ProductRepository productRepository;
	
	@Autowired
	public DefaultProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;

	}
	
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
	
	@Override
	public Product findById(Long id) {
		logger.trace("Finding product by ID [{}].", id);
		return this.productRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Could not find Product with ID [" + id + "]"));
	}

	@Override
	public List<Product> findAll() {
		logger.trace("Finding all products.");
		return this.productRepository.findAll();
	}

	@Override
	public List<Product> findByNameContaining(String param) {
		logger.trace("Finding all products containing: {}", param);
		return this.productRepository.findByNameContaining(param);
	}

	@Transactional(readOnly = false)
	@Override
	public Product registerNewProduct(Product product) {
		product.setReviews(null);
		return this.productRepository.save(product);
	}


	@Override
	public List<Product> findNumberById(long id, long number) {
		return this.productRepository.findNumberById(id,number);
	}

	@Override
	public List<Product> findNumberByIdAndNameContaining(long id, long number, String filter) {
		filter = "%"+filter+"%";
		return this.productRepository.findNumberByIdAndNameContaining(id,number,filter);
	}
	
	@Override
	public List<Product> findNumberByIdAndTagsContaining(long id, long number, String filter) {
		filter = "%"+filter+"%";
		return this.productRepository.findNumberByIdAndTagsContaining(id,number,filter);
	}


	@Override
	public void addGeneratedNewProduct(int numberOfProducts) {
		Product_DataGenerator generator = new Product_DataGenerator();
				
		for(int i = 0;i<numberOfProducts;i++) {
			Product product = new Product();
			int productType = (int) Math.ceil(Math.random()*4);
			product.setName(generator.generateProductName(productType));	
			product.setDescription(generator.generateDescription());
			product.setTags(generator.generateTags(productType));
			product.setSpecifications(generator.generateSpecifications(productType));
			product.setPrice( (double) Math.floor(Math.random()*500*100)/100 );
			product.setNumberInStock((int) Math.floor(Math.random()*101));
			
			product.setImages("https://robohash.org/"+UUID.randomUUID().toString()+".jpg?size=296x198&set=set"+productType);
			product.setExternalLinks("https://robohash.org/");
			product.setDateUpdated(generator.generateDate());
			
			System.out.println(product);
			this.registerNewProduct(product);
		}

	}



	
}


	