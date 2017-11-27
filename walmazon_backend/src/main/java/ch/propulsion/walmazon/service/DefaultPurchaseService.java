package ch.propulsion.walmazon.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.propulsion.walmazon.domain.Purchase;
import ch.propulsion.walmazon.repository.PurchaseRepository;




@Transactional(readOnly = true)
@Service
public class DefaultPurchaseService implements PurchaseService {
	private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);
	
	private final PurchaseRepository purchaseRepository;
	
	@Autowired
	public DefaultPurchaseService(PurchaseRepository purchaseRepository) {
		this.purchaseRepository = purchaseRepository;
	}
	
	
	//--------------------------------------------------------------------------------------------
	//------ METHODS -----------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------
		
	@Transactional(readOnly = false)
	@Override
	public void addNewPurchase(Purchase purchase) {
		// Make sure we are saving a new review and not accidentally
		// updating an existing review.
		purchase.setId(null);
		
		logger.trace("Adding new purchase [{}].", purchase);
	
		purchaseRepository.save(purchase);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Purchase findById(Long id) {
		logger.trace("Finding Order by ID [{}].", id);
		return purchaseRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException("Could not find purchase with ID [" + id + "]"));
	}

	@Transactional(readOnly = false)
	@Override
	public void update(Purchase purchase) {
		logger.trace("Updating purchase [{}].", purchase);
		purchaseRepository.update(purchase.getId(), purchase.getStatus());
	}


	@Override
	public List<Purchase> findAll() {
		logger.trace("Finding all purchases.");
		return this.purchaseRepository.findAll();
	}





	

}








