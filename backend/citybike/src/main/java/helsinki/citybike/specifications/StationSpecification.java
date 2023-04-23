package helsinki.citybike.specifications;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.specifications.filter.StationSearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static helsinki.citybike.util.StringConversionUtils.likeString;

public final class StationSpecification {

    private StationSpecification() {
    }

    public static Optional<Predicate> getPredicateFromFilter(final StationSearchCriteria searchCriteria) {
        PathBuilder<HSLStation> entityPath = new PathBuilder<>(HSLStation.class, "HSLStation");
        List<Predicate> predicateList = new ArrayList<>();

        Optional.ofNullable(searchCriteria.getNr()).ifPresent(nr -> {
            NumberPath<Long> path = entityPath.getNumber("nr", Long.TYPE);
            predicateList.add(path.eq(Long.parseLong(nr.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getExternalId()).ifPresent(externalId -> {
            StringPath stringPath = entityPath.getString("externalId");
            predicateList.add(stringPath.likeIgnoreCase(likeString(externalId.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getNameEn()).ifPresent(name -> {
            StringPath stringPath = entityPath.getString("nameEn");
            predicateList.add(stringPath.likeIgnoreCase(likeString(name.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getNameFi()).ifPresent(name -> {
            StringPath stringPath = entityPath.getString("nameFi");
            predicateList.add(stringPath.likeIgnoreCase(likeString(name.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getNameSe()).ifPresent(name -> {
            StringPath stringPath = entityPath.getString("nameSe");
            predicateList.add(stringPath.likeIgnoreCase(likeString(name.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getAddressFi()).ifPresent(address -> {
            StringPath stringPath = entityPath.getString("addressFi");
            predicateList.add(stringPath.likeIgnoreCase(likeString(address.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getAddressSe()).ifPresent(address -> {
            StringPath stringPath = entityPath.getString("addressSe");
            predicateList.add(stringPath.likeIgnoreCase(likeString(address.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getCityFi()).ifPresent(city -> {
            StringPath stringPath = entityPath.getString("cityFi");
            predicateList.add(stringPath.likeIgnoreCase(likeString(city.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getCitySe()).ifPresent(city -> {
            StringPath stringPath = entityPath.getString("citySe");
            predicateList.add(stringPath.likeIgnoreCase(likeString(city.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getOperator()).ifPresent(operator -> {
            StringPath stringPath = entityPath.getString("operator");
            predicateList.add(stringPath.likeIgnoreCase(likeString(operator.getFilter())));
        });
        Optional.ofNullable(searchCriteria.getCapacities()).ifPresent(capacities -> {
            NumberPath<Long> path = entityPath.getNumber("capacities", Long.TYPE);
            predicateList.add(path.eq(Long.parseLong(capacities.getFilter())));
        });

        return predicateList.isEmpty() ? Optional.empty() : Optional.of(ExpressionUtils.allOf(predicateList));
    }
}
