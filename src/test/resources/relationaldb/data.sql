INSERT INTO ORDER_FRAUD.ID_GENERATOR (GENERATED_NAME, GENERATED_VALUE) VALUES ('FRAUD_RQST_ID', 0);
INSERT INTO ORDER_FRAUD.ID_GENERATOR (GENERATED_NAME, GENERATED_VALUE) VALUES ('FRAUD_RQST_STATUS_HSTRY_ID', 0);

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('INITIAL_REQUEST', 'Initial request received.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('DECISION_MADE', 'Final fraud decision is made.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('PENDING_REVIEW', 'Fraud decision is pending manual review.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_STATUSES (FRAUD_STATUS_CODE, FRAUD_STATUS_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('CANCELLED', 'Fraud assessment is canceled.', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');


INSERT INTO ORDER_FRAUD.FRAUD_RQST_TYPES (REQUEST_TYPE_CODE, REQUEST_TYPE_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('FRAUD_CHECK', 'Fraud check request', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');

INSERT INTO ORDER_FRAUD.FRAUD_RQST_TYPES (REQUEST_TYPE_CODE, REQUEST_TYPE_DESCRIPTION, VERSION, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER) 
VALUES ('ORDER_CANCEL', 'Order cancellation', 1, SYSDATE, 'ORDER_FRAUD_SCRIPT', SYSDATE, 'ORDER_FRAUD_SCRIPT');
