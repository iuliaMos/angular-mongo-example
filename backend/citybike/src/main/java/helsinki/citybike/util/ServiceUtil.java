package helsinki.citybike.util;

import helsinki.citybike.dto.GridParamsDTO;
import helsinki.citybike.specifications.GridSortModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

public final class ServiceUtil {

    private ServiceUtil() {
    }

    public static Pageable getPageable(GridParamsDTO<?> gridParams) {
        if (CollectionUtils.isEmpty(gridParams.getSortModel())) {
            return PageRequest.of(gridParams.getPage(), gridParams.getSize());
        }
        GridSortModel sortModel = gridParams.getSortModel().get(0);
        Sort.Direction direction = "asc".equals(sortModel.getSort()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortModel.getColId());
        return PageRequest.of(gridParams.getPage(), gridParams.getSize(), sort);
    }
}
