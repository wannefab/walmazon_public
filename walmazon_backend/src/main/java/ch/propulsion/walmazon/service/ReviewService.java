package ch.propulsion.walmazon.service;

import ch.propulsion.walmazon.domain.Review;


public interface ReviewService {

	Review findById(Long id);

	void addNewReview(Review review);

	void delete(Long id);

	void update(Review review);

	
}
