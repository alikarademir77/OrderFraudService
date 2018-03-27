package ca.bestbuy.orders.fraud.client;

import ca.bestbuy.orders.fraud.utility.KeystoreConfig;
import ca.bestbuy.orders.fraud.utility.TimeoutConfig;
import ca.bestbuy.orders.fraud.utility.TruststoreConfig;

public interface WebClientConfig {

    KeystoreConfig getKeystoreConfig();
    TruststoreConfig getTruststoreConfig();
    TimeoutConfig getTimeoutConfig();
    Boolean verifyHostname();
    Boolean sslEnabled();

}
