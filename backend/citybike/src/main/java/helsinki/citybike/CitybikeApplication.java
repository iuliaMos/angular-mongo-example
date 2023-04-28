package helsinki.citybike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class CitybikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitybikeApplication.class, args);
	}

}
