package ca.bestbuy.orders.messaging.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ca.bestbuy.orders.messaging.EventTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public final class OutboundMessagingEvent {

    private EventTypes type;

    private String orderNumber;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "UTC")
    private Date messageCreationDate;

    private FraudResult result;


    private OutboundMessagingEvent(Builder builder) {

        this.type = builder.eventType;
        this.orderNumber = builder.orderNumber;
        this.messageCreationDate = builder.messageCreationDate;

        this.result = new FraudResult();
        this.result.status = builder.resultStatus;
        this.result.requestVersion = builder.requestVersion;
        this.result.totalFraudScore = builder.totalFraudScore;
        this.result.recommendationCode = builder.recommendationCode;
        this.result.accertifyUser = builder.accertifyUser;
        this.result.accertifyUserCreationDate = builder.accertifyUserCreationDate;

    }


    @Getter
    @ToString
    @JsonInclude(Include.NON_NULL)
    public class FraudResult {

        private String status;

        private String requestVersion;

        private String totalFraudScore;

        private String recommendationCode;

        private String accertifyUser;

        @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "UTC")
        private Date accertifyUserCreationDate;

        private FraudResult() {
        }
    }


    public static class Builder {

        private EventTypes eventType;

        private String orderNumber;

        private Date messageCreationDate;

        private String resultStatus;

        private String requestVersion;

        private String totalFraudScore;

        private String recommendationCode;

        private String accertifyUser;

        private Date accertifyUserCreationDate;


        public static Builder create(EventTypes eventType, String orderNumber, String requestVersion, String resultStatus) {

            if (eventType == null) {
                throw new IllegalArgumentException("Input parameter 'eventType' must not be null");
            }

            if (orderNumber == null || orderNumber.isEmpty()) {
                throw new IllegalArgumentException("Input parameter 'orderNumber' must not be null or empty");
            }

            if (requestVersion == null || requestVersion.isEmpty()) {
                throw new IllegalArgumentException("Input parameter 'requestVersion' must not be null or empty");
            }

            if (resultStatus == null || resultStatus.isEmpty()) {
                throw new IllegalArgumentException("Input parameter 'resultStatus' must not be null or empty");
            }

            Builder builder = new Builder();
            builder.eventType = eventType;
            builder.orderNumber = orderNumber;
            builder.messageCreationDate = new Date();
            builder.requestVersion = requestVersion;
            builder.resultStatus = resultStatus;
            return builder;
        }


        private Builder() {
        }


        public Builder totalFraudScore(String totalFraudScore) {
            this.totalFraudScore = totalFraudScore;
            return this;
        }

        public Builder recommendationCode(String recommendationCode) {
            this.recommendationCode = recommendationCode;
            return this;
        }

        public Builder accertifyUser(String accertifyUser) {
            this.accertifyUser = accertifyUser;
            return this;
        }

        public Builder accertifyUserCreationDate(Date accertifyUserCreationDate) {
            this.accertifyUserCreationDate = accertifyUserCreationDate;
            return this;
        }


        public OutboundMessagingEvent build() {
            return new OutboundMessagingEvent(this);
        }

    }


}
