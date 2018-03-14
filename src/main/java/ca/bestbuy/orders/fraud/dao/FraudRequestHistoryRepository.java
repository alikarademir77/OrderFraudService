/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequestHistory;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudRequestHistoryRepository extends OrderFraudBaseRepository<FraudRequestHistory, Long> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	<S extends FraudRequestHistory> S save(S entity);	
	
	/**
	 * Retrieves an FraudRequestHistory by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	FraudRequestHistory findOne(Long id);

	/**
	 * Returns all instances of the FraudRequestHistory with the given orderNumber.
	 * 
	 * @param orderNumber
	 * @return
	 */
	 Iterable<FraudRequestHistory> findAllByFraudRequestOrderNumber(BigDecimal orderNumber);

	/**
	 * Returns all instances of the FraudRequestHistory with the given orderNumber and requestVersion.
	 * 
	 * @param orderNumber
	 * @param requestVersion
	 * @return
	 */
	 Iterable<FraudRequestHistory> findAllByFraudRequestOrderNumberAndFraudRequestRequestVersion(BigDecimal orderNumber, BigDecimal requestVersion);
}
