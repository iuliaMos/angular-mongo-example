package helsinki.citybike.service;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.dto.AvgDistanceDTO;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.dto.JourneyDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.services.JourneyService;
import helsinki.citybike.specifications.filter.BasicColumnFilter;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JourneyServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private HSLJourneyRepository journeyRepository;

    @InjectMocks
    private JourneyService journeyService;

    @Test
    public void testGetAll() {
        Page<HSLJourney> pageContent = new PageImpl<>(List.of(new HSLJourney()));
        when(journeyRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(pageContent);
        when(journeyRepository.findAll(any(Pageable.class))).thenReturn(pageContent);

        JourneySearchCriteria searchCriteria = new JourneySearchCriteria();
        GridParamsDTO<JourneySearchCriteria> gridParams = new GridParamsDTO<>();
        gridParams.setSize(10);
        gridParams.setPage(0);
        gridParams.setFilterModel(searchCriteria);
        gridParams.setSortModel(new ArrayList<>());

        journeyService.getAll(gridParams);
        verify(journeyRepository, times(1)).findAll(any(Pageable.class));

        BasicColumnFilter filter = new BasicColumnFilter("number", "distance", "5");
        searchCriteria.setDistance(filter);

        journeyService.getAll(gridParams);
        verify(journeyRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));

        verifyNoMoreInteractions(journeyRepository);
    }

    @Test
    public void testGetAllThrowExceptionOnEmptyFilter() {
        GridParamsDTO<JourneySearchCriteria> gridParams = new GridParamsDTO<>();
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.getAll(gridParams));

        gridParams.setSize(10);
        gridParams.setPage(0);
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.getAll(gridParams));

        gridParams.setFilterModel(new JourneySearchCriteria());
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.getAll(gridParams));
    }

    @Test
    public void testTop5Departure() {
        AggregationResults<HSLJourney> result = new AggregationResults<>(List.of(new HSLJourney()), new Document());

        when(mongoTemplate.getCollectionName(HSLJourney.class)).thenReturn("hSLJourney");
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(HSLJourney.class))).thenReturn(result);

        List<JourneyDTO> returnList = journeyService.top5Departure(new HSLStation());

        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(HSLJourney.class));
        verifyNoMoreInteractions(mongoTemplate);

        assertEquals(1, returnList.size());
    }

    @Test
    public void testTop5DepartureInvalidArgument() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.top5Departure(null));
    }

    @Test
    public void testTop5Return() {
        AggregationResults<HSLJourney> result = new AggregationResults<>(List.of(new HSLJourney()), new Document());

        when(mongoTemplate.getCollectionName(HSLJourney.class)).thenReturn("hSLJourney");
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(HSLJourney.class))).thenReturn(result);

        List<JourneyDTO> returnList = journeyService.top5Return(new HSLStation());

        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(HSLJourney.class));
        verifyNoMoreInteractions(mongoTemplate);

        assertEquals(1, returnList.size());
    }

    @Test
    public void testTop5ReturnInvalidArgument() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.top5Return(null));
    }

    @Test
    public void testAvgDeparture() {
        Double returnVal = 3D;
        AggregationResults<AvgDistanceDTO> result = new AggregationResults<>(List.of(new AvgDistanceDTO("342", returnVal)), new Document());
        when(mongoTemplate.getCollectionName(HSLJourney.class)).thenReturn("hSLJourney");
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class))).thenReturn(result);

        Double value = journeyService.avgDeparture(new HSLStation());
        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class));

        assertEquals(returnVal, value);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class))).thenReturn(new AggregationResults<>(new ArrayList<>(), new Document()));

        value = journeyService.avgDeparture(new HSLStation());
        verify(mongoTemplate, times(2)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class));
        verifyNoMoreInteractions(mongoTemplate);

        assertEquals(0D, value);
    }

    @Test
    public void testAvgDepartureInvalidArgument() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.avgDeparture(null));
    }

    @Test
    public void testAvgReturn() {
        Double returnVal = 3D;
        AggregationResults<AvgDistanceDTO> result = new AggregationResults<>(List.of(new AvgDistanceDTO("342", returnVal)), new Document());
        when(mongoTemplate.getCollectionName(HSLJourney.class)).thenReturn("hSLJourney");
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class))).thenReturn(result);

        Double value = journeyService.avgReturn(new HSLStation());
        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class));

        assertEquals(returnVal, value);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class))).thenReturn(new AggregationResults<>(new ArrayList<>(), new Document()));

        value = journeyService.avgReturn(new HSLStation());
        verify(mongoTemplate, times(2)).aggregate(any(Aggregation.class), eq("hSLJourney"), eq(AvgDistanceDTO.class));
        verifyNoMoreInteractions(mongoTemplate);

        assertEquals(0D, value);
    }

    @Test
    public void testAvgReturnInvalidArgument() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> journeyService.avgReturn(null));
    }

    @Test
    public void testSave() {
        when(journeyRepository.save(any(HSLJourney.class))).thenReturn(new HSLJourney());

        journeyService.save(new JourneyDTO());

        verify(journeyRepository, times(1)).save(any(HSLJourney.class));
        verifyNoMoreInteractions(journeyRepository);
    }

    @Test
    public void testCountJourneysByStation() {
        when(journeyRepository.count(any(Predicate.class))).thenReturn(1L);

        List<String> valueList = journeyService.countJourneysByStation("abc");

        verify(journeyRepository, times(2)).count(any(Predicate.class));
        verifyNoMoreInteractions(journeyRepository);

        assertEquals(2, valueList.size());
        assertEquals("Total number of journeys starting from the station: 1", valueList.get(0));
        assertEquals("Total number of journeys ending at the station: 1", valueList.get(1));
    }
}
