/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType.RequestTypes;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudRequestTypeRepository extends OrderFraudBaseRepository<FraudRequestType, RequestTypes> {

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	FraudRequestType findOne(RequestTypes id);
	
	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	Iterable<FraudRequestType> findAll();
}
