package helsinki.citybike.util;

import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class EntityModelMapper {

    private EntityModelMapper() {
    }

    public static HSLJourney toEntity(JourneyDTO dto) {
        if (dto == null) {
            return null;
        }
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
        if (entity == null) {
            return null;
        }
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
        if (dto == null || dto.getX() == null || dto.getY() == null) {
            return null;
        }
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
                .location(new GeoJsonPoint(dto.getX(), dto.getY()))
                .build();
    }

    public static StationDTO toDTO(HSLStation entity) {
        if (entity == null) {
            return null;
        }
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
                .x(entity.getLocation().getX())
                .y(entity.getLocation().getY())
                .build();
    }

    public static List<StationDTO> toStationDTOList(List<HSLStation> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(EntityModelMapper::toDTO).collect(Collectors.toList());
    }

    public static List<JourneyDTO> toJourneyDTOList(List<HSLJourney> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(EntityModelMapper::toDTO).collect(Collectors.toList());
    }
}
