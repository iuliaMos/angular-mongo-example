package helsinki.citybike.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenericGridDTO <T>{

    private List<T> records;
    private Long totalRecords;

}
