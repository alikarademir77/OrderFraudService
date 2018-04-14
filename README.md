# Order Fraud Service
A service that integrates with our 3rd-party fraud management solution

## Compile and run

This command will install and run on a developer's local machine.
By default, the spring profile is dev, but can be overriden in a system property.

```
mvn clean install spring-boot:run -Dspring.profiles.active=dev
```

## Disabling registration with the management console

Can be achieved by setting the system property 

```
-Dspring.boot.admin.client.enabled=false
```

## Using H2 in-memory database for local development

Use the *h2* profile along with the *dev* spring profile when starting your application.

Additionally, you will need to change the scope of the H2 dependency in the **pom.xml** to *runtime* as follows:

```
<!--H2 In Memory Database -->
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

## Publishing messages to local RabbitMQ using the management REST API
If you have RabbitMQ set up locally with the management plugin enabled, you are able to publish messages directly to the exchanges using the management's REST API.

To do so, you can use the following endpoint:
```
POST /api/exchanges/{virtualhost}/{exchangeName}/publish
```
The request body that you send must look like the following below:
```
{
	"properties":{
		"content-type": "application/json"
	},
	"routing_key":"my_key",
	"payload":"{\"type\":\"FraudCheck\",\"orderNumber\":\"1235\",\"requestVersion\":\"1\",\"messageCreationDate\":\"02/02/2018 18:23:09\"}",
	"payload_encoding":"string"
}
```


# Checking for updates to compile Dependencies

Use the following commands to check for dependencies

```
mvn versions:display-dependency-updates -DprocessDependencyManagementTransitive=false -DprocessDependencyManagement=false
```
```
mvn versions:display-plugin-updates
```
```
mvn versions:display-property-updates
```