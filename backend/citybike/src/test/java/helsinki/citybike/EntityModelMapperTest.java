package helsinki.citybike;

import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.util.EntityModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class EntityModelMapperTest {

    @Test
    public void testToEntityJourney() {
        JourneyDTO dto1 = null;
        JourneyDTO dto = JourneyDTO.builder()
                .departureTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .departureStationId("testid")
                .departureStationName("abc")
                .returnStationId("test1")
                .returnStationName("name")
                .distance(45d)
                .duration(57l).build();

        assertNull(EntityModelMapper.toEntity(dto1));

        HSLJourney entity = EntityModelMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getDepartureTime(), entity.getDepartureTime());
        assertEquals(dto.getReturnTime(), entity.getReturnTime());
        assertEquals(dto.getDepartureStationId(), entity.getDepartureStationId());
        assertEquals(dto.getDepartureStationName(), entity.getDepartureStationName());
        assertEquals(dto.getReturnStationId(), entity.getReturnStationId());
        assertEquals(dto.getReturnStationName(), entity.getReturnStationName());
        assertEquals(dto.getDistance(), entity.getDistance());
        assertEquals(dto.getDuration(), entity.getDuration());
    }

    @Test
    public void testToDTOJourney() {
        HSLJourney entity1 = null;
        HSLJourney entity = HSLJourney.builder()
                .departureTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .departureStationId("testid")
                .departureStationName("abc")
                .returnStationId("test1")
                .returnStationName("name")
                .distance(45d)
                .duration(57l).build();

        assertNull(EntityModelMapper.toDTO(entity1));

        JourneyDTO dto = EntityModelMapper.toDTO(entity);
        assertNotNull(entity);
        assertEquals(entity.getDepartureTime(), dto.getDepartureTime());
        assertEquals(entity.getReturnTime(), dto.getReturnTime());
        assertEquals(entity.getDepartureStationId(), dto.getDepartureStationId());
        assertEquals(entity.getDepartureStationName(), dto.getDepartureStationName());
        assertEquals(entity.getReturnStationId(), dto.getReturnStationId());
        assertEquals(entity.getReturnStationName(), dto.getReturnStationName());
        assertEquals(entity.getDistance(), dto.getDistance());
        assertEquals(entity.getDuration(), dto.getDuration());
    }

    @Test
    public void testToEntityStation() {
        StationDTO dto1 = null;
        StationDTO dto = StationDTO.builder()
                .externalId("005")
                .nameFi("name")
                .nameEn("name")
                .cityFi("City")
                .addressFi("addr")
                .x(7d)
                .y(6d)
                .build();

        assertNull(EntityModelMapper.toEntity(dto1));

        HSLStation entity = EntityModelMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getX(), entity.getLocation().getX());
        assertEquals(dto.getY(), entity.getLocation().getY());
        assertEquals(dto.getCapacities(), entity.getCapacities());
        assertEquals(dto.getNr(), entity.getNr());
        assertEquals(dto.getAddressSe(), entity.getAddressSe());
        assertEquals(dto.getAddressFi(), entity.getAddressFi());
        assertEquals(dto.getCityFi(), entity.getCityFi());
        assertEquals(dto.getOperator(), entity.getOperator());
        assertEquals(dto.getNameFi(), entity.getNameFi());
        assertEquals(dto.getNameEn(), entity.getNameEn());
        assertEquals(dto.getNameSe(), entity.getNameSe());
        assertEquals(dto.getExternalId(), entity.getExternalId());
    }

    @Test
    public void testToDTOStation() {
        HSLStation entity1 = null;
        HSLStation entity = HSLStation.builder()
                .externalId("005")
                .nameFi("name")
                .nameEn("name")
                .cityFi("City")
                .addressFi("addr")
                .location(new GeoJsonPoint(7d, 6d))
                .build();

        assertNull(EntityModelMapper.toDTO(entity1));

        StationDTO dto = EntityModelMapper.toDTO(entity);
        assertNotNull(dto);
        assertEquals(entity.getLocation().getX(), dto.getX());
        assertEquals(entity.getLocation().getY(), dto.getY());
        assertEquals(entity.getCapacities(), dto.getCapacities());
        assertEquals(entity.getNr(), dto.getNr());
        assertEquals(entity.getAddressSe(), dto.getAddressSe());
        assertEquals(entity.getAddressFi(), dto.getAddressFi());
        assertEquals(entity.getCityFi(), dto.getCityFi());
        assertEquals(entity.getOperator(), dto.getOperator());
        assertEquals(entity.getNameFi(), dto.getNameFi());
        assertEquals(entity.getNameEn(), dto.getNameEn());
        assertEquals(entity.getNameSe(), dto.getNameSe());
        assertEquals(entity.getExternalId(), dto.getExternalId());
    }
}
