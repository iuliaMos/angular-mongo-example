package helsinki.citybike;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class StringConversionUtils {

    private StringConversionUtils() {
    }

    public static Double getDoubleValueFromField(String field) {
        if (!StringUtils.hasText(field)) {
            return 0D;
        }
        try {
            return Double.parseDouble(field);
        } catch (NumberFormatException e) {
        }
        return 0D;
    }

    public static Long getLongValueFromField(String field) {
        if (!StringUtils.hasText(field)) {
            return 0L;
        }
        try {
            return Long.valueOf(field);
        } catch (NumberFormatException e) {
        }
        return 0L;
    }

    public static LocalDateTime getDateTimeFromField(String field) {
        try {
            return LocalDateTime.parse(field);
        } catch (Exception e) {
        }

        try {
            return LocalDate.parse(field).atTime(0, 0, 0);
        } catch (Exception e) {
        }
        return null;
    }

    public static String likeString(String str) {
        StringBuilder builder = new StringBuilder();
        if (!str.startsWith("%")) {
            builder.append("%");
        }
        builder.append(str);
        if (!str.endsWith("%")) {
            builder.append("%");
        }
        return builder.toString();
    }
}
