package com.lin.missyou.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author ${吴延昭}
 * @create 2020/4/23
 */
public class DateUtils {

    public static Date asDate(LocalDateTime localDateTime) {
        Date from = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return from;
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
