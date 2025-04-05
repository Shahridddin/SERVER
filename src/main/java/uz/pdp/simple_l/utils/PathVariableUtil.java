package uz.pdp.simple_l.utils;

public final class PathVariableUtil {
    private PathVariableUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static int parseIntOrDefault(final String value, final int defaultValue) {
        return StringUtil.isBlank(value) ? defaultValue : Integer.parseInt(value);
    }
}

