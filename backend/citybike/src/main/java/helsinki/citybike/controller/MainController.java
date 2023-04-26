package helsinki.citybike.controller;

import helsinki.citybike.dto.GenericGridDTO;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.dto.StationMapMarkerDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.services.JourneyService;
import helsinki.citybike.services.StationService;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class MainController {

    private StationService stationService;
    private JourneyService journeyService;

    @GetMapping("/")
    public String greeting() {
        return "Greetings";
    }

    @PostMapping(value = "/stations", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GenericGridDTO<HSLStation> getStations(@RequestBody GridParamsDTO<StationSearchCriteria> gridParams) {
        log.info("filter: {}, sort: {}", gridParams.getFilterModel(), gridParams.getSortModel());
        return stationService.getAll(gridParams);
    }

    @PostMapping(value = "/journeys", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GenericGridDTO<HSLJourney> getJourneys(@RequestBody GridParamsDTO<JourneySearchCriteria> gridParams) {
        log.info("filter: {}, sort: {}", gridParams.getFilterModel(), gridParams.getSortModel());
        return journeyService.getAll(gridParams);
    }

    @GetMapping("/stationsGeo")
    public List<StationMapMarkerDTO> getStationsGeo() {
        return stationService.getStationsGeo();
    }

    @GetMapping("/top5Depart")
    public List<HSLJourney> getTop5Depart(@RequestParam("stationId") String stationId) {
        return journeyService.top5Departure(stationService.findByExternalId(stationId));
    }

    @GetMapping("/top5Ret")
    public List<HSLJourney> getTop5Ret(@RequestParam("stationId") String stationId) {
       return journeyService.top5Return(stationService.findByExternalId(stationId));
    }

    @GetMapping("/avgDistance")
    public List<String> avgDistance(@RequestParam("stationId") String stationId) {
        String departureAvg = String.valueOf(journeyService.avgDeparture(stationService.findByExternalId(stationId)));
        String returnAvg = String.valueOf(journeyService.avgReturn(stationService.findByExternalId(stationId)));
        return List.of("Departure avg: " + departureAvg + ", Return avg: " + returnAvg);
    }
}
