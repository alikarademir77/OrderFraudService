/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import java.math.BigDecimal;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequestStatusHistory;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudRequestStatusHistoryRepository extends OrderFraudBaseRepository<FraudRequestStatusHistory, Long> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	<S extends FraudRequestStatusHistory> S save(S entity);	
	
	/**
	 * Retrieves an FraudRequestStatusHistory by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	FraudRequestStatusHistory findOne(Long id);

	/**
	 * Returns all instances of the FraudRequestStatusHistory with the given orderNumber.
	 * 
	 * @param orderNumber
	 * @return
	 */
	 Iterable<FraudRequestStatusHistory> findByFraudRequestOrderNumber(BigDecimal orderNumber, Sort sort);

	/**
	 * Returns all instances of the FraudRequestStatusHistory with the given orderNumber and requestVersion.
	 * 
	 * @param orderNumber
	 * @param requestVersion
	 * @return
	 */
	 Iterable<FraudRequestStatusHistory> findByFraudRequestOrderNumberAndFraudRequestRequestVersion(BigDecimal orderNumber, BigDecimal requestVersion, Sort sort);
}
