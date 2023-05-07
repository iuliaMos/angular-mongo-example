package helsinki.citybike.controller;

import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.services.StationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JourneyValidator implements Validator {

    @Autowired
    private StationService stationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JourneyDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JourneyDTO journey = (JourneyDTO) target;
        HSLStation stationDepart = stationService.findByExternalId(journey.getDepartureStationId());
        HSLStation stationRet = stationService.findByExternalId(journey.getReturnStationId());
        if (stationDepart == null || stationDepart.getId() == null) {
            errors.rejectValue("departureStationId", "badid", new Object[]{"'departureStationId'"},"departureStationId must be a valid station externalId");
        }
        if (stationRet == null || stationRet.getId() == null) {
            errors.rejectValue("returnStationId", "badid", new Object[]{"'returnStationId'"},"returnStationId must be a valid station externalId");
        }
    }
}
