package com.xjdy.common.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.time.temporal.TemporalAdjusters.*;

/**
 * @author ：xusi
 * @date ：Created in 2019-07-03 17:31
 */
public class DateUtil {
    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;


    private DateUtil() {
    }

    /**
     * String 转时间
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }



    /**
     * String 转时间
     *
     * @param timeStr
     * @param format
     *            时间格式
     * @return
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }


    public static LocalDate parseLocalDate(String timeStr, TimeFormat format) {
        return LocalDate.parse(timeStr, format.formatter);
    }


    /**
     * 时间转 String
     *
     * @param time
     * @return
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }


    /**
     * 时间转 String
     *
     * @param time
     * @param format
     *            时间格式
     * @return
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }


    public static String parseLocalDate(LocalDate time, TimeFormat format) {
        return format.formatter.format(time);
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDatetime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }


    /**
     * 获取当前时间
     *
     * @param format
     *            时间格式
     * @return
     */
    public static String getCurrentDatetime(TimeFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }


    /**
     * 时间格式
     */
    public enum TimeFormat {


        /**
         * 短时间格式
         */
        SHORT_DATE_PATTERN_CHINESE("yyyy年MM月dd日"), SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"), SHORT_DATE_PATTERN_SLASH(
                "yyyy/MM/dd"), SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"), SHORT_DATE_PATTERN_NONE("yyyyMMdd"),


        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"), LONG_DATE_PATTERN_SLASH(
                "yyyy/MM/dd HH:mm:ss"), LONG_DATE_PATTERN_DOUBLE_SLASH(
                "yyyy\\MM\\dd HH:mm:ss"), LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),


        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"), LONG_DATE_PATTERN_WITH_MILSEC_SLASH(
                "yyyy/MM/dd HH:mm:ss.SSS"), LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH(
                "yyyy\\MM\\dd HH:mm:ss.SSS"), LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");


        private transient DateTimeFormatter formatter;


        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }


    /**
     * 获取Period（时间段）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Period LocalDateDiff(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        return p;
    }


    /**
     * 获取时间间隔，并格式化为XXXX年XX月XX日
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static String localDateDiffFormat(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        String str = String.format(" %d年 %d月 %d日", p.getYears(), p.getMonths(), p.getDays());
        return str;
    }


    /**
     * 获取Duration（持续时间）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Duration localTimeDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }


    /**
     * 获取时间间隔（毫秒）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long millisDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }


    /**
     * 获取时间间隔（秒）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long secondDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.getSeconds();
    }


    /**
     * 获取时间间隔（天）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long daysDiff(LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }


    /**
     * 创建一个新的日期，它的值为上月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, 1).plus(-1, MONTHS));


    }
    /**
     * 创建一个新的日期，它的值为上月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(-1, MONTHS));


    }
    /**
     * 创建一个新的日期，它的值为当月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(firstDayOfMonth());


    }
    /**
     * 创建一个新的日期，它的值为当月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(lastDayOfMonth());
    }
    /**
     * 创建一个新的日期，它的值为下月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextMonth(LocalDate date) {
        return date.with(firstDayOfNextMonth());


    }
    /**
     * 创建一个新的日期，它的值为下月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(1, MONTHS));


    }

    /**
     * 创建一个新的日期，它的值为上年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, 1).plus(-1, YEARS));
    }
    /**
     * 创建一个新的日期，它的值为上年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum()).plus(-1, YEARS));
    }


    /**
     * 创建一个新的日期，它的值为当年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with(firstDayOfYear());
    }


    /**
     * 创建一个新的日期，它的值为今年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(lastDayOfYear());
    }


    /**
     * 创建一个新的日期，它的值为明年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextYear(LocalDate date) {
        return date.with(firstDayOfNextYear());
    }


    /**
     * 创建一个新的日期，它的值为明年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum()).plus(1, YEARS));
    }


    /**
     * 创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstInMonth(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(firstInMonth(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * 
     * @param date
     * @return
     */
    public static LocalDate getNext(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(next(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * 
     * @param date
     * @return
     */
    public static LocalDate getPrevious(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(previous(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     * 
     * @param date
     * @return
     */
    public static LocalDate getNextOrSame(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(nextOrSame(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     * 
     * @param date
     * @return
     */
    public static LocalDate getPreviousOrSame(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(previousOrSame(dayOfWeek));
    }
}
