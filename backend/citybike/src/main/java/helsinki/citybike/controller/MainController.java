package helsinki.citybike.controller;

import helsinki.citybike.dto.*;
import helsinki.citybike.services.JourneyService;
import helsinki.citybike.services.StationService;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Api(description = "Rest controller used by frontend application")
public class MainController {

    private StationService stationService;
    private JourneyService journeyService;
    private StationValidator validator;

    @ApiOperation(value = "Returns stations filtered and paginated")
    @PostMapping(value = "/stations", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GenericGridDTO<StationDTO> getStations(@RequestBody GridParamsDTO<StationSearchCriteria> gridParams) {
        log.info("filter: {}, sort: {}", gridParams.getFilterModel(), gridParams.getSortModel());
        return stationService.getAll(gridParams);
    }

    @ApiOperation(value = "Returns journeys filtered and paginated")
    @PostMapping(value = "/journeys", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GenericGridDTO<JourneyDTO> getJourneys(@RequestBody GridParamsDTO<JourneySearchCriteria> gridParams) {
        log.info("filter: {}, sort: {}", gridParams.getFilterModel(), gridParams.getSortModel());
        return journeyService.getAll(gridParams);
    }

    @ApiOperation(value = "Returns coordinates of all stations")
    @GetMapping("/stationsGeo")
    public List<StationMapMarkerDTO> getStationsGeo() {
        return stationService.getStationsGeo();
    }

    @ApiOperation(value = "Returns top 5 journeys for station as departure")
    @GetMapping("/top5Depart")
    public List<JourneyDTO> getTop5Depart(@RequestParam("stationId") String stationId) {
        return journeyService.top5Departure(stationService.findByExternalId(stationId));
    }

    @ApiOperation(value = "Returns top 5 journeys for station as return")
    @GetMapping("/top5Ret")
    public List<JourneyDTO> getTop5Ret(@RequestParam("stationId") String stationId) {
        return journeyService.top5Return(stationService.findByExternalId(stationId));
    }

    @ApiOperation(value = "Returns average distance and count of journeys for a station")
    @GetMapping("/avgDistance")
    public List<String> avgDistance(@RequestParam("stationId") String stationId) {
        List<String> returnList = new ArrayList<>();
        String departureAvg = String.format("%.2f", journeyService.avgDeparture(stationService.findByExternalId(stationId)));
        String returnAvg = String.format("%.2f", journeyService.avgReturn(stationService.findByExternalId(stationId)));
        String avg1 = String.format("The average distance of a journey starting from the station: %s", departureAvg);
        String avg2 = String.format("The average distance of a journey ending at the station: %s", returnAvg);
        returnList.add(avg1);
        returnList.add(avg2);
        returnList.addAll(journeyService.countJourneysByStation(stationId));
        return returnList;
    }

    @ApiOperation(value = "Save station")
    @PostMapping(value = "/savestation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveStation(@Valid @RequestBody StationDTO station, BindingResult bindingResult) {
        validator.validate(station, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("{}", bindingResult.getAllErrors());
            return;
        }
        stationService.save(station);
    }

    @ApiOperation(value = "Save journey")
    @PostMapping(value = "/savejourney", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveJourney(@Valid @RequestBody JourneyDTO journey) {
        journeyService.save(journey);
    }
}
