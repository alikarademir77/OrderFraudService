package com.bbyc.orders.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.joda.time.DateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.bbyc.orders.model.client.orderdetails.CreditCardInfo;
import com.bbyc.orders.model.client.orderdetails.Discount;
import com.bbyc.orders.model.client.orderdetails.FSOrder;
import com.bbyc.orders.model.client.orderdetails.FSOrderLine;
import com.bbyc.orders.model.client.orderdetails.GiftCardInfo;
import com.bbyc.orders.model.client.orderdetails.ItemCharge;
import com.bbyc.orders.model.client.orderdetails.PaymentMethodInfo;
import com.bbyc.orders.model.client.orderdetails.PurchaseOrder;
import com.bbyc.orders.model.client.orderdetails.ShippingCharge;
import com.bbyc.orders.model.client.orderdetails.ShippingOrderLine;
import com.bbyc.orders.model.client.orderdetails.Surcharge;
import com.bbyc.orders.model.client.orderdetails.Tax;
import com.bbyc.orders.model.internal.Address;
import com.bbyc.orders.model.internal.Item;
import com.bbyc.orders.model.internal.Order;
import com.bbyc.orders.model.internal.PaymentDetails;
import com.bbyc.orders.model.internal.ShippingOrder;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {


    @Mappings({
        @Mapping(target = "webOrderRefID", source = "webOrderRefId"),
        @Mapping(target = "fsOrderID", source = "id"),
        @Mapping(target = "csrSalesRepID", ignore = true), // TODO - Figure out how to get value
        @Mapping(target = "ipAddress", source = "ipAddress"),
        @Mapping(target = "webOrderCreationDate", source = "webOrderCreationDate"),
        @Mapping(target = "rewardZoneID", source = "rewardZone.rewardZoneId"),
        @Mapping(target = "items", source = "fsOrderLines"),
        @Mapping(target = "purchaseOrders", source = "purchaseOrders"),
        @Mapping(target = "shippingOrders", source = "shippingOrders"),
        @Mapping(target = "paymentDetails", source = "paymentMethodInfo")
    })
    public abstract Order mapOrder(FSOrder fsOrderToMap);


    @Mappings({
        @Mapping(target = "fsoLineID", source = "id"),
        @Mapping(target = "name", source = "product.name"),
        @Mapping(target = "category", ignore = true), // TODO - Figure out how to get value
        @Mapping(target = "itemUnitPrice", source = "itemCharge.unitPrice"),
        @Mapping(target = "itemQuantity", source = "qtyOrdered"),
        @Mapping(target = "itemTax", ignore = true), // Handled by custom mapping - mapItem_ItemTax()
        @Mapping(target = "itemDiscounts", source = "itemCharge.discounts") // TODO - Write custom mapping
    })
    protected abstract Item mapItem(FSOrderLine fsOrderLineToMap);


    @AfterMapping
    protected void mapItem_ItemTax(FSOrderLine fsOrderLineToMap, @MappingTarget Item mappedItem) {
        ItemCharge itemChargeToMap = fsOrderLineToMap.getItemCharge();
        if(itemChargeToMap != null) {
            Tax taxToMap = itemChargeToMap.getTax();
            if(taxToMap != null) {
                float totalTax = taxToMap.getGst() + taxToMap.getPst();
                mappedItem.setItemTax(totalTax);
            }
        }
    }


    @Mappings({
        @Mapping(target = "purchaseOrderID", source = "id"),
        @Mapping(target = "purchaseOrderStatus", source = "poSendStatus.name"),
        @Mapping(target = "shippingOrderRefID", source = "shippingOrderRefId")
    })
    protected abstract com.bbyc.orders.model.internal.PurchaseOrder mapPurchaseOrder(PurchaseOrder purchaseOrderToMap);


    @Mappings({
        @Mapping(target = "shippingOrderID", source = "id"),
        @Mapping(target = "globalContractID", source = "globalContractRefId"),
        @Mapping(target = "shippingOrderStatus", source = "status.name"),
        @Mapping(target = "shippingCharge", ignore = true), // Handled by custom mapping - mapShippingOrder_ShippingChargesAndTax()
        @Mapping(target = "shippingTax", ignore = true), // Handled by custom mapping - mapShippingOrder_ShippingChargesAndTax()
        @Mapping(target = "fulfillmentPartner", source = "fulfillmentPartner"),
        @Mapping(target = "shippingMethod", source = "requestedCarrier.levelOfService.id"),
        @Mapping(target = "deliveryDate", source = "requestedCarrier.levelOfService.deliveryDate"),
        @Mapping(target = "shippingAddress", source = "shipToAddress"),
        @Mapping(target = "shippingOrderLines", source = "shippingOrderLines"),
        @Mapping(target = "chargebacks", ignore = true) // TODO - Figure out how to get value
    })
    protected abstract ShippingOrder mapShippingOrder(com.bbyc.orders.model.client.orderdetails.ShippingOrder shippingOrderToMap);


    @AfterMapping
    protected void mapShippingOrder_ShippingChargesAndTax(com.bbyc.orders.model.client.orderdetails.ShippingOrder shippingOrderToMap, @MappingTarget ShippingOrder mappedShippingOrder) {

        float totalShippingCharge = 0.0f;
        float totalShippingChargeTax = 0.0f;
        float totalShippingDiscount = 0.0f;

        if(shippingOrderToMap.getShippingCharges() == null){
            return;
        }

        for(ShippingCharge shippingChargeToMap : shippingOrderToMap.getShippingCharges()) {

            // Add shipping charge to total shipping charge
            totalShippingCharge += shippingChargeToMap.getUnitPrice();

            // Add shipping charge tax to total shipping charge tax
            Tax shippingChargeTaxToMap = shippingChargeToMap.getTax();
            if(shippingChargeTaxToMap != null) {
                totalShippingChargeTax += shippingChargeTaxToMap.getGst();
                totalShippingChargeTax += shippingChargeTaxToMap.getPst();
            }



            if(shippingChargeToMap.getDiscounts() != null) {
                // Add shipping charge discounts to total shipping charge discounts
                List<Discount> shippingDiscounts = shippingChargeToMap.getDiscounts();
                for (Discount shippingDiscount : shippingDiscounts) {
                    totalShippingDiscount += (shippingDiscount.getUnitValue() * shippingDiscount.getQuantity());
                }
            }

        }



        mappedShippingOrder.setShippingCharge(totalShippingCharge - totalShippingDiscount);
        mappedShippingOrder.setShippingTax(totalShippingChargeTax);
    }


    @Mappings({
        @Mapping(target = "shippingOrderLineID", source = "id"),
        @Mapping(target = "shippingOrderLineStatus", source = "status.name"),
        @Mapping(target = "fsoLineRefID", source = "fsoLineRefId"),
        @Mapping(target = "quantity", source = "qtyOrdered"),
        @Mapping(target = "shippingCharge", ignore = true), // Handled by custom mapping - mapShippingOrderLine_ShippingChargesAndTax()
        @Mapping(target = "shippingTax", ignore = true), // Handled by custom mapping - mapShippingOrderLine_ShippingChargesAndTax()
        @Mapping(target = "shippingDiscount", ignore = true), // Handled by custom mapping - mapShippingOrderLine_ShippingChargesAndTax()
        @Mapping(target = "ehf", ignore = true), // Handled by custom mapping - mapShippingOrderLine_Surcharges()
        @Mapping(target = "ehfTax", ignore = true) // Handled by custom mapping - mapShippingOrderLine_Surcharges()
    })
    protected abstract com.bbyc.orders.model.internal.ShippingOrderLine mapShippingOrderLine(ShippingOrderLine shippingOrderLineToMap);


    @AfterMapping
    protected void mapShippingOrderLine_ShippingChargesAndTax(ShippingOrderLine shippingOrderLineToMap, @MappingTarget com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine) {

        float totalShippingCharge = 0.0f;
        float totalShippingChargeTax = 0.0f;
        float totalShippingDiscount = 0.0f;

        if(shippingOrderLineToMap.getShippingCharges() == null){
            return;
        }

        for(ShippingCharge shippingLineChargeToMap : shippingOrderLineToMap.getShippingCharges()) {

            // Add shipping charge to total shipping charge
            totalShippingCharge += shippingLineChargeToMap.getUnitPrice();

            // Add shipping charge tax to total shipping charge tax
            Tax shippingChargeTaxToMap = shippingLineChargeToMap.getTax();
            if(shippingChargeTaxToMap != null) {
                totalShippingChargeTax += shippingChargeTaxToMap.getGst();
                totalShippingChargeTax += shippingChargeTaxToMap.getPst();
            }

            // Add shipping charge discounts to total shipping charge discounts
            List<Discount> shippingDiscounts = shippingLineChargeToMap.getDiscounts();
            for(Discount shippingLineDiscount : shippingDiscounts) {
                totalShippingDiscount += (shippingLineDiscount.getUnitValue() * shippingLineDiscount.getQuantity());
            }

        }

        mappedShippingOrderLine.setShippingCharge(totalShippingCharge);
        mappedShippingOrderLine.setShippingTax(totalShippingChargeTax);
        mappedShippingOrderLine.setShippingDiscount(totalShippingDiscount);
    }


    @AfterMapping
    protected void mapShippingOrderLine_Surcharges(ShippingOrderLine shippingOrderLineToMap, @MappingTarget com.bbyc.orders.model.internal.ShippingOrderLine mappedShippingOrderLine) {

        float totalEhf = 0.0f;
        float totalEhfTax = 0.0f;

        if(shippingOrderLineToMap.getSurcharges() == null){
            return;
        }

        for(Surcharge shippingLineSurchargeToMap : shippingOrderLineToMap.getSurcharges()) {

            // Add surcharge to total ehf
            totalEhf += shippingLineSurchargeToMap.getTotalValue();

            // Add ehf tax to total ehf tax
            Tax surchargeTaxToMap = shippingLineSurchargeToMap.getTax();
            if(surchargeTaxToMap != null) {
                totalEhfTax += surchargeTaxToMap.getGst();
                totalEhfTax += surchargeTaxToMap.getPst();
            }

        }

        mappedShippingOrderLine.setEhf(totalEhf);
        mappedShippingOrderLine.setEhfTax(totalEhfTax);
    }


    @Mappings({
        @Mapping(target = "creditCards", source = "creditCards"),
        @Mapping(target = "giftCards", source = "giftCards"),
        @Mapping(target = "payPal", ignore = true) // TODO - Figure out how to get value
    })
    protected abstract PaymentDetails mapPaymentDetails(PaymentMethodInfo paymentDetailsToMap);


    @Mappings({
        @Mapping(target = "billingAddress", source = "billingAddress"),
        @Mapping(target = "creditCardNumber", source = "creditCardNumber"),
        @Mapping(target = "creditCardType", source = "creditCardType"),
        @Mapping(target = "creditCardExpiryDate", source = "creditCardExpiryDate"),
        @Mapping(target = "creditCardAvsResponse", ignore = true), // TODO - Figure out how to get value
        @Mapping(target = "creditCardCvvResponse", ignore = true), // TODO - Figure out how to get value
        @Mapping(target = "creditCard3dSecureValue", ignore = true), // TODO - Figure out how to get value
        @Mapping(target = "totalAuthorizedAmount", ignore = true) // TODO - Figure out how to get value
    })
    protected abstract PaymentDetails.CreditCard mapCreditCard(CreditCardInfo creditCardToMap);


    @Mappings({
        @Mapping(target = "giftCardNumber", source = "giftCardNumber")
    })
    protected abstract PaymentDetails.GiftCard mapGiftCard(GiftCardInfo giftCardToMap);


    @Mappings({
        @Mapping(target = "firstName", source = "firstName"),
        @Mapping(target = "lastName", source = "lastName"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "address1", source = "address1"),
        @Mapping(target = "address2", source = "address2"),
        @Mapping(target = "city", source = "city"),
        @Mapping(target = "province", source = "province"),
        @Mapping(target = "postalCode", source = "postalCode"),
        @Mapping(target = "country", source = "country"),
        @Mapping(target = "phoneNumber", source = "phone"),
        @Mapping(target = "secondaryPhoneNumber", source = "phone2")
    })
    protected abstract Address mapAddress(com.bbyc.orders.model.client.orderdetails.Address addressToMap);


    protected LocalDateTime mapDateTime(DateTime dateTime) {

        if(dateTime != null) {
            Instant instant = Instant.ofEpochMilli(dateTime.getMillis());

            ZonedDateTime ldtZoned = instant.atZone(ZoneId.systemDefault());

            ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));

            LocalDateTime localDateTime = utcZoned.toLocalDateTime();

            return localDateTime;
        }

        return null;
    }


}
