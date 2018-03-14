package ca.bestbuy.orders.fraud.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;

@Repository
public interface FraudRequestRepository extends OrderFraudBaseRepository<FraudRequest, Long> {

	/**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	<S extends FraudRequest> S save(S entity);
	
	/**
	 * Retrieves an FraudRequest by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	FraudRequest findOne(Long id);
	
	/**
	 * Returns all instances of the FraudRequest with the given orderNumber.
	 * 
	 * @param orderNumber
	 * @return
	 */
	 Iterable<FraudRequest> findAllByOrderNumber(BigDecimal orderNumber);
	
	/**
	 * Returns all instances of FraudRequest with orderNumber equal to parameter orderNumber and requestVersion greater than given version number.
	 * The result is given in descending order of request version
	 * 
	 * @param orderNumber
	 * @param requestVersion
	 * @return all matching instances of FraudRequest or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code orderNumber} is {@literal null} or {@code requestVersion} is {@literal null} 
	 */
	 Iterable<FraudRequest> findByOrderNumberAndRequestVersionGreaterThanOrderByRequestVersionDesc(BigDecimal orderNumber, BigDecimal requestVersion);

}
