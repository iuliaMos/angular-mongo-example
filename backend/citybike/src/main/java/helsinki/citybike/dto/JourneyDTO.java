package helsinki.citybike.dto;

import lombok.*;

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
    private Double distance; // in meters
    @NotNull
    private Long duration; // in seconds
}
