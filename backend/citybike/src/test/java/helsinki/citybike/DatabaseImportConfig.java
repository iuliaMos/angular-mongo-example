package helsinki.citybike;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.repository.HSLStationRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@TestConfiguration
public class DatabaseImportConfig {

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRepositoryPopulator(HSLStationRepository stationRepository,
                                                                         HSLJourneyRepository hslJourneyRepository) {
        stationRepository.deleteAll();
        hslJourneyRepository.deleteAll();
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .modules(new GeoJsonModule(), new JavaTimeModule())
                .build();
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data/stations.json"), new ClassPathResource("data/journeys.json")});
        factory.setMapper(mapper);
        return factory;
    }

}
