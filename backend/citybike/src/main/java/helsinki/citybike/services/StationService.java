package helsinki.citybike.services;

import com.querydsl.core.types.Predicate;
import helsinki.citybike.dto.GenericGridDTO;
import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.dto.StationDTO;
import helsinki.citybike.dto.StationMapMarkerDTO;
import helsinki.citybike.entities.HSLStation;
import helsinki.citybike.repository.HSLStationRepository;
import helsinki.citybike.specifications.StationSpecification;
import helsinki.citybike.specifications.filter.StationSearchCriteria;
import helsinki.citybike.util.EntityModelMapper;
import helsinki.citybike.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class StationService {

    private HSLStationRepository stationRepository;

    public GenericGridDTO<StationDTO> getAll(GridParamsDTO<StationSearchCriteria> gridParams) {
        Pageable pageable = ServiceUtil.getPageable(gridParams);

        Optional<Predicate> predicate = StationSpecification.getPredicateFromFilter(gridParams.getFilterModel());
        Page<HSLStation> pageContent = predicate.map(value -> stationRepository.findAll(value, pageable))
                .orElseGet(() -> stationRepository.findAll(pageable));

        return new GenericGridDTO<>(EntityModelMapper.toStationDTOList(pageContent.getContent()),
                pageContent.getTotalElements());
    }

    public List<StationMapMarkerDTO> getStationsGeo() {
        return stationRepository.findAll().stream()
                .map(entity -> new StationMapMarkerDTO(entity.getLocation().getX(), entity.getLocation().getY(), entity.getNameEn()))
                .collect(Collectors.toList());
    }

    public HSLStation findByExternalId(String stationId) {
        return stationRepository.findByExternalId(stationId).orElse(new HSLStation());
    }

    public void save(StationDTO entity) {
        stationRepository.save(EntityModelMapper.toEntity(entity));
    }
}
