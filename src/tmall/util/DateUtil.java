package tmall.util;

public class DateUtil {
	public static java.sql.Timestamp d2t(java.util.Date date){
		if (null==date) {
			return null;
		}
		return new java.sql.Timestamp(date.getTime());
	}
	public static java.util.Date t2d(java.sql.Timestamp date){
		if (null==date) {
			return null;
		}
		return new java.util.Date(date.getTime());
	}
}
