package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLStationRepository;
import helsinki.citybike.specifications.StationSpecification;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
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
public class StationService {

    private HSLStationRepository stationRepository;

    public GenericGridDTO<HSLStation> getAll(GridParamsDTO<StationSearchCriteria> gridParams) {
        Pageable pageable = ServiceUtil.getPageable(gridParams);

        Optional<Predicate> predicate = StationSpecification.getPredicateFromFilter(gridParams.getFilterModel());
        Page<HSLStation> pageContent = predicate.map(value -> stationRepository.findAll(value, pageable))
                .orElseGet(() -> stationRepository.findAll(pageable));

        return new GenericGridDTO<>(pageContent.getContent(), pageContent.getTotalElements());
    }
}
