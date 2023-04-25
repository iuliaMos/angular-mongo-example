package helsinki.citybike.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@ToString
public class HSLStation {

    @Id
    private String id;

    private Long nr;
    private String externalId;
    @NotNull
    private String nameFi;
    private String nameSe;
    @NotNull
    private String nameEn;
    @NotNull
    private String addressFi;
    private String addressSe;
    @NotNull
    private String cityFi;
    private String citySe;
    private String operator;
    private Long capacities;
    @NotNull
    private Double x;
    @NotNull
    private Double y;

}
