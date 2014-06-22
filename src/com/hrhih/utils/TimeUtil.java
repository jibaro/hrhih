package com.hrhih.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TimeUtil {
	private static SimpleDateFormat format0 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHH");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");

	
	/**
	 * 格式化日期。
	 * 
	 * @param time
	 *            毫秒级的时间
	 * @param format
	 *            时间格式
	 * @return
	 */
	public static String formatDate(long time, String format) {
		SimpleDateFormat format3 = new SimpleDateFormat(format);
		Date date = new Date(time);
		String ymd = format3.format(date);
		return ymd;
	}
	
	/**
	 * 把可读的日期，转成Long类型
	 * @param date
	 * @param format
	 * @return 时间的毫秒级
	 */
	public static long formatTimeMillis(String date, String format) {
		SimpleDateFormat format3 = new SimpleDateFormat(format);
		long time=0L;
		try {
			time = format3.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * 获取当前时间的前30分钟，间隔为30分钟。
	 * 
	 * @return
	 */
	public static String[] getStartRow_StopRow() {

		String startrow = null;
		String stoprow = null;

		Calendar cal = Calendar.getInstance();
		int minute = cal.get(Calendar.MINUTE);

		if (minute >= 30) {
			String timestr = format1.format(cal.getTime());
			startrow = timestr + "0000";
			stoprow = timestr + "3000";
		} else {
			cal.add(Calendar.MINUTE, -30);
			String timestr = format1.format(cal.getTime());
			startrow = timestr + "3000";

			cal.add(Calendar.MINUTE, 30);
			String timestr2 = format1.format(cal.getTime());

			stoprow = timestr2 + "0000";
		}

		Date startdate = null;
		Date stopdate = null;
		try {
			startdate = (Date) format2.parse(startrow);
			stopdate = (Date) format2.parse(stoprow);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] start_stop = { String.valueOf(startdate.getTime() / 1000),
				String.valueOf(stopdate.getTime() / 1000) };

		return start_stop;
	}

	/**
	 * 获取当前时间前interval分钟的间隔。
	 * @param interval 间隔的分钟数。
	 * @return
	 */
	public static long[] getStartRow_StopRow(int interval) {
		
		long startrow = 0L;
		long stoprow = 0L;
		
		Calendar cal = Calendar.getInstance();
		cal.get(Calendar.MINUTE);
		
		int second = cal.get(Calendar.SECOND);
		
		cal.add(Calendar.SECOND, -second);
		
		int minute = cal.get(Calendar.MINUTE);
		
		int minute0=minute%interval;
		
		cal.add(Calendar.MINUTE, -minute0);
		
		cal.add(Calendar.MINUTE, -interval);
		stoprow=cal.getTimeInMillis()/1000;
		
		cal.add(Calendar.MINUTE, -interval);
		startrow=cal.getTimeInMillis()/1000;
		
		long[] start_stop = { startrow,stoprow };
		
		return start_stop;
	}
	
	/**
	 * 间隔1天。
	 * 
	 * @param date
	 * @return 经过格式化的日期，如：20121101000000
	 */
	public static String[] interval1day(String date) {
		String stopdate=null;
		try {
			Calendar cal = format0.getCalendar();
			cal.setTime(format0.parse(date));
			cal.add(Calendar.DATE, 1);
			stopdate = format0.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String[] interval1day = { date + "000000", stopdate + "000000" };
		return interval1day;

	}
	
	/**
	 * 间隔1天。
	 * @param date
	 * @return long类型的日期，如：1351785600000
	 */
	public static long[] interval1day_long(String date){
		String[] interval1day =interval1day(date);
		Calendar cal = format2.getCalendar();
		
		try {
			cal.setTime(format2.parse(interval1day[0]));
			long start_time=cal.getTimeInMillis();
			
			cal.setTime(format2.parse(interval1day[1]));
			long stop_time=cal.getTimeInMillis();
			
			long[] startTime_stopTime={start_time,stop_time};
			return startTime_stopTime;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
//		String[] sd = getStartRow_StopRow();
//		System.out.println("start====" + sd[0] + "===="+ formatDate(Long.parseLong(sd[0]) * 1000L, "yyyyMMddHHmmss"));
//		System.out.println("stop=====" + sd[1] + "===="+ formatDate(Long.parseLong(sd[1]) * 1000L, "yyyyMMddHHmmss"));
//
//		long[] sd2 = getStartRow_StopRow(15);
//		System.out.println("start====" + sd[0] + "===="+ formatDate(sd2[0] * 1000L, "yyyy-MM-dd HH:mm:ss"));
//		System.out.println("stop=====" + sd[1] + "===="+ formatDate(sd2[1] * 1000L, "yyyy-MM-dd HH:mm:ss"));
//		
//		String[] interval1day =interval1day("20121107");
//		System.out.println("start===="+interval1day[0]);
//		System.out.println("stop====="+interval1day[1]);
//		
//		long[] startTime_stopTime=interval1day_long("20121107");
//		System.out.println("start===="+startTime_stopTime[0]);
//		System.out.println("stop====="+startTime_stopTime[1]);
//		
//		
//		System.out.println(TimeUtil.formatTimeMillis("2012-11-30 23:00:00", "yyyy-MM-dd HH:mm:ss"));
		
//		List<String> argList = new LinkedList<String>();
//		argList.add("10");
//		args = argList.toArray(new String[argList.size()]);
		
		int interval=10;
		if(args!=null&&args.length==1){
			interval=Integer.parseInt(args[0]);
		}
		
		long[] sd2 = getStartRow_StopRow(interval);
		
		System.out.println(formatDate((sd2[0]+59) * 1000L, "yyyyMMddHHmmss")+"--"+formatDate((sd2[1]+59) * 1000L, "yyyyMMddHHmmss"));
		
	}
}
