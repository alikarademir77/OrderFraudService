package ca.bestbuy.orders.fraud.mappers;



import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayPalPayment;
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class PaymentServiceResponseMapper {


    @Mappings({
//            @Mapping(target = "payPalBillingAgreementId", source = "billingAggrementId"),
            @Mapping(target = "payPalOrderId", ignore = true), //todo: map based on which paypal order is not closed
            @Mapping(target = "verifiedStatus", source = "payerStatus"),
            @Mapping(target = "email", source = "payerEmail")
    })
    public abstract PaymentDetails.PayPal.PayPalAdditionalInfo mapPayPalAdditionalInfo(PayPalPayment payPalPayment);

}
