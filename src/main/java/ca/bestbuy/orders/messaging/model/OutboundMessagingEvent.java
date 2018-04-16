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
public final class OutboundMessagingEvent<T> {

    private EventTypes type;

    private String requestVersion;

    private String orderNumber;

    @JsonFormat(timezone = "UTC")
    private Date messageCreationDate;

    private T result;


    @JsonCreator
    public OutboundMessagingEvent(EventTypes type, String requestVersion, String orderNumber, T result) {

        if (type == null) {
            throw new IllegalArgumentException("Input parameter 'type' must not be null");
        }

        if (orderNumber == null || orderNumber.isEmpty()) {
            throw new IllegalArgumentException("Input parameter 'orderNumber' must not be null or empty");
        }

        if (requestVersion == null || requestVersion.isEmpty()) {
            throw new IllegalArgumentException("Input parameter 'requestVersion' must not be null or empty");
        }

        this.type = type;
        this.requestVersion = requestVersion;
        this.orderNumber = orderNumber;
        this.messageCreationDate = new Date();
        this.result = result;
    }

}
