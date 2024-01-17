# Local start with Kafka and Zookeeper
This requires installment and starting of Zookeeper and Kafka

    1: Start Zookeeper
       brew services start zookeeper
    2: Start Kafka
       brew services start kafka
    4: Start police-service
    5: Use the Swagger URL to call the endpoints
    6: Stop police-service
    7: Stop first Kafka
       brew services stop Kafka
    8: Stop Zookeeper
       bres services stop Zookeeper

Both calls to Police API and the exposed Alert endpoint is working and publishing Kafka events

# Start police service with profile dev
Two ways to start the service

    1: java -jar -Dspring.profiles.active=dev target/police-service-0.0.1-SNAPSHOT.jar
    2: Use the Intellij IDE to start the service after setting profile to dev in edit configurations

# Swagger URL for local server start

Swagger URL: http://localhost:8089/police-service/swagger-ui/index.html#/

# Dockerized start
Police-service is a part of an news agency architectural design that is build with docker-compose
Docker compose builds a cluster och containers where police-service in one of the containers

In order for this to work Docker needs to be installed on the used device

More exact details on how to start docker-composed and running a multi container environment can be found in the project for docker-compose
https://github.com/zokypri/kafka-docker

# Local light standalone start

Start the service alone which will render in errors since Kafka has not started
In this scenario only the endpoint in PoliceNewsController will work and calls to the Police API will work
Kafka will not work and there will be a lot of errors in the log

Use this variant only if you do not want to install kafka and zookeeper 
and if you only want to use the Police API to fetch data and not use Kafka

# Architecture 
This service is part of a news agency architecture. 
Service can be started standalone or running docker multi container with docker compose

# police-service
The service has two controllers and is created to get information from the Swedish police authority
The service publishes a Kafka event when an endpoint is called

    1: Calling the Swedish Police API
    2: Exposiong an endpoint to simulate a callback from the Swedish Police to get alerts the public should be aware of

# flaws

Since the Police API is incorrect and returns false values I need to filter based on type 
The parameter type should return the type of the crime but is WRONG!!!!
It returns all type of crimes even when specified a specific Crime
The same goes for the date.....and this is why I will filter the data myself

# Police api

https://polisen.se/om-polisen/om-webbplatsen/oppna-data/api-over-polisens-handelser/

# List TODO 

    1: Add MDC log id https://dzone.com/articles/correlation-id-for-logging-in-microservices 
    2: Secure the service with JWT tokens
    3: Errorhandling







