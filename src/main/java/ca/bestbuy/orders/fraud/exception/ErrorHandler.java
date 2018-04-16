package ca.bestbuy.orders.fraud.exception;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by kundsing on 2018-04-13.
 */

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    /**
     * Handler for ValidationException
     *
     * @param ex {@link ValidationException}
     * @return {@link ErrorResponse} with an HTTP 400 status and an application status of ERROR_INVALID_DATA
     */
    @Order(1)
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(final ValidationException ex) {

        log.warn("Validation error", ex);

        // Create and send error response
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage());
        log.info("HTTP 400 - {}", errorResponse);
        return errorResponse;
    }

    @Order(1)
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleUnexpectedError(final Throwable t) {

        log.error("Unexpected error occurred", t);

        // Create and send error response
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Unexpected error occurred " + t);
        log.info("HTTP 500 - {}", errorResponse);
        return errorResponse;
    }

    @Api
    public class ErrorResponse {

        @Getter
        @ApiModelProperty(position = 1, required = true)
        private final String status;

        @Getter
        @ApiModelProperty(position = 2, required = true)
        private final String message;

        ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        @Override
        public String toString() {
            return "ErrorResponse{" + "status='" + status + '\'' + ", message='" + message + '\'' + '}';
        }
    }
}
