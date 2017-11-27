package ch.propulsion.walmazon.service;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.propulsion.walmazon.domain.Review;
import ch.propulsion.walmazon.repository.ReviewRepository;

@Transactional(readOnly = true)
@Service
public class DefaultReviewService implements ReviewService{
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);
	
	private final ReviewRepository reviewRepository;
	
	@Autowired
	public DefaultReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
		
		@Transactional(readOnly = false)
		@Override
	public void addNewReview(Review review) {
		// Make sure we are saving a new review and not accidentally
		// updating an existing review.
		review.setId(null);

		logger.trace("Adding new review [{}].", review);

		reviewRepository.save(review);
	}

	@Transactional(readOnly = true)
	@Override
	public Review findById(Long id) {
		logger.trace("Finding Review by ID [{}].", id);
		return reviewRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Could not find Review with ID [" + id + "]"));
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(Long id) {
		logger.trace("Deleting review with ID [{}].", id);
		reviewRepository.delete(id);
		
	}

	@Transactional(readOnly = false)
	@Override
	public void update(Review review) {
		logger.trace("Updating review [{}].", review);
		System.out.println(review);
		reviewRepository.update(review.getId(), review.getText(), review.getRating(), LocalDateTime.now());
	}
	
}