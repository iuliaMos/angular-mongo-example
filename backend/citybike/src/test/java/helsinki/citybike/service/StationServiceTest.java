package helsinki.citybike.service;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.dto.StationMapMarkerDTO;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLStationRepository;
import helsinki.citybike.services.StationService;
import helsinki.citybike.specifications.filter.BasicColumnFilter;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {
    @Mock
    private HSLStationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    @Test
    public void testGetAll() {
        HSLStation entity = HSLStation.builder().location(new GeoJsonPoint(6d, 7d)).build();
        Page<HSLStation> pageContent = new PageImpl<>(List.of(entity));
        when(stationRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(pageContent);
        when(stationRepository.findAll(any(Pageable.class))).thenReturn(pageContent);

        StationSearchCriteria searchCriteria = new StationSearchCriteria();
        GridParamsDTO<StationSearchCriteria> gridParams = new GridParamsDTO<>();
        gridParams.setSize(10);
        gridParams.setPage(0);
        gridParams.setFilterModel(searchCriteria);
        gridParams.setSortModel(new ArrayList<>());

        stationService.getAll(gridParams);
        verify(stationRepository, times(1)).findAll(any(Pageable.class));

        BasicColumnFilter filter = new BasicColumnFilter("number", "nr", "5");
        searchCriteria.setNr(filter);

        stationService.getAll(gridParams);
        verify(stationRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));

        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    public void testGetAllThrowExceptionOnEmptyFilter() {
        GridParamsDTO<StationSearchCriteria> gridParams = new GridParamsDTO<>();
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> stationService.getAll(gridParams));

        gridParams.setSize(10);
        gridParams.setPage(0);
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> stationService.getAll(gridParams));

        gridParams.setFilterModel(new StationSearchCriteria());
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> stationService.getAll(gridParams));
    }

    @Test
    public void testSave() {
        when(stationRepository.save(any(HSLStation.class))).thenReturn(new HSLStation());

        StationDTO dto = StationDTO.builder()
                .x(6d)
                .y(7d).build();
        stationService.save(dto);

        verify(stationRepository, times(1)).save(any(HSLStation.class));
        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    public void testFindByExternalId() {
        when(stationRepository.findByExternalId(anyString())).thenReturn(Optional.of(new HSLStation()));

        HSLStation obj = stationService.findByExternalId("abc1");
        verify(stationRepository, times(1)).findByExternalId("abc1");
        assertNotNull(obj);

        when(stationRepository.findByExternalId(anyString())).thenReturn(Optional.empty());

        obj = stationService.findByExternalId("abc2");
        verify(stationRepository, times(1)).findByExternalId("abc2");
        assertNotNull(obj);

        verifyNoMoreInteractions(stationRepository);
    }

    @Test
    public void testGetStationsGeo() {
        HSLStation entity = HSLStation.builder().location(new GeoJsonPoint(6d, 7d)).build();

        when(stationRepository.findAll()).thenReturn(List.of(entity));

        List<StationMapMarkerDTO> returnList = stationService.getStationsGeo();
        verify(stationRepository, times(1)).findAll();
        assertEquals(1, returnList.size());

        when(stationRepository.findAll()).thenReturn(new ArrayList<>());

        returnList = stationService.getStationsGeo();
        verify(stationRepository, times(2)).findAll();
        assertEquals(0, returnList.size());

        verifyNoMoreInteractions(stationRepository);
    }
}
