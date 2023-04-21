package helsinki.citybike.repository;

import helsinki.citybike.entities.HSLStation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HSLStationRepository extends MongoRepository<HSLStation, String>, QuerydslPredicateExecutor<HSLStation> {
}
