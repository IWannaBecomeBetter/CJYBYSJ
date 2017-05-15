package com.bs.common.util;

import com.bs.common.exception.ApplicationException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by fusj on 16/3/10.
 */
public class DateUtil {

    public static final SimpleDateFormat format_yyyyMMddHH24Miss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat format_yyyyMMddHH24Mi=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat format_yyyy_MM_DD=new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format_yyyyMMdd=new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat format_yyyyMM=new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat format_MMdd=new SimpleDateFormat("MMdd");
    public static final SimpleDateFormat format_yyyy=new SimpleDateFormat("yyyy");

    /**
     * 获取当前日期
     * @return
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 获取当前日期后多少分钟
     * @return
     */
    public static Timestamp getTimestampByMinutes(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp addDays(Timestamp date,int days){
        try {
            Calendar c = Calendar.getInstance();
            String sDate = getFormatDate(date, DateUtil.format_yyyy_MM_DD);
            Date dDate = DateUtil.format_yyyy_MM_DD.parse(sDate);
            c.setTime(dDate);   //设置当前日期
            c.add(Calendar.DATE, days); //日期加1
            Date d = c.getTime(); //结果
            return new Timestamp(d.getTime());
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 判断过期时间是否有效
     * @param validTime
     * @return true 有效
     */
    public static Boolean isValid(Timestamp validTime) {
        Timestamp now = getTimestamp();

        // 失效
        if(now.after(validTime)) {
            return false;
        }

        return true;
    }

    /**
     * 获取格式化时间
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static String getFormatDate(Timestamp time, SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(time);
    }

    /**
     * 转换成时间
     * @param time
     * @param simpleDateFormat
     * @return
     */
    public static Timestamp getTimestamp(String time, SimpleDateFormat simpleDateFormat) {
        Timestamp ts = null;
        try {
            ts = new Timestamp(simpleDateFormat.parse(time).getTime());
        } catch (ParseException e) {
            throw new ApplicationException("日期转换错误");
        }
        return ts;
    }

    /**
     * 将日期转化为毫秒
     * @param date		200612241200	,20061224	,2006-12-24,
     * 					2006年12月24日"	,12月24日" ;
     * 					2006-12-24 12:00:59 星期四	,2006年12月24日 12时00分59秒 星期四,
     *
     * @param format	"yyyyMMddhhmm" 	,"yyyyMMdd"	,"yyyy-MM-dd",
     * 					"yyyy年MM月dd日"	,"MM月dd日" ;
     * 					"yyyy-MM-dd HH:mm:ss E"		,"yyyy年MM月dd日 HH时mm分ss秒 E ",
     *
     * @return
     */
    public static long getMillisFromDate(String date ,SimpleDateFormat format ) {

        if (!StringUtil.isNotEmptyObject(date) || "-1".equals(date)) {
            return 0;
        }

        long millionSeconds = -1;
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat(format);
            millionSeconds = format.parse(date).getTime() ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;

    }

    /**
     * 将时间日期转化格式
     * @param time 时间（2013-09-12 12：00 或者任意）
     * @param fromFormat 时间传进的格式（
     * 					"yyyyMMddhhmm" 	,"yyyyMMdd"	,"yyyy-MM-dd",
     * 					"yyyy年MM月dd日"	,"MM月dd日" ;
     * 					"yyyy-MM-dd HH:mm:ss E","yyyy年MM月dd日 HH时mm分ss秒 E "）
     * @param toFormat 时间传出的格式（
     * 					"yyyyMMddhhmm" 	,"yyyyMMdd"	,"yyyy-MM-dd",
     * 					"yyyy年MM月dd日"	,"MM月dd日" ;
     * 					"yyyy-MM-dd HH:mm:ss E"	,"yyyy年MM月dd日 HH时mm分ss秒 E "）
     * @return
     */
    public static String getFormatTime(String time, SimpleDateFormat fromFormat, SimpleDateFormat toFormat){

        if (!StringUtil.isNotEmptyObject(time) || "-1".equals(time)) {
            return "" ;
        }

        long haoMiao = getMillisFromDate(time, fromFormat) ;
        return getCalendarFromMillis(haoMiao, toFormat) ;
    }

    /**
     * 毫秒数 转 日期
     * @param millis		200612241200	,20061224	,2006-12-24,
     * 					2006年12月24日"	,12月24日" ;
     * 					2006-12-24 12:00:59 星期四	,2006年12月24日 12时00分59秒 星期四,
     *
     * @param format	"yyyyMMddhhmm" 	,"yyyyMMdd"	,"yyyy-MM-dd",
     * 					"yyyy年MM月dd日"	,"MM月dd日" ;
     * 					"yyyy-MM-dd HH:mm:ss E"		,"yyyy年MM月dd日 HH时mm分ss秒 E ",
     *
     * @return	yyyy-MM-dd HH:mm:ss
     */
    public static String getCalendarFromMillis(long millis, SimpleDateFormat format){

//        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String riQi = format.format(millis);
        return riQi ;

    }

    public static int getDaysBetween2Date(Timestamp t1,Timestamp t2){

        long mills =Math.abs(t1.getTime()-t2.getTime());
        int days = (int)mills/(24*60*60*1000);
        return days;
    }

    public static void main(String[] args) {
//        Timestamp t1 = DateUtil.getTimestamp("2016-11-22 08:11:11",DateUtil.format_yyyyMMddHH24Miss);
//        Timestamp t2 = DateUtil.getTimestamp();
//        int days = DateUtil.getDaysBetween2Date(t1,t2);
        Timestamp t1 = DateUtil.getTimestamp("2016-11-22 08:11:11", DateUtil.format_yyyy_MM_DD);
        Timestamp t2 = DateUtil.addDays(t1,1);
        String sst2 = DateUtil.getFormatDate(t2, DateUtil.format_yyyy_MM_DD);
        System.out.println(sst2);
    }
}
