package helsinki.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvgDistanceDTO {

    private String Id;

    private Double avgDistance;
}
