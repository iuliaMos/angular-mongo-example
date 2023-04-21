package helsinki.citybike.specifications;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static helsinki.citybike.StringConversionUtils.likeString;

public final class JourneySpecification {

    private JourneySpecification() {
    }

    public static Optional<Predicate> getPredicateFromFilter(final JourneySearchCriteria searchCriteria) {
        PathBuilder<HSLJourney> entityPath = new PathBuilder<>(HSLJourney.class, "HSLJourney");
        List<Predicate> predicateList = new ArrayList<>();

        Optional.ofNullable(searchCriteria.getDepartureStationId()).ifPresent(str -> {
            StringPath stringPath = entityPath.getString("departureStationId");
            predicateList.add(stringPath.like(likeString(str)));
        });
        Optional.ofNullable(searchCriteria.getDepartureStationName()).ifPresent(str -> {
            StringPath stringPath = entityPath.getString("departureStationName");
            predicateList.add(stringPath.like(likeString(str)));
        });
        Optional.ofNullable(searchCriteria.getReturnStationId()).ifPresent(str -> {
            StringPath stringPath = entityPath.getString("returnStationId");
            predicateList.add(stringPath.like(likeString(str)));
        });
        Optional.ofNullable(searchCriteria.getReturnStationName()).ifPresent(str -> {
            StringPath stringPath = entityPath.getString("returnStationName");
            predicateList.add(stringPath.like(likeString(str)));
        });
        Optional.ofNullable(searchCriteria.getDistance()).ifPresent(distance -> {
            NumberPath<Double> path = entityPath.getNumber("distance", Double.TYPE);
            predicateList.add(path.eq(distance));
        });
        Optional.ofNullable(searchCriteria.getDuration()).ifPresent(duration -> {
            NumberPath<Long> path = entityPath.getNumber("duration", Long.TYPE);
            predicateList.add(path.eq(duration));
        });
        Optional.ofNullable(searchCriteria.getDepartureTime()).ifPresent(date -> {
            DateTimePath<LocalDateTime> path = entityPath.getDateTime("departureTime", LocalDateTime.class);
            predicateList.add(path.between(date.atStartOfDay(), date.atTime(LocalTime.MAX)));
        });
        Optional.ofNullable(searchCriteria.getReturnTime()).ifPresent(date -> {
            DateTimePath<LocalDateTime> path = entityPath.getDateTime("returnTime", LocalDateTime.class);
            predicateList.add(path.between(date.atStartOfDay(), date.atTime(LocalTime.MAX)));
        });
        return predicateList.isEmpty() ? Optional.empty() : Optional.of(ExpressionUtils.allOf(predicateList));
    }
}
