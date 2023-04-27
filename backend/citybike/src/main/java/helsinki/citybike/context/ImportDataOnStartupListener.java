package helsinki.citybike.context;

import com.opencsv.CSVReader;
import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.services.JourneyService;
import helsinki.citybike.services.StationService;
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

import static helsinki.citybike.util.StringConversionUtils.*;

@Component
@Data
@Slf4j
public class ImportDataOnStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private StationService stationService;
    @Autowired
    private JourneyService journeyService;
    @Value("${app.journey.download.path}")
    private String journeyDownloadPath;
    @Value("${app.journey.urls}")
    private String[] journeyUrls;

    @Value("${app.journey.filesnames}")
    private String[] journeyFilesNames;


    //No of imported journeys in 3 files: 3128758, each file contains around 1000000
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            File dir = new File(journeyDownloadPath);
            if (dir.exists()) {
                FileUtils.deleteDirectory(dir);
            }
            dir.mkdir();
            downloadJourneys();
            if (importStations()) {
                importJourneys();
            } else {
                log.info("End imported journeys: 0");
            }
        } catch (IOException e) {
            log.error("Error on importing data", e);
        }
    }

    private void downloadJourneys() throws IOException {
        if (journeyUrls == null) {
            log.info("No URL defined for importing journey");
            return;
        }
        for (String url : journeyUrls) {
            String name = url.substring(url.lastIndexOf("/") + 1);
            File file = ResourceUtils.getFile(journeyDownloadPath + name);
            FileUtils.copyURLToFile(new URL(url), file, 10000, 10000);
        }
    }

    private boolean importStations() throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(ResourceUtils.getFile(journeyDownloadPath + "726277c507ef4914b0aec3cbcfcbfafc_0.csv")))) {
            String[] line;
            csvReader.readNext(); // read header
            while ((line = csvReader.readNext()) != null) {
                try {
                    stationService.save(createNewStationEntity(line));
                } catch (Exception e) {
                    return false;
                }
            }
        }
        log.info("End imported stations");
        return true;
    }

    private StationDTO createNewStationEntity(String[] line) {
        return StationDTO.builder()
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
        if (journeyUrls == null) {
            log.info("End imported journeys: 0");
            return;
        }
        for (String file : journeyFilesNames) {
            try (CSVReader csvReader = new CSVReader(new FileReader(ResourceUtils.getFile(journeyDownloadPath + file)))) {
                String[] line;
                csvReader.readNext(); // read header
                while ((line = csvReader.readNext()) != null) {
                    if (validateMinDistanceAndDuration(line)) {
                        continue;
                    }
                    journeyService.save(createNewJourneyEntity(line));
                }
            }
        }

        log.info("End imported journeys");
    }

    private boolean validateMinDistanceAndDuration(String[] line) {
        if (getDoubleValueFromField(line[6]).compareTo(10D) < 0) {
            return true;
        }
        return getLongValueFromField(line[7]).compareTo(10L) < 0;
    }

    private JourneyDTO createNewJourneyEntity(String[] line) {
        return JourneyDTO.builder()
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
