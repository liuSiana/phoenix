package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
/**
 * 使用selenium工具webdirver进行浏览器的打开，通过传入浏览器类型，selenium版本及对应浏览器驱动文件的存放地址进行打开
 * @author liuxinai
 *
 */
public class SeleniumUtil {
	/**
	 * 
	 * @param browserType 浏览器类型
	 * @param seleniumVersion selenium版本
	 * @param driverPath 驱动文件的存放地址
	 */
	public static WebDriver openBrowser(String browserType,String seleniumVersion,String driverPath){
		WebDriver driver=null;
		if ("ie".equalsIgnoreCase(browserType)) {
			driver = openIE(driverPath);
		}
		if ("firefox".equalsIgnoreCase(browserType)) {
			driver = openFirefox(seleniumVersion, driverPath);
		}
		if ("chrome".equalsIgnoreCase(browserType)) {
			driver = openChrome(driverPath);
		}
		return driver;
	}
/**
 * 打开chrome浏览器
 * @param driverPath Chrome浏览器驱动文件的路径
 * @return
 */
	private static WebDriver openChrome(String driverPath) {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverPath);
		return new ChromeDriver();
		
	}
/**
 * 打开火狐浏览器
 * @param seleniumVersion 如果是3.x的selenium需要浏览器的驱动文件，此处传入selenium的版本
 * @param driverPath 火狐浏览器驱动文件的存放地址
 * @return
 */
	private static WebDriver openFirefox(String seleniumVersion, String driverPath) {
		if ("3.x".equalsIgnoreCase(seleniumVersion)) {
			System.setProperty("webdriver.gecko.driver", driverPath);
		}
		System.setProperty(SystemProperty.BROWSER_BINARY, "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		return new FirefoxDriver();
		
	}
/**
 * 打开IE浏览器
 * @param driverPath IE浏览器驱动文件的存放地址
 * @return
 */
	private static WebDriver openIE(String driverPath) {
		//对IE浏览器进行设置
		System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
		//driver期望的能力
		DesiredCapabilities capabilities=new DesiredCapabilities();
		//忽略IE安全设置
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		//忽略IE缩放设置
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		//设置初始url
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com");
		return new InternetExplorerDriver(capabilities);
		
	}
}
