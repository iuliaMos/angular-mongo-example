package helsinki.citybike.util;

import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;

import java.util.List;
import java.util.stream.Collectors;

public class EntityModelMapper {

    private EntityModelMapper() {
    }

    public static HSLJourney toEntity(JourneyDTO dto) {
        return HSLJourney.builder()
                .departureTime(dto.getDepartureTime())
                .returnTime(dto.getReturnTime())
                .departureStationId(dto.getDepartureStationId())
                .departureStationName(dto.getDepartureStationName())
                .returnStationId(dto.getReturnStationId())
                .returnStationName(dto.getReturnStationName())
                .distance(dto.getDistance())
                .duration(dto.getDuration())
                .build();
    }

    public static JourneyDTO toDTO(HSLJourney entity) {
        return JourneyDTO.builder()
                .departureTime(entity.getDepartureTime())
                .returnTime(entity.getReturnTime())
                .departureStationId(entity.getDepartureStationId())
                .departureStationName(entity.getDepartureStationName())
                .returnStationId(entity.getReturnStationId())
                .returnStationName(entity.getReturnStationName())
                .distance(entity.getDistance())
                .duration(entity.getDuration())
                .build();
    }

    public static HSLStation toEntity(StationDTO dto) {
        return HSLStation.builder()
                .nr(dto.getNr())
                .externalId(dto.getExternalId())
                .nameFi(dto.getNameFi())
                .nameSe(dto.getNameSe())
                .nameEn(dto.getNameEn())
                .addressFi(dto.getAddressFi())
                .addressSe(dto.getAddressSe())
                .cityFi(dto.getCityFi())
                .citySe(dto.getCitySe())
                .operator(dto.getOperator())
                .capacities(dto.getCapacities())
                .x(dto.getX())
                .y(dto.getY())
                .build();
    }

    public static StationDTO toDTO(HSLStation entity) {
        return StationDTO.builder()
                .nr(entity.getNr())
                .externalId(entity.getExternalId())
                .nameFi(entity.getNameFi())
                .nameSe(entity.getNameSe())
                .nameEn(entity.getNameEn())
                .addressFi(entity.getAddressFi())
                .addressSe(entity.getAddressSe())
                .cityFi(entity.getCityFi())
                .citySe(entity.getCitySe())
                .operator(entity.getOperator())
                .capacities(entity.getCapacities())
                .x(entity.getX())
                .y(entity.getY())
                .build();
    }

    public static List<StationDTO> toStationDTOList(List<HSLStation> list) {
        return list.stream().map(EntityModelMapper::toDTO).collect(Collectors.toList());
    }
    public static List<JourneyDTO> toJourneyDTOList(List<HSLJourney> list) {
        return list.stream().map(EntityModelMapper::toDTO).collect(Collectors.toList());
    }
    public static List<HSLStation> toStationEntityList(List<StationDTO> list) {
        return list.stream().map(EntityModelMapper::toEntity).collect(Collectors.toList());
    }
    public static List<HSLJourney> toJourneyEntityList(List<JourneyDTO> list) {
        return list.stream().map(EntityModelMapper::toEntity).collect(Collectors.toList());
    }
}
