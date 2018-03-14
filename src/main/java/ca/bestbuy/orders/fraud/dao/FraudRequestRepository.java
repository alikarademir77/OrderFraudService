package ca.bestbuy.orders.fraud.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.bestbuy.orders.fraud.model.jpa.FraudRequest;

@Repository
public interface FraudRequestRepository extends JpaRepository<FraudRequest, Long> {

}
