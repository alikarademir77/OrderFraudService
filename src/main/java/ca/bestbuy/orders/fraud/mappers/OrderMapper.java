package ca.bestbuy.orders.fraud.mappers;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import ca.bestbuy.orders.fraud.model.client.orderdetails.CreditCardInfo;
import ca.bestbuy.orders.fraud.model.client.orderdetails.Discount;
import ca.bestbuy.orders.fraud.model.client.orderdetails.FSOrder;
import ca.bestbuy.orders.fraud.model.client.orderdetails.FSOrderLine;
import ca.bestbuy.orders.fraud.model.client.orderdetails.GiftCardInfo;
import ca.bestbuy.orders.fraud.model.client.orderdetails.ItemCharge;
import ca.bestbuy.orders.fraud.model.client.orderdetails.ItemChargeDiscount;
import ca.bestbuy.orders.fraud.model.client.orderdetails.PaymentMethodInfo;
import ca.bestbuy.orders.fraud.model.client.orderdetails.PurchaseOrder;
import ca.bestbuy.orders.fraud.model.client.orderdetails.ShippingCharge;
import ca.bestbuy.orders.fraud.model.client.orderdetails.ShippingOrderLine;
import ca.bestbuy.orders.fraud.model.client.orderdetails.Surcharge;
import ca.bestbuy.orders.fraud.model.client.orderdetails.Tax;
import ca.bestbuy.orders.fraud.model.internal.Address;
import ca.bestbuy.orders.fraud.model.internal.Item;
import ca.bestbuy.orders.fraud.model.internal.Order;
import ca.bestbuy.orders.fraud.model.internal.PaymentDetails;
import ca.bestbuy.orders.fraud.model.internal.ShippingOrder;

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
            @Mapping(target = "paymentDetails", source = "paymentMethodInfo"),
            @Mapping(target = "enterpriseCustomerId", ignore = true), // TODO - Figure out how to get value
            @Mapping(target = "salesChannel", ignore = true), // TODO - Figure out how to get value
            @Mapping(target = "orderMessage",ignore = true) // TODO - Figure out how to get value
    })
    public abstract Order mapOrder(FSOrder fsOrderToMap);


    @Mappings({
            @Mapping(target = "fsoLineID", source = "id"),
            @Mapping(target = "name", source = "product.name"),
            @Mapping(target = "category", ignore = true), // TODO - Figure out how to get value
            @Mapping(target = "itemUnitPrice", source = "itemCharge.unitPrice"),
            @Mapping(target = "itemQuantity", source = "qtyOrdered"),
            @Mapping(target = "itemTax", ignore = true), // Handled by custom mapping - mapItem_ItemTax()
            @Mapping(target = "itemTotalDiscount", ignore = true), // Handled by custom mapping - mapItem_Discounts()
            @Mapping(target = "staffDiscount", ignore = true), // Handled by custom mapping - mapItem_Discounts()
            @Mapping(target = "itemStatus", ignore = true), // TODO - Need to add to Order Details
            @Mapping(target = "itemSkuNumber", ignore = true), // TODO - Need to add to Order Details
            @Mapping(target = "itemSkuDescription", ignore = true), // TODO - Need to add to Order Details
            @Mapping(target = "postCaptureDiscount", ignore = true) // TODO - Need to add to Order Details

    })
    protected abstract Item mapItem(FSOrderLine fsOrderLineToMap);


    @AfterMapping
    protected void mapItem_ItemTax(FSOrderLine fsOrderLineToMap, @MappingTarget Item mappedItem) {
        ItemCharge itemChargeToMap = fsOrderLineToMap.getItemCharge();
        if (itemChargeToMap != null) {
            Tax taxToMap = itemChargeToMap.getTax();
            if (taxToMap != null) {
                try {
                    BigDecimal totalTax = new BigDecimal(0.0);

                    if(!StringUtils.isBlank(taxToMap.getGst())) {
                        BigDecimal gst = new BigDecimal(taxToMap.getGst());
                        totalTax = totalTax.add(gst);
                    }

                    if(!StringUtils.isBlank(taxToMap.getPst())) {
                        BigDecimal pst = new BigDecimal(taxToMap.getPst());
                        totalTax = totalTax.add(pst);
                    }

                    mappedItem.setItemTax(totalTax);

                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapItem_ItemTax()", e);
                }
            }
        }
    }

    @AfterMapping
    protected  void mapItem_Discounts(FSOrderLine fsOrderLineToMap, @MappingTarget Item mappedItem){
        ItemCharge itemChargeToMap = fsOrderLineToMap.getItemCharge();
        BigDecimal totalDiscount = new BigDecimal(0.0);
        BigDecimal staffDiscount = new BigDecimal(0.0);

        if(itemChargeToMap == null || itemChargeToMap.getDiscounts() == null){
            return;
        }

        for(ItemChargeDiscount discount : itemChargeToMap.getDiscounts()){

            try {

                if(!StringUtils.isBlank(discount.getUnitValue())) {
                    BigDecimal quantity = new BigDecimal(discount.getQuantity());
                    BigDecimal unitValue = new BigDecimal(discount.getUnitValue());

                    totalDiscount = totalDiscount.add(quantity.multiply(unitValue));

                    if(discount.getCode() != null && discount.getCode().equals("SP")){
                        staffDiscount = staffDiscount.add(quantity.multiply(unitValue));
                    }

                }

            } catch (NumberFormatException e) {
                throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapItem_Discounts()", e);
            }
        }

        mappedItem.setStaffDiscount(staffDiscount);
        mappedItem.setItemTotalDiscount(totalDiscount);
    }

    @Mappings({
            @Mapping(target = "purchaseOrderID", source = "id"),
            @Mapping(target = "purchaseOrderStatus", source = "poSendStatus.name"),
            @Mapping(target = "shippingOrderRefID", source = "shippingOrderRefId")
    })
    protected abstract ca.bestbuy.orders.fraud.model.internal.PurchaseOrder mapPurchaseOrder(PurchaseOrder purchaseOrderToMap);


    @Mappings({
            @Mapping(target = "shippingOrderID", source = "id"),
            @Mapping(target = "globalContractID", source = "globalContractRefId"),
            @Mapping(target = "shippingOrderStatus", source = "status.name"),
            @Mapping(target = "shippingCharge", ignore = true), // Handled by custom mapping - mapShippingOrder_ShippingChargesAndTax()
            @Mapping(target = "shippingTax", ignore = true), // Handled by custom mapping - mapShippingOrder_ShippingChargesAndTax()
            @Mapping(target = "shippingDiscount", ignore = true), // Handled by custom mapping - mapShippingOrder_ShippingChargesAndTax()
            @Mapping(target = "fulfillmentPartner", source = "fulfillmentPartner"),
            @Mapping(target = "shippingMethod", source = "requestedCarrier.levelOfService.id"),
            @Mapping(target = "deliveryDate", source = "requestedCarrier.levelOfService.deliveryDate"),
            @Mapping(target = "shippingAddress", source = "shipToAddress"),
            @Mapping(target = "shippingOrderLines", source = "shippingOrderLines"),
            @Mapping(target = "chargebacks", ignore = true) // TODO - Figure out how to get value
    })
    protected abstract ShippingOrder mapShippingOrder(ca.bestbuy.orders.fraud.model.client.orderdetails.ShippingOrder shippingOrderToMap);


    @AfterMapping
    protected void mapShippingOrder_ShippingChargesAndTax(ca.bestbuy.orders.fraud.model.client.orderdetails.ShippingOrder shippingOrderToMap, @MappingTarget ShippingOrder mappedShippingOrder) {

        BigDecimal totalShippingCharge = new BigDecimal(0.0);
        BigDecimal totalShippingChargeTax = new BigDecimal(0.0);
        BigDecimal totalShippingDiscount = new BigDecimal(0.0);

        if (shippingOrderToMap.getShippingCharges() == null) {
            return;
        }

        for (ShippingCharge shippingChargeToMap : shippingOrderToMap.getShippingCharges()) {

            if(!StringUtils.isBlank(shippingChargeToMap.getUnitPrice())) {
                try {
                    BigDecimal shippingCharge = new BigDecimal(shippingChargeToMap.getUnitPrice());
                    totalShippingCharge = totalShippingCharge.add(shippingCharge);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrder_ShippingChargesAndTax() - shipping charge", e);
                }
            }

            // Add shipping charge tax to total shipping charge tax
            Tax shippingChargeTaxToMap = shippingChargeToMap.getTax();
            if (shippingChargeTaxToMap != null) {
                try {
                    if(!StringUtils.isBlank(shippingChargeTaxToMap.getGst())) {
                        BigDecimal gst = new BigDecimal(shippingChargeTaxToMap.getGst());
                        totalShippingChargeTax = totalShippingChargeTax.add(gst);
                    }

                    if(!StringUtils.isBlank(shippingChargeTaxToMap.getPst())) {
                        BigDecimal pst = new BigDecimal(shippingChargeTaxToMap.getPst());
                        totalShippingChargeTax = totalShippingChargeTax.add(pst);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrder_ShippingChargesAndTax() - shipping tax", e);
                }
            }

            List<Discount> shippingDiscounts = shippingChargeToMap.getDiscounts();
            if (shippingDiscounts != null) {
                // Add shipping charge discounts to total shipping charge discounts
                for (Discount shippingDiscount : shippingDiscounts) {
                    try {
                        if(!StringUtils.isBlank(shippingDiscount.getUnitValue())) {
                            BigDecimal quantity = new BigDecimal(shippingDiscount.getQuantity());
                            BigDecimal unitValue = new BigDecimal(shippingDiscount.getUnitValue());
                            totalShippingDiscount = totalShippingDiscount.add(quantity.multiply(unitValue));
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrder_ShippingChargesAndTax() - shipping discount", e);
                    }
                }
            }

        }

        mappedShippingOrder.setShippingCharge(totalShippingCharge);
        mappedShippingOrder.setShippingTax(totalShippingChargeTax);
        mappedShippingOrder.setShippingDiscount(totalShippingDiscount);
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
    protected abstract ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine mapShippingOrderLine(ShippingOrderLine shippingOrderLineToMap);


    @AfterMapping
    protected void mapShippingOrderLine_ShippingChargesAndTax(ShippingOrderLine shippingOrderLineToMap, @MappingTarget ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine mappedShippingOrderLine) {

        BigDecimal totalShippingCharge = new BigDecimal(0.0);
        BigDecimal totalShippingChargeTax = new BigDecimal(0.0);
        BigDecimal totalShippingDiscount = new BigDecimal(0.0);

        if (shippingOrderLineToMap.getShippingCharges() == null) {
            return;
        }

        for (ShippingCharge shippingLineChargeToMap : shippingOrderLineToMap.getShippingCharges()) {

            // Add shipping charge to total shipping charge
            if(!StringUtils.isBlank(shippingLineChargeToMap.getUnitPrice())) {
                try {
                    BigDecimal shippingCharge = new BigDecimal(shippingLineChargeToMap.getUnitPrice());
                    totalShippingCharge = totalShippingCharge.add(shippingCharge);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrderLine_ShippingChargesAndTax() - shipping line charge", e);
                }
            }

            // Add shipping charge tax to total shipping charge tax
            Tax shippingChargeTaxToMap = shippingLineChargeToMap.getTax();
            if (shippingChargeTaxToMap != null) {

                try {
                    if(!StringUtils.isBlank(shippingChargeTaxToMap.getGst())) {
                        BigDecimal gst = new BigDecimal(shippingChargeTaxToMap.getGst());
                        totalShippingChargeTax = totalShippingChargeTax.add(gst);
                    }

                    if(!StringUtils.isBlank(shippingChargeTaxToMap.getPst())) {
                        BigDecimal pst = new BigDecimal(shippingChargeTaxToMap.getPst());
                        totalShippingChargeTax = totalShippingChargeTax.add(pst);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrderLine_ShippingChargesAndTax() - shipping line tax", e);
                }
            }

            // Add shipping charge discounts to total shipping charge discounts
            List<Discount> shippingDiscounts = shippingLineChargeToMap.getDiscounts();
            for (Discount shippingLineDiscount : shippingDiscounts) {
                try {

                    if(!StringUtils.isBlank(shippingLineDiscount.getUnitValue())) {
                        BigDecimal quantity = new BigDecimal(shippingLineDiscount.getQuantity());
                        BigDecimal unitValue = new BigDecimal(shippingLineDiscount.getUnitValue());

                        totalShippingDiscount = totalShippingDiscount.add(quantity.multiply(unitValue));
                    }

                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrderLine_ShippingChargesAndTax() - shipping line discount", e);
                }
            }

        }

        mappedShippingOrderLine.setShippingCharge(totalShippingCharge);
        mappedShippingOrderLine.setShippingTax(totalShippingChargeTax);
        mappedShippingOrderLine.setShippingDiscount(totalShippingDiscount);
    }


    @AfterMapping
    protected void mapShippingOrderLine_Surcharges(ShippingOrderLine shippingOrderLineToMap, @MappingTarget ca.bestbuy.orders.fraud.model.internal.ShippingOrderLine mappedShippingOrderLine) {

        BigDecimal totalEhf = new BigDecimal(0.0);
        BigDecimal totalEhfTax = new BigDecimal(0.0);

        if (shippingOrderLineToMap.getSurcharges() == null) {
            return;
        }

        for (Surcharge shippingLineSurchargeToMap : shippingOrderLineToMap.getSurcharges()) {

            // Add surcharge to total ehf
            if(!StringUtils.isBlank(shippingLineSurchargeToMap.getTotalValue())) {
                try {
                    BigDecimal shippingLineSurcharge = new BigDecimal(shippingLineSurchargeToMap.getTotalValue());
                    totalEhf = totalEhf.add(shippingLineSurcharge);
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrderLine_Surcharges() - shipping line ehf", e);
                }
            }

            // Add ehf tax to total ehf tax
            Tax surchargeTaxToMap = shippingLineSurchargeToMap.getTax();
            if (surchargeTaxToMap != null) {

                try {
                    if(!StringUtils.isBlank(surchargeTaxToMap.getGst())) {
                        BigDecimal gst = new BigDecimal(surchargeTaxToMap.getGst());
                        totalEhfTax = totalEhfTax.add(gst);
                    }

                    if(!StringUtils.isBlank(surchargeTaxToMap.getPst())) {
                        BigDecimal pst = new BigDecimal(surchargeTaxToMap.getPst());
                        totalEhfTax = totalEhfTax.add(pst);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalStateException("Error while trying to map Order Detail Response to Order Fraud Service domain objects - mapShippingOrderLine_Surcharges() - shipping line ehf tax", e);
                }
            }

        }

        mappedShippingOrderLine.setEhf(totalEhf);
        mappedShippingOrderLine.setEhfTax(totalEhfTax);
    }


    @Mappings({
            @Mapping(target = "creditCards", source = "creditCards"),
            @Mapping(target = "giftCards", source = "giftCards"),
            @Mapping(target = "payPal", ignore = true) // TODO - Figure out how to get value -- might need to call Payment Service
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
            @Mapping(target = "totalAuthorizedAmount", ignore = true), // TODO - Figure out how to get value
            @Mapping(target="status",ignore=true) // TODO - Figure out how to get value
    })
    protected abstract PaymentDetails.CreditCard mapCreditCard(CreditCardInfo creditCardToMap);


    @Mappings({
            @Mapping(target = "giftCardNumber", source = "giftCardNumber"),
            @Mapping(target = "totalAuthorizedAmount", ignore = true), // TODO - Figure out how to get value
            @Mapping(target="status",ignore=true) // TODO - Figure out how to get value

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
    protected abstract Address mapAddress(ca.bestbuy.orders.fraud.model.client.orderdetails.Address addressToMap);


}
