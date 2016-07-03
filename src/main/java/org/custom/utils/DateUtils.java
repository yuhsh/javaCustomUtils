/**
 * 日期工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.custom.constant.UtilConstants;

public class DateUtils {

    /** 毫秒 */
    private static final BigDecimal MILLS = new BigDecimal("1000");

    /** 秒 */
    private static final BigDecimal SECONDS = new BigDecimal("60");

    /** 分 */
    private static final BigDecimal MINUTES = new BigDecimal("60");

    /** 小时 */
    private static final BigDecimal HOURS = new BigDecimal("24");

    /** 时间算出分母 */
    private static final BigDecimal TIMS_DENOMINATOR = MILLS.multiply(SECONDS).multiply(MINUTES).multiply(HOURS);

    /**
     * 系统时间取得
     * @return 当前的系统时间
     */
    public static Date getNow() {
        Date date = new Date();
        return date;
    }

    /**
     * Date类型格式转换<br>
     * <pre>
     * Date类型向指定的格式转换
     * null的场合，返回空字符串
     * </pre>
     * @param _date 转换对象时间
     * @param _format 转换的格式
     * @return 转换后的时间类型
     * @see UtilConstants#DATE_FORMAT_YYYYMMDDHHMMSS
     * @see UtilConstants#DATE_FORMAT_YYYY_MM_DD_HH_MM_SS
     * @see UtilConstants#DATE_FORMAT_YYYYMMDD
     * @see UtilConstants#DATE_FORMAT_YYYY_MM_DD
     * @see UtilConstants#DATE_FORMAT_YYYYMM
     * @see UtilConstants#DATE_FORMAT_YYYY_MM
     * @see UtilConstants#DATE_FORMAT_MMDD
     * @see UtilConstants#DATE_FORMAT_MM_DD
     * @see UtilConstants#DATE_FORMAT_YYYY
     * @see UtilConstants#DATE_FORMAT_MM
     * @see UtilConstants#DATE_FORMAT_DD
     */
    public static String format(Date _date, String _format) {
        String res = "";
        if (!CheckUtils.isNull(_date) && !CheckUtils.isNullOrEmpty(_format)) {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setLenient(false);
            sdf.applyPattern(_format);
            res = sdf.format(_date);
        }
        return res;
    }

    /**
     * 指定日期前或后指定日数的日期取得
     * @param date 指定日期
     * @param step 指定日数
     * @return 日期
     */
    public static Calendar getCalendar(Date date, int step) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, step);
        return calendar;
    }

    /**
     * 字符串转Date
     * @param _str 被转换字符串
     * @param _format 转换字符串格式
     * @return Date
     * @throws ParseException
     */
    public static Date parse(String _str, String _format) throws ParseException {
        Date dt = null;
        if (!CheckUtils.isNullOrEmpty(_str) && !CheckUtils.isNullOrEmpty(_format)) {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setLenient(false);
            // 转换格式设定
            sdf.applyPattern(_format);
            ParsePosition pos = new ParsePosition(0);
            dt = sdf.parse(_str, pos);
            if (CheckUtils.isNull(dt)) {
                throw new ParseException("字符串不能解析", pos.getErrorIndex());
            } else if (pos.getIndex() != _str.length()) {
                throw new ParseException("字符串不能解析", pos.getIndex());
            }
        }
        return dt;
    }

    /**
     * 日数差取得处理
     * @param _dateStr1 基础日期
     * @param _dateStr2 比较对象日期
     * @param _roundingMode 模式（true：时间差舍去、false：时间差进位）
     * @return 日数差
     * @throws ParseException
     */
    public static int getDifferenceOfDay(String _dateStr1, String _dateStr2, boolean _roundingMode)
            throws ParseException {
        int diff = 0;
        if (!CheckUtils.isNullOrEmpty(_dateStr1) && !CheckUtils.isNullOrEmpty(_dateStr2)) {
            Date date1 = parse(_dateStr1, UtilConstants.DATE_FORMAT_YYYY_MM_DD);
            Date date2 = parse(_dateStr2, UtilConstants.DATE_FORMAT_YYYY_MM_DD);

            diff = getDifferenceOfDay(date1, date2, _roundingMode);
        }
        return diff;
    }

    /**
     * 日数差取得处理
     * @param _date1 基础日期
     * @param _date2 比较对象日期
     * @param _roundingMode 模式（true：时间差舍去、false：时间差进位）
     * @return 日数差
     * @throws ParseException
     */
    public static int getDifferenceOfDay(Date _date1, Date _date2, boolean _roundingMode) throws ParseException {
        int diff = 0;
        if (_date1 != null && _date2 != null) {
            long lnDate1 = _date1.getTime();
            long lnDate2 = _date2.getTime();
            long delta = lnDate2 - lnDate1;

            if (_roundingMode) {
                diff = getDate(delta, RoundingMode.DOWN);
            } else {
                diff = getDate(delta, RoundingMode.UP);
            }
        }
        return diff;
    }

    /**
     * 天数加算处理
     * @param date 基础日期
     * @param amount 天数
     * @return 加算后的日期
     */
    public static Date addDays(Date _date, int _amount) {
        return add(_date, Calendar.DAY_OF_MONTH, _amount);
    }

    /**
     * 日期加算处理
     * @param date 基础日期
     * @param calendarField 加算类型
     * @param amount 加算值
     * @return 加算后的日期
     */
    public static Date add(Date _date, int _calendarField, int _amount) {
        if (_date == null) {
            return _date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(_date);
        c.add(_calendarField, _amount);
        return c.getTime();
    }

    /**
     * 日数差取得
     * @param _time 日数
     * @param _mode 模式
     * @return 算出的日数差
     */
    private static int getDate(long _time, RoundingMode _mode) {
        BigDecimal diff = new BigDecimal(Long.toString(_time));
        int diffDay = diff.divide(TIMS_DENOMINATOR, 0, _mode).intValue();
        return diffDay;
    }

    /**
     * 指定日期前或后指定日数的日期取得
     * @param _date 指定日期
     * @param _format 转换字符串格式
     * @param _step 指定日数
     * @return 日期
     */
    public static String addDate(String _date, String _format, int _step) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(parse(_date, _format));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, _step);

        return format(calendar.getTime(), _format);
    }
}
