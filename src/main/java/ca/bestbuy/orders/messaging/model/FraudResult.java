package ca.bestbuy.orders.messaging.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class FraudResult {

    private String status;

    private String totalFraudScore;

    private String recommendationCode;

    private String accertifyUser;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss", timezone = "UTC")
    private Date accertifyUserCreationDate;


    private FraudResult(Builder builder) {
        this.status = builder.resultStatus;
        this.recommendationCode = builder.recommendationCode;
        this.totalFraudScore = builder.totalFraudScore;
        this.accertifyUser = builder.accertifyUser;
        this.accertifyUserCreationDate = builder.accertifyUserCreationDate;
    }


    public static class Builder {

        private String resultStatus;

        private String totalFraudScore;

        private String recommendationCode;

        private String accertifyUser;

        private Date accertifyUserCreationDate;


        public static Builder create(String resultStatus) {

            if (resultStatus == null || resultStatus.isEmpty()) {
                throw new IllegalArgumentException("Input parameter 'resultStatus' must not be null or empty");
            }

            Builder builder = new Builder();
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


        public FraudResult build() {
            return new FraudResult(this);
        }

    }

}
