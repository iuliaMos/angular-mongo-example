package helsinki.citybike.dto;

import helsinki.citybike.specifications.GridSortModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GridParamsDTO<T> {
    private Integer page;
    private Integer size;
    private T filterModel;
    private List<GridSortModel> sortModel;
}
