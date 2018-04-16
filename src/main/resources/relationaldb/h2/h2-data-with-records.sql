-- *************************************************************************************************************
-- THIS SCRIPT CREATES THE BASE DATA NEEDED TO BE POPULATED IN THE DATABASE.
-- IT ALSO ADDS SOME RECORDS TO THE TABLES THAT CAN BE USED FOR TESTING DIFFERENT SCENARIOS.
-- *************************************************************************************************************


INSERT INTO ORDER_FRAUD.ID_GENERATOR (GENERATED_NAME, GENERATED_VALUE) VALUES ('FRAUD_RQST_ID', 0);
INSERT INTO ORDER_FRAUD.ID_GENERATOR (GENERATED_NAME, GENERATED_VALUE) VALUES ('FRAUD_RQST_STATUS_HSTRY_ID', 0);
INSERT INTO ORDER_FRAUD.ID_GENERATOR (GENERATED_NAME, GENERATED_VALUE) VALUES ('FRAUD_RQST_STATUS_HSTRY_DTL_ID', 0);

-- *************************************************************************************************************
-- Populate FRAUD_STATUSES with static data (INITIAL_REQUEST, FINAL_DECISION, PENDING_REVIEW, CANCELLED)
-- *************************************************************************************************************

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('INITIAL_REQUEST', 'Initial request received.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('FINAL_DECISION', 'Final fraud decision is made.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('PENDING_REVIEW', 'Fraud decision is pending manual review.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('CANCELLED', 'Fraud assessment is canceled.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');


-- *************************************************************************************************************
-- Populate FRAUD_RQST_TYPES with static data (FRAUD_CHECK, ORDER_CANCEL)
-- *************************************************************************************************************

INSERT INTO ORDER_FRAUD.FRAUD_RQST_TYPES (REQUEST_TYPE_CODE, REQUEST_TYPE_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('FRAUD_CHECK', 'Fraud check request', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_RQST_TYPES (REQUEST_TYPE_CODE, REQUEST_TYPE_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES ('ORDER_CANCEL', 'Order cancellation', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');


-- *************************************************************************************************************
-- Create Initial Request Entry (Fraud Request ID: 1, Request Version: 1, Order Number: 1234)
-- *************************************************************************************************************

INSERT INTO ORDER_FRAUD.FRAUD_RQST(FRAUD_RQST_ID, ORDER_NUMBER, EVENT_DATE, REQUEST_VERSION, REQUEST_TYPE_CODE, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) VALUES (1, 1234, CURRENT_TIMESTAMP, 1, 'FRAUD_CHECK', 'INITIAL_REQUEST', 1, CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT', CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY(FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RQST_ID, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(1, 1, 'INITIAL_REQUEST', 1, (SELECT CREATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 1), (SELECT CREATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 1), (SELECT UPDATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 1), (SELECT UPDATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 1));


-- *************************************************************************************************************
-- Create Final Decision Entry - Accepted (Fraud Request ID: 2, Request Version: 1, Order Number: 1235)
-- *************************************************************************************************************

INSERT INTO ORDER_FRAUD.FRAUD_RQST(FRAUD_RQST_ID, ORDER_NUMBER, EVENT_DATE, REQUEST_VERSION, REQUEST_TYPE_CODE, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) VALUES (2, 1235, CURRENT_TIMESTAMP, 1, 'FRAUD_CHECK', 'FINAL_DECISION', 1, CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT', CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY(FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RQST_ID, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(2, 2, 'INITIAL_REQUEST', 1, (SELECT CREATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT CREATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT UPDATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT UPDATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2));

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY(FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RQST_ID, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(3, 2, 'FINAL_DECISION', 1, (SELECT CREATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT CREATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT UPDATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2), (SELECT UPDATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 2));

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY_DTLS(FRAUD_RQST_STATUS_HSTRY_DTL_ID, FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RESPONSE_STATUS_CODE, TOTAL_FRAUD_SCORE, RECOMMENDATION_CODE, TAS_REQUEST, TAS_RESPONSE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(1, 3, 'ACCEPTED', 1875, '1:Allow', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ManageOrderRequest xmlns="http://bestbuy.com/TenderAuth"><IxTranType>FRAUDCHECK</IxTranType><TransactionData><requestVersion>1</requestVersion><transactionId>1235</transactionId><transactionType>ORDER</transactionType><transactionDateTime>2018-04-04T15:58:25.946-07:00</transactionDateTime><transactionTotalAmount>50</transactionTotalAmount><billingDetails><currencyCode>CAD</currencyCode></billingDetails><member/><items/><shippingOrders><shippingOrder><shippingDetails/></shippingOrder></shippingOrders></TransactionData></ManageOrderRequest>', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ManageOrderResult xmlns="http://bestbuy.com/TenderAuth"><actionCode>SUCCESS</actionCode><errorDescription>?</errorDescription><transaction-results><transaction-id>1235</transaction-id><cross-reference>?</cross-reference><rules-tripped>5237260000000734584:EmailAge Billing Email Domain Country Code Not Equal to IP Country:50;5237260000000734581:EmailAge Billing Email First Seen LT 6 Months Ago:100</rules-tripped><total-score>1875</total-score><recommendation-code>1:Allow</recommendation-code><remarks>AT:  12/16/17 11:49:04 AM EST BUSINESS PROCESS:  1 SCORE:  1,875 USER:  test@accertify.com RESOLUTION:  Allow</remarks><responseData><transaction><reason-code>ACCEPT</reason-code><reason-description>?</reason-description><transaction-details><transaction-detail>?</transaction-detail></transaction-details></transaction><responseVersion>1</responseVersion></responseData></transaction-results></ManageOrderResult>', 1, CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT', CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT');


-- *************************************************************************************************************
-- Create Pending Review Entry (Fraud Request ID: 3, Request Version: 1, Order Number: 1236)
-- *************************************************************************************************************

INSERT INTO ORDER_FRAUD.FRAUD_RQST(FRAUD_RQST_ID, ORDER_NUMBER, EVENT_DATE, REQUEST_VERSION, REQUEST_TYPE_CODE, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) VALUES (3, 1236, CURRENT_TIMESTAMP, 1, 'FRAUD_CHECK', 'PENDING_REVIEW', 1, CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT', CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY(FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RQST_ID, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(4, 3, 'INITIAL_REQUEST', 1, (SELECT CREATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT CREATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT UPDATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT UPDATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3));

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY(FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RQST_ID, FRAUD_STATUS_CODE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(5, 3, 'PENDING_REVIEW', 1, (SELECT CREATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT CREATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT UPDATE_DATE FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3), (SELECT UPDATE_USER FROM ORDER_FRAUD.FRAUD_RQST WHERE FRAUD_RQST_ID = 3));

INSERT INTO ORDER_FRAUD.FRAUD_RQST_STATUS_HSTRY_DTLS(FRAUD_RQST_STATUS_HSTRY_DTL_ID, FRAUD_RQST_STATUS_HSTRY_ID, FRAUD_RESPONSE_STATUS_CODE, TOTAL_FRAUD_SCORE, RECOMMENDATION_CODE, TAS_REQUEST, TAS_RESPONSE, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER)
VALUES(2, 5, 'PENDING_REVIEW', 1875, '1:Allow', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ManageOrderRequest xmlns="http://bestbuy.com/TenderAuth"><IxTranType>FRAUDCHECK</IxTranType><TransactionData><requestVersion>1</requestVersion><transactionId>1236</transactionId><transactionType>ORDER</transactionType><transactionDateTime>2018-04-04T15:57:26.929-07:00</transactionDateTime><transactionTotalAmount>1500</transactionTotalAmount><billingDetails><currencyCode>CAD</currencyCode></billingDetails><member/><items/><shippingOrders><shippingOrder><shippingDetails/></shippingOrder></shippingOrders></TransactionData></ManageOrderRequest>', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ManageOrderResult xmlns="http://bestbuy.com/TenderAuth"><actionCode>CALLBANK</actionCode><errorDescription>?</errorDescription><transaction-results><transaction-id>1236</transaction-id><cross-reference>?</cross-reference><rules-tripped>5237260000000734584:EmailAge Billing Email Domain Country Code Not Equal to IP Country:50;5237260000000734581:EmailAge Billing Email First Seen LT 6 Months Ago:100</rules-tripped><total-score>1875</total-score><recommendation-code>1:Allow</recommendation-code><remarks>AT:  12/16/17 11:49:04 AM EST BUSINESS PROCESS:  1 SCORE:  1,875 USER:  test@accertify.com RESOLUTION:  Allow</remarks><responseData><transaction><reason-code>PENDINGREVIEW</reason-code><reason-description>?</reason-description><transaction-details><transaction-detail>?</transaction-detail></transaction-details></transaction><responseVersion>1</responseVersion></responseData></transaction-results></ManageOrderResult>', 1, CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT', CURRENT_TIMESTAMP, 'ORDER_FRAUD_SCRIPT');