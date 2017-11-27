package ch.propulsion.walmazon.service;

import java.util.List;

import ch.propulsion.walmazon.domain.Purchase;

public interface PurchaseService {
	
	Purchase findById(Long id);
	
	List<Purchase> findAll();

	void addNewPurchase(Purchase purchase);

	void update(Purchase purchase);

}


