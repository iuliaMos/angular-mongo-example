package helsinki.citybike.specifications.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationSearchCriteria {

    private BasicColumnFilter nr;
    private BasicColumnFilter externalId;
    private BasicColumnFilter nameFi;
    private BasicColumnFilter nameSe;
    private BasicColumnFilter nameEn;
    private BasicColumnFilter addressFi;
    private BasicColumnFilter addressSe;
    private BasicColumnFilter cityFi;
    private BasicColumnFilter citySe;
    private BasicColumnFilter operator;
    private BasicColumnFilter capacities;
}
