# command-service
A web service that exposes a RESTful api to trigger commands on a host. The commands can be configured via config file. First implementation will be Spring Boot but I plan to make a version for GoLang and Python too. 

## Start
To start the spring boot application do the following
mvn package
java -jar target/command-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=<your_profile_name>