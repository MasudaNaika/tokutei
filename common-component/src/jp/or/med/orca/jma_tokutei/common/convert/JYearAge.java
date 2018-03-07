package jp.or.med.orca.jma_tokutei.common.convert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JYearAge {
	/**
	 * 生年月日から満年齢を求める
	 * @param birthDay
	 * @return 年齢
	 */
	public static String getAge(String birthDay) {

		float birthDayValue = -1f;
		int ageValue = 0;

		try {
			birthDayValue = new Float(birthDay);

		if (birthDayValue < 0)
			return "";

		// eidt s.inoue 2012/07/04
        Calendar calendar = Calendar.getInstance();
        String s1 = String.format("%d%02d%02d", new Object[] {
            Integer.valueOf(calendar.get(Calendar.YEAR)),
            Integer.valueOf(calendar.get(Calendar.MONTH) + 1),
            Integer.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
        });

		// eidt s.inoue 2012/07/04
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(simpledateformat.parse(birthDay));
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(simpledateformat.parse(s1));
        ageValue = calendar2.get(1) - calendar1.get(1);
        calendar1.clear(1);
        calendar2.clear(1);
        if(calendar1.after(calendar2))
        	ageValue--;
		} catch (Exception e) {
		}
		return String.valueOf(ageValue);
	}

//	/**
//	 * 検診日から年齢を求める
//	 * @param birthDay
//	 * @return 年齢
//	 */
//    public static String getAge(String s, String s1)
//    {
//        float f = -1F;
//        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
//        float f1 = 0.0F;
//        try
//        {
//            String s2 = (new StringBuilder()).append(s1.substring(0, 4)).append("/").append(s1.substring(4, 6)).append("/").append(s1.substring(6, 8)).toString();
//            java.util.Date date = DateFormat.getDateInstance().parse(s2);
//            String s3 = simpledateformat.format(date);
//            f1 = Integer.valueOf(s3).intValue();
//            f = (new Float(s)).floatValue();
//        }
//        catch(Exception exception) { }
//        if(f < 0.0F)
//        {
//            return "";
//        } else
//        {
//            Integer integer = Integer.valueOf(Math.round((f1 - f) / 10000F));
//            return integer.toString();
//        }
//    }

	//************************************************************************************************
	/**
	* 年齢を返します。
	* @param format 引数date1、date2の書式を指定します。yyyymmddまたはyyyy/mm/ddです。
	* @param date1 算出対象日を指定します。
	* @param date2 基準日を指定します。
	* @return date1とdate2の間隔を年数で返します。
	*/
	//************************************************************************************************
	public static String getAge(String date1, String date2) {
	int year1, month1, day1;
	int year2, month2, day2;
	int age = 0;
//	String[] values;


//	if (format.equals("yyyy/mm/dd")) {
//	values = date1.split("/"); // キー部を分割する
//	year1 = Integer.parseInt(values[0]);
//	month1 = Integer.parseInt(values[1]);
//	day1 = Integer.parseInt(values[2]);
//
//	values = date2.split("/"); // キー部を分割する
//	year2 = Integer.parseInt(values[0]);
//	month2 = Integer.parseInt(values[1]);
//	day2 = Integer.parseInt(values[2]);
//	} else {
//	year1 = Integer.parseInt(Functions.substringB(date1, 0, 4));
//	month1 = Integer.parseInt(Functions.substringB(date1, 4, 6));
//	day1 = Integer.parseInt(Functions.substringB(date1, 6, 8));
	year1 = Integer.parseInt(date1.substring(0, 4));
	month1 = Integer.parseInt(date1.substring(4, 6));
	day1 =  Integer.parseInt(date1.substring(6, 8));
//	year2 = Integer.parseInt(Functions.substringB(date2, 0, 4));
//	month2 = Integer.parseInt(Functions.substringB(date2, 4, 6));
//	day2 = Integer.parseInt(Functions.substringB(date2, 6, 8));
	year2 = Integer.parseInt(date2.substring(0, 4));
	month2 = Integer.parseInt(date2.substring(4, 6));
	day2 =  Integer.parseInt(date2.substring(6, 8));
//	}
	// 年齢計算
	age = year2 - year1;
	if (age != 0) {
	if (month1 > month2) {
	age = age - 1;
	} else if (month1 == month2) {
	if (day1 > day2) {
	age = age - 1;
	}
	}
	}
	System.out.println(age);
	System.out.println(age);
	return String.valueOf(age);
	}
}
