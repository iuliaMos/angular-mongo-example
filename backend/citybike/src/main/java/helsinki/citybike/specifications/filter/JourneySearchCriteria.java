package helsinki.citybike.specifications.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JourneySearchCriteria {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnTime;

    private String departureStationId;
    private String departureStationName;

    private String returnStationId;
    private String returnStationName;

    private Double distance; // in meters
    private Long duration; // in seconds
}
