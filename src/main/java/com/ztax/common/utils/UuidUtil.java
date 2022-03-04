package com.ztax.common.utils;

import java.util.UUID;

public final class UuidUtil {
    private UuidUtil() {
    }

    public static String get32Uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String get40Uuid() {
        return UUID.randomUUID().toString();
    }
}