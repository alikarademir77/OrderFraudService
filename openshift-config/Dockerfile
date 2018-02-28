FROM registry.dev-ocp.ca.bestbuy.com/ecomm-devops-team/bby-rhel7-atomic-java:latest

ENV APP_NAME=bby-orders-fraud-service \
    CERT_PATH=/etc/pki/ca-trust/extracted/java/cacerts \
    PROFILE=dev \
    PORT=8080 \
    JAVA_TRUSTORE_OPTS=-Djavax.net.ssl.trustStore=$CERT_PATH -Djavax.net.ssl.trustStorePassword=$SSL_TRUST_STORE_PWD

RUN mkdir -p /opt/app/${APP_NAME}

WORKDIR /opt/app/${APP_NAME}

ADD ${APP_NAME}.jar .

ENTRYPOINT java -jar ${APP_NAME}.jar --server.port=$PORT