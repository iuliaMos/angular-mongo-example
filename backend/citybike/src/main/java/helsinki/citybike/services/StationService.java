package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLStationRepository;
import helsinki.citybike.specifications.StationSpecification;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
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
public class StationService {

    private HSLStationRepository stationRepository;

    public List<HSLStation> getAll(int page, int size, final StationSearchCriteria searchCriteria) {
        Pageable pageable = PageRequest.of(page, size);

        Optional<Predicate> predicate = StationSpecification.getPredicateFromFilter(searchCriteria);
        Page<HSLStation> pageContent = predicate.map(value -> stationRepository.findAll(value, pageable))
                .orElseGet(() -> stationRepository.findAll(pageable));

        log.info("nr pages: {}", pageContent.getTotalPages());
        return pageContent.getContent();
    }
}
