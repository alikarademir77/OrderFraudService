package ca.bestbuy.orders.fraud.utility;

import org.springframework.core.io.Resource;

public class TruststoreConfig {

    private Resource truststore;
    private String truststorePassword;


    /**
     * @param truststore @NotNull Truststore
     * @param truststorePassword @NotNull Password for truststore
     */
    public TruststoreConfig(Resource truststore, String truststorePassword) {

        if (truststore == null) {
            throw new IllegalArgumentException("truststore provided cannot be null");
        }

        if (truststorePassword == null) {
            throw new IllegalArgumentException("truststorePassword provided cannot be null");
        }

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
