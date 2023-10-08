package utils;

public final class ContentUtil {
    public static String format(String value) {
       if (value == null) {
        return "";
    }
    
    if (value.contains(",") || value.contains("\n") || value.contains("\"")) {
        value = value.replace("\"", "\"\"");
        return "\"" + value + "\"";
    } else {
        return value;
    }
    }
}
