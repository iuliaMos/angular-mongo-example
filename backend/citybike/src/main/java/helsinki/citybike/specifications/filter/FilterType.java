package helsinki.citybike.specifications.filter;

public enum FilterType {
    CONTAINS("contains"), EQUALS("equals");
    private final String type;

    FilterType(String type) {
        this.type = type;
    }
}
