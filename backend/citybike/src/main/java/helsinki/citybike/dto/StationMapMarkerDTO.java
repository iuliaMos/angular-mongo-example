package helsinki.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationMapMarkerDTO {
    private Double lon;
    private Double lat;
    private String name;
}
