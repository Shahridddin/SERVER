package uz.pdp.simple_l.utils;

import java.util.Objects;
import java.util.UUID;

public final class StringUtil {
    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getExtension(final String str) {
        Objects.requireNonNull(str, "can not be null");
        return str.substring(str.lastIndexOf('.') + 1);
    }

    public static String generateName(String extension) {
        Objects.requireNonNull(extension, "can not be null");
        return UUID.randomUUID() + "." + extension;
    }

    public static String generateNameFromFileName(String str) {
        return UUID.randomUUID() + str.substring(str.lastIndexOf('.'));
    }

    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }
}
