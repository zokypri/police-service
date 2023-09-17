package se.implementer.policeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoliceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliceServiceApplication.class, args);

		String swaggerUrl = "/police-service/swagger-ui/index.html#/";
		System.out.println("Swagger URL: http://localhost:8089" + swaggerUrl);

	}

}
