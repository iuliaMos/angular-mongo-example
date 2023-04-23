package helsinki.citybike.specifications.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JourneySearchCriteria {

    private DateColumnFilter departureTime;
    private DateColumnFilter returnTime;

    private BasicColumnFilter departureStationId;
    private BasicColumnFilter departureStationName;

    private BasicColumnFilter returnStationId;
    private BasicColumnFilter returnStationName;

    private BasicColumnFilter distance; // in meters
    private BasicColumnFilter duration; // in seconds
}
