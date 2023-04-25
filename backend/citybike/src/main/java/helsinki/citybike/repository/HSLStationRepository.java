package helsinki.citybike.repository;

import helsinki.citybike.entities.HSLStation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface HSLStationRepository extends MongoRepository<HSLStation, String>, QuerydslPredicateExecutor<HSLStation> {
    Optional<HSLStation> findByExternalId(String externalId);
}
