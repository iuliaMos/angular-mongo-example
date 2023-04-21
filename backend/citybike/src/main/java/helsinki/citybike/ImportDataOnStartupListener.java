package helsinki.citybike;

import com.opencsv.CSVReader;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.repository.HSLStationRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static helsinki.citybike.StringConversionUtils.*;

@Component
@Data
@Slf4j
public class ImportDataOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private HSLStationRepository hslStationRepository;
    @Autowired
    private HSLJourneyRepository hslJourneyRepository;
    @Value("${app.journey.download.path}")
    private String journeyDownloadPath;
    @Value("${app.journey.urls}")
    private String[] journeyUrls;


    //No of imported journeys: 3128758
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            downloadJourneys();
            importStations();
            importJourneys();
        } catch (IOException e) {
            log.error("Error on importing data", e);
        }
    }

    private void downloadJourneys() throws IOException {
        for (String url : journeyUrls) {
            String name = url.substring(url.lastIndexOf("/") + 1);
            File file = ResourceUtils.getFile(journeyDownloadPath + name);
            FileUtils.copyURLToFile(new URL(url), file, 10000, 10000);
        }
    }

    private void importStations() throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(ResourceUtils.getFile(journeyDownloadPath + "Helsingin_ja_Espoon_kaupunkipyB6rA4asemat_avoin.csv")))) {
            String[] line;
            csvReader.readNext(); // read header
            while ((line = csvReader.readNext()) != null) {
                hslStationRepository.save(createNewStationEntity(line));
            }
        }
        log.info("No of imported stations: {}", hslStationRepository.count());
    }

    private HSLStation createNewStationEntity(String[] line) {
        return HSLStation.builder()
                .nr(Long.valueOf(line[0]))
                .externalId(line[1])
                .nameFi(line[2])
                .nameSe(line[3])
                .nameEn(line[4])
                .addressFi(line[5])
                .addressSe(line[6])
                .cityFi(line[7])
                .citySe(line[8])
                .operator(line[9])
                .capacities(Optional.ofNullable(line[10]).map(Long::valueOf).orElse(0L))
                .x(Double.parseDouble(line[11]))
                .y(Double.parseDouble(line[12]))
                .build();
    }

    private void importJourneys() throws IOException {
        String[] files = {"2021-04.csv"};//{"2021-05.csv", "2021-06.csv", "2021-07.csv"};
        for (String file : files) {
            try (CSVReader csvReader = new CSVReader(new FileReader(ResourceUtils.getFile(journeyDownloadPath + file)))) {
                String[] line;
                csvReader.readNext(); // read header
                while ((line = csvReader.readNext()) != null) {
                    if (validateMinDistanceAndDuration(line)) {
                        continue;
                    }
                    hslJourneyRepository.save(createNewJourneyEntity(line));
                }
            }
        }

        log.info("No of imported journeys: {}", hslJourneyRepository.count());
    }

    private boolean validateMinDistanceAndDuration(String[] line) {
        if (getDoubleValueFromField(line[6]).compareTo(10D) < 0) {
            return true;
        }
        return getLongValueFromField(line[7]).compareTo(10L) < 0;
    }

    private HSLJourney createNewJourneyEntity(String[] line) {
        return HSLJourney.builder()
                .departureTime(getDateTimeFromField(line[0]))
                .returnTime(getDateTimeFromField(line[1]))
                .departureStationId(line[2])
                .departureStationName(line[3])
                .returnStationId(line[4])
                .returnStationName(line[5])
                .distance(getDoubleValueFromField(line[6]))
                .duration(getLongValueFromField(line[7]))
                .build();
    }
}
