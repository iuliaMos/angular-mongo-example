package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.entities.HSLJourney;
import helsinki.citybike.repository.HSLJourneyRepository;
import helsinki.citybike.specifications.JourneySpecification;
import helsinki.citybike.specifications.filter.JourneySearchCriteria;
import helsinki.citybike.util.GenericGridDTO;
import helsinki.citybike.util.GridParamsDTO;
import helsinki.citybike.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class JourneyService {

    private HSLJourneyRepository journeyRepository;

    public GenericGridDTO<HSLJourney> getAll(GridParamsDTO<JourneySearchCriteria> gridParams) {
        Pageable pageable = ServiceUtil.getPageable(gridParams);

        Optional<Predicate> predicate = JourneySpecification.getPredicateFromFilter(gridParams.getFilterModel());
        Page<HSLJourney> pageContent = predicate.map(value -> journeyRepository.findAll(value, pageable))
                .orElseGet(() -> journeyRepository.findAll(pageable));

        return new GenericGridDTO<>(pageContent.getContent(), pageContent.getTotalElements());
    }
}
