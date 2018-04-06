package ca.bestbuy.orders.messaging.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import ca.bestbuy.orders.messaging.EventTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
public final class OutboundMessagingEvent {

    private final EventTypes type;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone="UTC")
    private final Date messageCreationDate;

    private final FraudResult result;


    @JsonCreator
    public OutboundMessagingEvent(final EventTypes type, final Date messageCreationDate, final FraudResult result) {
        this.type = type;
        this.messageCreationDate = messageCreationDate;
        this.result = result;
    }


    @Getter
    @ToString
    public static class FraudResult {

        private final String status;

        private final long requestVersion;

        private final String totalFraudScore;

        private final String orderNumber;

        @JsonCreator
        public FraudResult(String status, long requestVersion, String totalFraudScore, String orderNumber) {
            this.status = status;
            this.requestVersion = requestVersion;
            this.totalFraudScore = totalFraudScore;
            this.orderNumber = orderNumber;
        }

    }

}
