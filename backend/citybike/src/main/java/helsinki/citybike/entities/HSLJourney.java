package helsinki.citybike.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class HSLJourney {

    @Id
    private String Id;

    @NotNull
    private LocalDateTime departureTime;
    @NotNull
    private LocalDateTime returnTime;

    private String departureStationId;
    @NotNull
    private String departureStationName;

    private String returnStationId;
    @NotNull
    private String returnStationName;

    @NotNull
    private Double distance; // in meters
    @NotNull
    private Long duration; // in seconds
}
