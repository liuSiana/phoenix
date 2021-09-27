package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties properties;
	static{
		loadProperties();
//		System.out.println("加载静态代码块");
	}

	private static void loadProperties() {
		properties=new Properties();
		try {
			properties.load(PropertiesUtil.class.getResourceAsStream("/url.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getURL(String strKey) {
		return properties.getProperty(strKey);
	}

}
