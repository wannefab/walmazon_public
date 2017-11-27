package ch.propulsion.walmazon.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ch.propulsion.walmazon.domain.ErrorMessages;
import ch.propulsion.walmazon.domain.JsonViews;
import ch.propulsion.walmazon.domain.Product;
import ch.propulsion.walmazon.domain.Review;
import ch.propulsion.walmazon.domain.Role;
import ch.propulsion.walmazon.domain.User;
import ch.propulsion.walmazon.service.ProductService;
import ch.propulsion.walmazon.service.ReviewService;
import ch.propulsion.walmazon.service.TokenService;


@RestController
@RequestMapping("/products")
public class RestProductController {
	
	private final ProductService productService;
	private final TokenService tokenService;
	private final ReviewService reviewService;
	
	@Autowired
	public RestProductController(ProductService productService, TokenService tokenService, ReviewService reviewService) {
		this.productService = productService;
		this.tokenService = tokenService;
		this.reviewService = reviewService;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	// Get all Product
	@JsonView(JsonViews.Public.class)
	@GetMapping
	public List<Product> retrieveAllProducts() {
		return productService.findAll();
	}

	// Get Product by ID
	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}")
	public Object retrieveProductById(@PathVariable long id) {
		return productService.findById(id);

	}
	
	// Get number of Products by ID and number
	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}/{number}")
	public Object retrieveNumberOfProductsById(@PathVariable long id, @PathVariable long number) {
		return productService.findNumberById(id, number);
	}
	
	// Get number of filtered Products by ID and number
	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}/{number}/{filter}")
	public Object retrieveNumberOfProductsByIdAndNameContaining(@PathVariable long id, @PathVariable long number,  @PathVariable String filter) {
		return productService.findNumberByIdAndNameContaining(id, number, filter);
	}
	
	// Get number of filtered Products by ID and number
	@JsonView(JsonViews.Public.class)
	@GetMapping("/tags/{id}/{number}/{filter}")
	public Object retrieveNumberOfProductsByIdAndTagsContaining(@PathVariable long id, @PathVariable long number,  @PathVariable String filter) {
		System.out.println(filter);
		return productService.findNumberByIdAndTagsContaining(id, number, filter);
	}
		
	
	// Find Product with name containing
	@JsonView(JsonViews.Public.class)
	@GetMapping("/search/{param}")
	public List<Product> retrieveProductNameContaining(@PathVariable String param) {
		return productService.findByNameContaining(param);
	}
	
	// Add Product
	@JsonView(JsonViews.Public.class)
	@PostMapping
	public Object addNewProduct(@RequestBody Product product, @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		System.out.println(user);
		
		if(user != null && product != null) {
			//Restrict access to Admins
			Role accessRestrictionToRole = new Role();
			accessRestrictionToRole.setName("ROLE_ADMIN");
			if(user.hasRole(accessRestrictionToRole)) {
				return productService.registerNewProduct(product);
			}
			else {
				return new ErrorMessages("ACCESS_DENIED");
			}
		}
		return new ErrorMessages("TOKEN_ERROR");
	}
	
	// Add Generated Products
	@JsonView(JsonViews.Public.class)
	@PostMapping("/generate/{numberOfProducts}")
	public Object addGeneratedProduct(@PathVariable int numberOfProducts, @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		System.out.println(user);
		
		if( user != null ) {
			//Restrict access to Admins
			Role accessRestrictionToRole = new Role();
			accessRestrictionToRole.setName("ROLE_ADMIN");
			if(user.hasRole(accessRestrictionToRole)) {
				productService.addGeneratedNewProduct(numberOfProducts);
				return null;	
			}
			else {
				return new ErrorMessages("ACCESS_DENIED");
			}
		}
		return new ErrorMessages("TOKEN_ERROR");
	}
	
	// Add Review to Product
	@JsonView(JsonViews.Public.class)
	@PostMapping("/{id}/review")
	public Object addNewProductReview(@PathVariable long id, @RequestBody Review review, @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		Product product = productService.findById(id);

		// Security Checks
		if(user != null && product != null) {
			review.setUser(user);
			review.setProduct(product);
			reviewService.addNewReview(review);
			return review;
		}
		return new ErrorMessages("TOKEN_ERROR");
	}
	
	// Delete
	@JsonView(JsonViews.Public.class)
	@DeleteMapping("/{id}/review/{reviewId}")
	public Object DeleteReview(@PathVariable long id, @PathVariable long reviewId, @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		Product product = productService.findById(id);
		Review review = reviewService.findById(reviewId);
		
		// Security Checks
		if(user != null && product != null) {
			if( user.equals(review.getUser()) && product.equals(review.getProduct()) ) {
				reviewService.delete(review.getId());
				return review;
			}
			else {
				return new ErrorMessages("REVIEW_UPDATE_ERROR");
			}
		}
		return new ErrorMessages("TOKEN_ERROR");
	}
	
	// Update
	@JsonView(JsonViews.Public.class)
	@PutMapping("/{id}/review/{reviewId}")
	public Object DeleteReview(@PathVariable long id, @PathVariable long reviewId, @RequestBody Review review, @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		Product product = productService.findById(id);
		Review reviewDatabase = reviewService.findById(reviewId);
		
		// Security Checks
		if(user != null && product != null) {
			if( user.equals(reviewDatabase.getUser()) && product.equals(reviewDatabase.getProduct()) ) {
				review.setId(reviewId);
				reviewService.update(review);
				return review;
			}
			else {
				return new ErrorMessages("REVIEW_DELETE_ERROR");
			}
		}
		return new ErrorMessages("TOKEN_ERROR");
	}

}