package hu.rekaertsey.client_data_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import hu.rekaertsey.client_data_manager_api.config.AppJwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppJwtProperties.class)
public class ClientDataManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientDataManagerApiApplication.class, args);
	}

}
