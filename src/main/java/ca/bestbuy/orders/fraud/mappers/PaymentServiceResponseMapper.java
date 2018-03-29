package ca.bestbuy.orders.fraud.mappers;



import ca.bestbuy.orders.fraud.client.paymentservice.MissingInformationException;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.OrderStatus;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayPalOrder;
import ca.bestbuy.orders.fraud.model.client.generated.paymentservice.wsdl.PayPalPayment;
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PaymentServiceResponseMapper {


    @Mappings({
//            @Mapping(target = "payPalBillingAgreementId", source = "billingAggrementId"),
            @Mapping(target = "payPalOrderId", ignore = true), //handled by custom mapper mapPayPalAdditionalInfo_PayPalOrderId()
            @Mapping(target = "verifiedStatus", source = "payerStatus"),
            @Mapping(target = "email", source = "payerEmail")
    })
    public abstract PaymentDetails.PayPal.PayPalAdditionalInfo mapPayPalAdditionalInfo(PayPalPayment payPalPayment);



    @AfterMapping
    public void mapPayPalAdditionalInfo_PayPalOrderId(PayPalPayment payPalPayment, @MappingTarget PaymentDetails.PayPal.PayPalAdditionalInfo payPalAdditionalInfoToMap) throws MissingInformationException{

        List<PayPalOrder> payPalOrderList = payPalPayment.getPayPalOrders().getPayPalOrder();
        Boolean activeOrderFound = false;

        for(PayPalOrder payPalOrder : payPalOrderList) {

            //check for ACTIVE paypal order
            if(payPalOrder.getOrderStatus().equals(OrderStatus.ACTIVE)){
                payPalAdditionalInfoToMap.payPalOrderId = payPalOrder.getPayPalOrderId();
                activeOrderFound = true;
            }

        }

        if(!activeOrderFound){
            throw new MissingInformationException("There was no ACTIVE PayPal Order found");
        }

    }
}
