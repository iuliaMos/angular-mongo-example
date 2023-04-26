package helsinki.citybike.controller;


import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.services.StationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class StationValidator implements Validator {

    @Autowired
    private StationService stationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return HSLStation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "externalId.required");
        HSLStation station = (HSLStation) target;
        HSLStation existing = stationService.findByExternalId(station.getExternalId());
        if (existing != null && existing.getId() != null) {
            errors.rejectValue("externalId", "uniqueValue", new Object[]{"'externalId'"}, "externalId must be unique");
        }
    }
}
