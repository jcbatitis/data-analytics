package main.utils;

import java.util.UUID;

public final class UUIDUtil {
    public static String guid() {
        UUID uuid = UUID.randomUUID();
        String rawUUID = uuid.toString().replace("-", "");
        return rawUUID.substring(0, 4);

    }
}