package groupZ.backend_paniers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendPaniersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPaniersApplication.class, args);
	}

}
