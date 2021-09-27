package base;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.jmx.LoggerDynamicMBean;
import org.omg.PortableServer.ServantActivator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import testcase.Register_Tester_Case_1;
import util.SeleniumUtil;

public class BasicClass2 {
	private static Logger logger=Logger.getLogger(BasicClass2.class);
	/**
	 * 此处使用static修饰全局变量 在内存中只会有一份，大家共用
	 * 如果不用static修饰，是对象属性，各不相同，此时在不同的方法中调用就会报空指针异常
	 */

	protected static WebDriver driver;

	@BeforeSuite
	@Parameters(value = { "browserType", "seleniumVersion", "driverPath" })
	public static void beforSuite(String browserType, String seleniumVersion, String driverPath) {
		driver = SeleniumUtil.openBrowser(browserType, seleniumVersion, driverPath);
		// driver.get("https://www.baidu.com");
		// driver.get("https://www.singulato.com/wap-login?model=login");
	}

	@AfterSuite
	public static void afterSuit() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}

	/**
	 * 智能等待
	 * 
	 * @param timeOutInSeconds
	 *            等待的时间，以秒为单位
	 * @param by
	 *            by对象
	 * @return 返回单个元素对象
	 */
	protected WebElement getElement(long timeOutInSeconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {

			@Override
			public WebElement apply(WebDriver arg0) {
				return driver.findElement(by);
			}
		});
		return element;
	}

	/**
	 * 智能等待，默认等待5秒
	 * 
	 * @param by
	 *            by对象
	 * @return 返回单个元素对象
	 */
	protected WebElement getElement(By by) {
		return getElement(5, by);
	}

	/**
	 * 智能等待
	 * 
	 * @param timeOutInSeconds
	 *            等待的时间，以秒为单位
	 * @param by
	 *            by对象
	 * @return 返回元素列表
	 */
	protected List<WebElement> getElements(long timeOutInSeconds, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		List<WebElement> elements = wait.until(new ExpectedCondition<List<WebElement>>() {

			@Override
			public List<WebElement> apply(WebDriver arg0) {
				return driver.findElements(by);
			}
		});
		return elements;
	}

	/**
	 * 智能等待，默认等待5秒
	 * 
	 * @param by by对象
	 * @return 返回元素列表
	 */
	protected List<WebElement> getElements(By by) {

		return getElements(5, by);
	}
	/**
	 * 访问URL
	 * @param urlStr key值:从url.properties文件中传入key值，得到value（url地址）
	 */
	protected void to(String urlKey) {
		//创建一个properties对象
		Properties properties=new Properties();
		try {
			//以输入流的方式加载文件
			properties.load(BasicClass2.class.getResourceAsStream("/url.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//通过key来获取对一个的url地址
		String url=properties.getProperty(urlKey);
		//访问url地址
		driver.get(url);
	}
	/**
	 * 窗口最大化
	 */
	protected void  maximize() {
		driver.manage().window().maximize();
	}
	/**
	 * 后退
	 */
	protected void  back() {
		driver.navigate().back();
	}
	/**
	 * 前进
	 */
	protected void  forward(String url) {
		driver.navigate().forward();
	}
	/**
	 * 刷新
	 */
	protected void  refresh(String url) {
		driver.navigate().refresh();
	}
	/**
	 * 输入内容
	 * @param content 要输入的内容
	 * @param webElement 输入框元素
	 */
	protected void  type(WebElement webElement,String content) {
		webElement.sendKeys(content);
	}
	/**
	 * 点击
	 * @param webElement 要点击的元素
	 */
	protected void  click(WebElement webElement) {
		webElement.click();
	}
	/**
	 * 获取文本信息
	 * @param webElement 要获取文本的元素
	 * @return 返回文本内容
	 */
	protected String getText(WebElement webElement) {
		logger.info("开始获取文本信息");
		String text=webElement.getText();
		logger.info("结束获取文本信息");
		return text;
	}

}
