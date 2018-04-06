# Order Fraud Service
A service that integrates with our 3rd-party fraud management solution

# Compile and run

This command will install and run on a developer's local machine.
By default, the spring profile is dev, but can be overriden in a system property.

mvn clean install spring-boot:run -Dspring.profiles.active=dev

## Disabling registration with the management console

Can be achieved by setting the system property 

-Dspring.boot.admin.client.enabled=false

# Checking for updates to compile Dependencies

Use the following commands to check for dependencies

- mvn versions:display-dependency-updates -DprocessDependencyManagementTransitive=false -DprocessDependencyManagement=false
- mvn versions:display-plugin-updates
- mvn versions:display-property-updates

