/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequestHistory;

/**
 * @author akaradem
 *
 */
@Repository
public interface FraudRequestHistoryRepository extends JpaRepository<FraudRequestHistory, Long> {

}
