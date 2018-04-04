/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatusCodes;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudStatusRepository extends OrderFraudBaseRepository<FraudStatus, FraudStatusCodes> {

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	FraudStatus findOne(FraudStatusCodes id);
	
	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	Iterable<FraudStatus> findAll();

}
