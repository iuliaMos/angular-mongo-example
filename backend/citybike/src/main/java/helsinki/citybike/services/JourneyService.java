package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.specifications.JourneySpecification;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class JourneyService {

    private HSLJourneyRepository journeyRepository;

    public List<HSLJourney> getAll(int page, int size, final JourneySearchCriteria searchCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Optional<Predicate> predicate = JourneySpecification.getPredicateFromFilter(searchCriteria);
        Page<HSLJourney> pageContent = predicate.map(value -> journeyRepository.findAll(value, pageable))
                .orElseGet(() -> journeyRepository.findAll(pageable));

        return pageContent.getContent();
    }
}
