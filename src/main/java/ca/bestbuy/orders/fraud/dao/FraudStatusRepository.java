/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudStatus;
import ca.bestbuy.orders.fraud.model.jpa.FraudStatus.FraudStatuses;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudStatusRepository extends JpaRepository<FraudStatus, FraudStatuses> {

}
