package util;

import java.util.Date;
import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
	public static Date getCurrentTime() {
		Date date = new Date();
		date.setTime(date.getTime() + 8*60*60*1000);
		return date;
	}

}
