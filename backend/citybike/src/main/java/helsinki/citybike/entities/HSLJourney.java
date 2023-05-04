package helsinki.citybike.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@CompoundIndexes({
        @CompoundIndex(name = "departureStationId", def = "{'departureStationId' : 1}"),
        @CompoundIndex(name = "returnStationId", def = "{'returnStationId' : 1}")
})
@ToString
public class HSLJourney {

    @Id
    private String id;

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
