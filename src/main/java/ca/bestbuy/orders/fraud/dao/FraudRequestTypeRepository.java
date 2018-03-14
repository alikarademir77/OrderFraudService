/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType;
import ca.bestbuy.orders.fraud.model.jpa.FraudRequestType.RequestTypes;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudRequestTypeRepository extends JpaRepository<FraudRequestType, RequestTypes> {

}
