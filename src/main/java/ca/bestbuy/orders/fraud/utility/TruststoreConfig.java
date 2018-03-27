package ca.bestbuy.orders.fraud.utility;

import org.springframework.core.io.Resource;

public class TruststoreConfig {

    private Resource truststore;
    private String truststorePassword;


    /**
     * @param truststore Truststore
     * @param truststorePassword Password for truststore
     */
    public TruststoreConfig(Resource truststore, String truststorePassword) {
        this.truststore = truststore;
        this.truststorePassword = truststorePassword;
    }

    public Resource getTruststore() {
        return truststore;
    }

    public String getTruststorePassword() {
        return truststorePassword;
    }

}
