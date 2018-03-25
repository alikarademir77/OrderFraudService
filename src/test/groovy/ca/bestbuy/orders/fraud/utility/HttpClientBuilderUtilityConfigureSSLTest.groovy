package ca.bestbuy.orders.fraud.utility

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.web.context.support.ServletContextResource
import spock.lang.Specification

class HttpClientBuilderUtilityConfigureSSLTest extends Specification {


    def "Test configureSSL() when builder passed in is null"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(null, keystoreConfig, truststoreConfig, false)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("HttpClientBuilder")
    }


    def "Test configureSSL() when keystoreConfig passed in is null"() {

        setup:

        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), null, truststoreConfig, false)


        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("KeystoreConfig must not be null")
    }


    def "Test configureSSL() when keystoreConfig passed in has a keystore with a Resource instance that is not ClassPathResource or UrlResource"() {

        setup:

        KeystoreConfig keystoreConfig = new KeystoreConfig(Stub(ServletContextResource.class), "password")
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("KeystoreConfig.keystore must be an instance of UrlResource or ClassPathResource")
    }


    def "Test configureSSL() when truststoreConfig passed in is null"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, null, false)

        then:

        final IllegalArgumentException exception = thrown()
        exception.message.contains("TruststoreConfig must not be null")
    }


    def "Test configureSSL() when truststoreConfig passed in has a truststore with a Resource instance that is not ClassPathResource or UrlResource"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = new TruststoreConfig(Stub(ServletContextResource.class), "password")

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("TruststoreConfig.truststore must be an instance of UrlResource or ClassPathResource")
    }


    def "Test configureSSL() with a keystore that does not exist"() {

        setup:

        KeystoreConfig keystoreConfig = new KeystoreConfig(new ClassPathResource("doesnotexist.jks"), "password")
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("Could not load keystore and/or trust store")
    }


    def "Test configureSSL() with a keystore with the wrong keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createInvalidJKSKeystore_WrongKeystorePassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("Could not load keystore and/or trust store")
    }


    def "Test configureSSL() with a keystore with the wrong key password"() {

        setup:

        KeystoreConfig keystoreConfig = createInvalidJKSKeystore_WrongKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("Could not load keystore and/or trust store")
    }


    def "Test configureSSL() with a keystore with the wrong key alias"() {

        setup:

        KeystoreConfig keystoreConfig = createInvalidJKSKeystore_WrongKeyAlias()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        // Here no exception should be thrown
        noExceptionThrown()

    }


    def "Test configureSSL() with a truststore that does not exist"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = new TruststoreConfig(new ClassPathResource("doesnotexist.jks"), "234")

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("Could not load keystore and/or trust store")
    }


    def "Test configureSSL() with a truststore with the wrong keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = createInvalidJKSTruststore_WrongKeystorePassword()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        final IllegalStateException exception = thrown()
        exception.message.contains("Could not load keystore and/or trust store")
    }


    def "Test configureSSL() with a (JKS) keystore configured with the same key password as the keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        noExceptionThrown()
    }


    def "Test configureSSL() with a (PKCS12) keystore configured with the same key password as the keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createValidPFXKeystoreConfigWithSameKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        noExceptionThrown()
    }


    def "Test configureSSL() with a (JKS) keystore configured with a different key password than the keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createValidJKSKeystoreConfigWithDifferentKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        noExceptionThrown()
    }


    def "Test configureSSL() with a (PKCS12) keystore configured with a different key password than the keystore password"() {

        setup:

        KeystoreConfig keystoreConfig = createValidPFXKeystoreConfigWithDifferentKeyPassword()
        TruststoreConfig truststoreConfig = createValidJKSTruststoreConfig()

        when:

        HttpClientBuilderUtility.configureSSL(HttpClientBuilder.create(), keystoreConfig, truststoreConfig, false)


        then:

        noExceptionThrown()
    }

    /* ********************************************************** */
    /*                      HELPER METHODS                        */
    /* ********************************************************** */


    private static KeystoreConfig createValidJKSKeystoreConfigWithSameKeyPassword() {

        Resource resource = new ClassPathResource("test-keystores/keystore-with-same-key-password.jks")
        String keystorePassword = "password"

        return new KeystoreConfig(resource, keystorePassword)
    }


    private static KeystoreConfig createValidJKSKeystoreConfigWithDifferentKeyPassword() {

        Resource resource = new ClassPathResource("test-keystores/keystore-with-diff-key-password.jks")
        String keystorePassword = "password"
        String keyPassword = "secret"

        return new KeystoreConfig(resource, keystorePassword, null, keyPassword)
    }


    private static KeystoreConfig createValidPFXKeystoreConfigWithSameKeyPassword() {

        Resource resource = new ClassPathResource("test-keystores/keystore-with-same-key-password.pfx")
        String keystorePassword = "password"

        return new KeystoreConfig(resource, keystorePassword)
    }


    private static KeystoreConfig createValidPFXKeystoreConfigWithDifferentKeyPassword() {

        Resource resource = new ClassPathResource("test-keystores/keystore-with-diff-key-password.pfx")
        String keystorePassword = "password"
        String keyPassword = "secret"

        return new KeystoreConfig(resource, keystorePassword, null, keyPassword)
    }


    private static TruststoreConfig createValidJKSTruststoreConfig() {

        Resource resource = new ClassPathResource("test-keystores/truststore.jks")
        String truststorePassword = "password"

        return new TruststoreConfig(resource, truststorePassword)
    }


    private static KeystoreConfig createInvalidJKSKeystore_WrongKeystorePassword() {
        Resource resource = new ClassPathResource("test-keystores/keystore-with-same-key-password.jks")
        String wrongKeystorePassword = "wrongpassword"

        return new KeystoreConfig(resource, wrongKeystorePassword)
    }


    private static KeystoreConfig createInvalidJKSKeystore_WrongKeyPassword() {
        Resource resource = new ClassPathResource("test-keystores/keystore-with-same-key-password.jks")
        String keystorePassword = "password"
        String wrongKeyPassword = "WRONG"

        return new KeystoreConfig(resource, keystorePassword, null, wrongKeyPassword)
    }


    private static KeystoreConfig createInvalidJKSKeystore_WrongKeyAlias() {
        Resource resource = new ClassPathResource("test-keystores/keystore-with-same-key-password.jks")
        String keystorePassword = "password"
        String wrongAlias = "WRONG"

        return new KeystoreConfig(resource, keystorePassword, wrongAlias, null)
    }


    private static TruststoreConfig createInvalidJKSTruststore_WrongKeystorePassword() {

        Resource resource = new ClassPathResource("test-keystores/truststore.jks")
        String truststorePassword = "WRONG"

        return new TruststoreConfig(resource, truststorePassword)
    }


}
