package ch.propulsion.walmazon.web;

import java.util.List;
import java.util.Map;

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
import ch.propulsion.walmazon.domain.Purchase;
import ch.propulsion.walmazon.domain.Role;
import ch.propulsion.walmazon.domain.User;
import ch.propulsion.walmazon.service.PurchaseService;
import ch.propulsion.walmazon.service.ProductService;
import ch.propulsion.walmazon.service.TokenService;


@RestController
@RequestMapping("/purchases")
public class RestPurchaseController {
	private final PurchaseService purchaseService;
	private final TokenService tokenService;
	private final ProductService productService;
	
	
	@Autowired
	public RestPurchaseController(PurchaseService purchaseService, TokenService tokenService, ProductService productService) {
		this.purchaseService = purchaseService;
		this.tokenService = tokenService;
		this.productService = productService;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------

	
	// Add Order
	@JsonView(JsonViews.Public.class)
	@PostMapping("/{id}")
	public Object addNewPurchase(@PathVariable long id, @RequestBody Purchase purchase,  @RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		Product product = productService.findById(id);

		// Security Checks
		if(user != null && product != null) {
			purchase.setStatus("open");
			purchase.setUser(user);
			purchase.setProduct(product);
			
			System.out.println(purchase);
			
			purchaseService.addNewPurchase(purchase);
			return purchase;
		}
		return new ErrorMessages("TOKEN_ERROR");
	}
	
	
	// Get all Product
	@JsonView(JsonViews.Public.class)
	@GetMapping
	public Object retrieveAllProducts(@RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		User user = tokenService.validateToken(token);
		
		//Restrict access to Admins
		Role accessRestrictionToRole = new Role();
		accessRestrictionToRole.setName("ROLE_ADMIN");
		if(user.hasRole(accessRestrictionToRole)) {
			return purchaseService.findAll();
		}
		else {
			return new ErrorMessages("ACCESS_DENIED");
		}
		
	}

}


	/*
	
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

}*/