package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.dto.AvgDistanceDTO;
import helsinki.citybike.dto.GenericGridDTO;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.specifications.JourneySpecification;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class JourneyService {

    private HSLJourneyRepository journeyRepository;
    private MongoTemplate mongoTemplate;

    public GenericGridDTO<HSLJourney> getAll(GridParamsDTO<JourneySearchCriteria> gridParams) {
        Pageable pageable = ServiceUtil.getPageable(gridParams);

        Optional<Predicate> predicate = JourneySpecification.getPredicateFromFilter(gridParams.getFilterModel());
        Page<HSLJourney> pageContent = predicate.map(value -> journeyRepository.findAll(value, pageable))
                .orElseGet(() -> journeyRepository.findAll(pageable));

        return new GenericGridDTO<>(pageContent.getContent(), pageContent.getTotalElements());
    }

    public List<HSLJourney> top5Departure(final HSLStation station) {
        GroupOperation countReturns = group("departureStationId", "returnStationId").count().as("departCount")
                .first("$$ROOT").as("objId");
        SortOperation sortByCount = sort(Sort.Direction.DESC, "departCount");
        MatchOperation filter = match(Criteria.where("_id.departureStationId").is(station.getExternalId()));
        LimitOperation limit = limit(5);
        ReplaceRootOperation replaceRootOperation = replaceRoot("$objId");

        Aggregation aggregation = newAggregation(countReturns, filter, sortByCount, limit, replaceRootOperation);

        AggregationResults<HSLJourney> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(HSLJourney.class), HSLJourney.class);
        return result.getMappedResults();
    }

    public List<HSLJourney> top5Return(final HSLStation station) {
        GroupOperation countReturns = group("returnStationId", "departureStationId").count().as("returnCount")
                .first("$$ROOT").as("objId");
        SortOperation sortByCount = sort(Sort.Direction.DESC, "returnCount");
        MatchOperation filter = match(Criteria.where("_id.returnStationId").is(station.getExternalId()));
        LimitOperation limit = limit(5);
        ReplaceRootOperation replaceRootOperation = replaceRoot("$objId");

        Aggregation aggregation = newAggregation(countReturns, filter, sortByCount, limit, replaceRootOperation);

        AggregationResults<HSLJourney> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(HSLJourney.class), HSLJourney.class);
        return result.getMappedResults();
    }

    public Double avgDeparture(final HSLStation station) {
        MatchOperation filter = match(Criteria.where("departureStationId").is(station.getExternalId()));
        GroupOperation avgDistance = group("departureStationId").avg("$distance").as("avgDistance");
        Aggregation aggregation = newAggregation(filter, avgDistance);

        AggregationResults<AvgDistanceDTO> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(HSLJourney.class), AvgDistanceDTO.class);
        AvgDistanceDTO avg = result.getUniqueMappedResult();
        return Optional.ofNullable(avg).map(AvgDistanceDTO::getAvgDistance).orElse(0d);
    }

    public Double avgReturn(final HSLStation station) {
        MatchOperation filter = match(Criteria.where("returnStationId").is(station.getExternalId()));
        GroupOperation avgDistance = group("returnStationId").avg("$distance").as("avgDistance");
        Aggregation aggregation = newAggregation(filter, avgDistance);

        AggregationResults<AvgDistanceDTO> result = mongoTemplate
                .aggregate(aggregation, mongoTemplate.getCollectionName(HSLJourney.class), AvgDistanceDTO.class);
        AvgDistanceDTO avg = result.getUniqueMappedResult();
        return Optional.ofNullable(avg).map(AvgDistanceDTO::getAvgDistance).orElse(0d);
    }

    public void save(HSLJourney entity) {
        journeyRepository.save(entity);
    }
}
