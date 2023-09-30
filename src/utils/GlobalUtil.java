package utils;

public final class GlobalUtil {
    public static boolean hasEmptyField(String[] params) {
        for (String param : params) {
            if (param == null || param.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
