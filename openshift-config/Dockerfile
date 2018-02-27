FROM ecomm-devops-team/bby-rhel7-atomic-java

ENV APP_NAME=bby-orders-fraud-service
ENV CERT_PATH=/etc/pki/ca-trust/extracted/java/cacerts
ENV PROFILE=dev
ENV PORT=8080
ENV JAVA_TRUSTORE_OPTS=-Djavax.net.ssl.trustStore=$CERT_PATH -Djavax.net.ssl.trustStorePassword=$SSL_TRUST_STORE_PWD

RUN mkdir -p /opt/app/${APP_NAME}

WORKDIR /opt/app/${APP_NAME}

ADD ${APP_NAME}.jar .

ENTRYPOINT java -jar ${APP_NAME}.jar --server.port=$PORT