package helsinki.citybike.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JourneyDTO {
    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime returnTime;

    @NotBlank
    private String departureStationId;
    private String departureStationName;
    @NotBlank
    private String returnStationId;
    private String returnStationName;

    @NotNull
    @Min(10)
    private Double distance; // in meters
    @NotNull
    @Min(10)
    private Long duration; // in seconds
}
