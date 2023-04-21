package helsinki.citybike.repository;

import helsinki.citybike.entities.HSLJourney;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface HSLJourneyRepository extends MongoRepository<HSLJourney, String>, QuerydslPredicateExecutor<HSLJourney> {
}
