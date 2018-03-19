/**
 * 
 */
package ca.bestbuy.orders.fraud.dao;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * @author akaradem
 * 
 * Defining that interface so that sub repositories will only expose needed operations to client code.
 *  
 */
@NoRepositoryBean
public interface OrderFraudBaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

}
