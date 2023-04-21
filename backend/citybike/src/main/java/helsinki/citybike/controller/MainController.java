package helsinki.citybike.controller;

import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.services.JourneyService;
import helsinki.citybike.services.StationService;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MainController {

    private StationService stationService;
    private JourneyService journeyService;

    @GetMapping("/")
    public String greeting() {
        return "Greetings";
    }

    @GetMapping("/stations")
    @ResponseBody
    public List<HSLStation> getStations(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        final StationSearchCriteria searchCriteria) {
        return stationService.getAll(page, size, searchCriteria);
    }

    @GetMapping("/journeys")
    @ResponseBody
    public List<HSLJourney> getJourneys(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        final JourneySearchCriteria searchCriteria) {
        return journeyService.getAll(page, size, searchCriteria);
    }
}
