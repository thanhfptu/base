package vn.edu.fpt.capstone.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final String TIMEZONE = "Asia/Ho_Chi_Minh";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATETIME_FORMAT_CUSTOM = "yyyyMMddHHmmss";

    public static Date now() {
        return new Date();
    }

    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setLenient(false);
        return formatter.format(date);
    }

    public static Date parse(String date, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setLenient(false);
            return formatter.parse(date);
        } catch (ParseException e) {
            LOGGER.error("Can not parse date, details: {}", e.getMessage());
            return null;
        }
    }

    public static LocalDate toLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date of(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date of(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date at(LocalDate localDate, LocalTime time) {
        return Date.from(localDate.atTime(time).atZone(ZoneId.systemDefault()).toInstant());
    }

}
