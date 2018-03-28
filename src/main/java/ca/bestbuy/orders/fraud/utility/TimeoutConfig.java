package ca.bestbuy.orders.fraud.utility;

public class TimeoutConfig {

    private Integer connectionTimeout;
    private Integer requestTimeout;

    /**
     * @param connectionTimeout @Nullable Timeout in milliseconds for establishing a connection
     * @param requestTimeout @Nullable Timeout in milliseconds for getting back a response after a request has been sent
     */
    public TimeoutConfig(Integer connectionTimeout, Integer requestTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.requestTimeout = requestTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public Integer getRequestTimeout() {
        return requestTimeout;
    }

}
