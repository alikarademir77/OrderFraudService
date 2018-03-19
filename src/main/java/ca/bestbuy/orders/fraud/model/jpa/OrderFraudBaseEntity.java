/**
 * 
 */
package ca.bestbuy.orders.fraud.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author akaradem
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Accessors(chain=true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OrderFraudBaseEntity implements Serializable {

	@Version
	@Column(name = "VERSION")
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "CREATE_USER")
	private String createUser;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	@Column(name = "UPDATE_USER")
	private String updateUser;
	
	public OrderFraudBaseEntity(){
		
	}
	
}
