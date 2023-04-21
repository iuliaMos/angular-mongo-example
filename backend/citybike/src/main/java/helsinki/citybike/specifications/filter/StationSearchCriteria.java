package helsinki.citybike.specifications.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationSearchCriteria {

    private Long nr;
    private String externalId;
    private String nameFi;
    private String nameSe;
    private String nameEn;
    private String addressFi;
    private String addressSe;
    private String cityFi;
    private String citySe;
    private String operator;
    private Long capacities;
}
