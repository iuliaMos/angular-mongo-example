package helsinki.citybike.specifications.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasicColumnFilter {
    private String filterType;
    private String type;
    private String filter;
}
