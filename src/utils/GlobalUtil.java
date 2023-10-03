package utils;

public final class GlobalUtil {
    public static boolean hasEmptyField(Object[] params) {
        for (Object param : params) {
            if (param == null) {
                return true;
            }
            if (param instanceof String && ((String) param).trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
