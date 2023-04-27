package helsinki.citybike.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StationDTO {

    private Long nr;
    @NotBlank
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
