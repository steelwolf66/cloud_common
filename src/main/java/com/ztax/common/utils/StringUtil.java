package com.ztax.common.utils;

public final class StringUtil {
    private StringUtil() {
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty() || "null".equals(s);
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String join(String... lines) {
        if (lines.length == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            String[] var2 = lines;
            int var3 = lines.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String line = var2[var4];
                sb.append(line);
            }

            return sb.toString();
        }
    }
}