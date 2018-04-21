package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static String ip = "127.0.0.1";
	private static int port = 3306;
	private static String database = "tmall";
	private static String encoding = "UTF-8";
	private static String logingName = "root";
	private static String password = "admin";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}		
	}
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://"+ip+":"+port+"/"+database+"?characterEncoding="+encoding;
		return DriverManager.getConnection(url, logingName, password);
		
	}
}
