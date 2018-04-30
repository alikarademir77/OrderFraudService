/**
 * 
 */
package ca.bestbuy.orders.fraud.utility;

/**
 * @author akaradem
 *
 */
public class UatServerHolder {

	public static final String[] args = new String[] {"-tcpPort", "8092", "-tcpAllowOthers","true" };
	
	public static org.h2.tools.Server server = null;
	
}
