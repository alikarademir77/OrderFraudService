package ca.bestbuy.orders.fraud.utility;

import org.springframework.core.io.Resource;

public class KeystoreConfig {

    private Resource keystore;
    private String keystorePassword;
    private String keyAlias;
    private String keyPassword;


    /**
     * @param keystore @NotNull Keystore
     * @param keystorePassword @NotNull Password for keystore
     */
    public KeystoreConfig(Resource keystore, String keystorePassword) {
        this(keystore, keystorePassword, null, null);
    }


    /**
     * @param keystore @NotNull Keystore
     * @param keystorePassword @NotNull Password for keystore
     * @param keyAlias @Nullable Alias for key in keystore
     * @param keyPassword @Nullable Password for key in keystore
     */
    public KeystoreConfig(Resource keystore, String keystorePassword, String keyAlias, String keyPassword) {

        if(keystore == null) {
            throw new IllegalArgumentException("keystore provided cannot be null");
        }

        if(keystorePassword == null) {
            throw new IllegalArgumentException("keystorePassword provided cannot be null");
        }

        this.keystore = keystore;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
    }


    public Resource getKeystore() {
        return keystore;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }
}
