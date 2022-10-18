# police-service
news from the police api

# flaws

Since the Police API is incorrect and returns false values I need to filter based on type 
The parameter type should return the type of the crime but is WRONG!!!!
It returns all type of crimes even when specified a specific Crime
The same goes for the date.....and this is why I will filter the data myself

#Police api

https://polisen.se/om-polisen/om-webbplatsen/oppna-data/api-over-polisens-handelser/

#List TODO 

    1: Filter by city since Police API is not trustable
    2: Add MDC log id https://dzone.com/articles/correlation-id-for-logging-in-microservices 
    3: Secure the service with JWT tokens
    4: Errorhandling

