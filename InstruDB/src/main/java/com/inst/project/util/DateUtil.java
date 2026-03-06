package com.inst.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public final class DateUtil {

    private DateUtil() {}

    private static final ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>> FORMATTERS =
            new ConcurrentHashMap<>();

    private static SimpleDateFormat formatter(String pattern) {
        ThreadLocal<SimpleDateFormat> tl = FORMATTERS.computeIfAbsent(pattern,
                p -> ThreadLocal.withInitial(() -> new SimpleDateFormat(p)));
        return tl.get();
    }

    // 기본: yyyy-MM-dd HH:mm:ss
    public static String now() {
        return now("yyyy-MM-dd HH:mm:ss");
    }

    // 원하는 패턴
    public static String now(String pattern) {
        return formatter(pattern).format(new Date());
    }

    // 타임존까지 명시하고 싶을 때 (예: Asia/Seoul)
    public static String now(String pattern, String tzId) {
        SimpleDateFormat sdf = formatter(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone(tzId));
        return sdf.format(new Date());
    }

    // Date -> String 변환도 같이
    public static String format(Date date, String pattern) {
        if (date == null) return "";
        return formatter(pattern).format(date);
    }
}