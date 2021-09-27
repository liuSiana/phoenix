package base;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.net.Urls;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import util.PropertiesUtil;
import util.ReadXMLUtil;
import util.SeleniumUtil;

public class BasicClass {
	private static Logger logger = Logger.getLogger(BasicClass.class);
	/**
	 * 此处使用static修饰全局变量 在内存中只会有一份，大家共用
	 * 如果不用static修饰，是对象属性，各不相同，此时在不同的方法中调用就会报空指针异常
	 */

	protected static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		BasicClass.driver = driver;
	}

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
	/*
	 * protected WebElement getElement(long timeOutInSeconds, By by) {
	 * WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
	 * WebElement element = wait.until(new ExpectedCondition<WebElement>() {
	 * 
	 * @Override public WebElement apply(WebDriver arg0) { return
	 * driver.findElement(by); } }); return element; }
	 */

	protected WebElement getElement(long timeOutInSeconds, String keyword, Class<?> clazz) {
		String className = clazz.getName();
		Locator locator = ReadXMLUtil.pageMap.get(className).get(keyword);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver arg0) {
				String byString = locator.getBy();
				String valueString = locator.getValue();
				String descString = locator.getDesc();
				/*
				 * By by=null; if ("id".equals(byString)) {
				 * by=by.id(valueString); }else if ("name".equals(byString)) {
				 * by=by.name(valueString); }else if
				 * ("tagName".equals(byString)) { by=by.tagName(valueString);
				 * }else if ("className".equals(byString)) {
				 * by=by.className(valueString); }else if
				 * ("linkText".equals(byString)) { by=by.linkText(valueString);
				 * }else if ("partialLinkText".equals(byString)) {
				 * by=by.partialLinkText(valueString); }else if
				 * ("cssSelector".equals(byString)) {
				 * by=by.cssSelector(valueString); }else if
				 * ("xpath".equals(byString)) { by=by.xpath(valueString); }
				 */
				By by = null;
				// 不管是id，name，cssSelector...都是by类中的方法
				// 都可以通过字节码得到这些类的细节
				Class<By> clazz = By.class;
				try {
					logger.info("开始获取元素");
					// 得到对应的方法，byString是方法的名称，后面的参数是所要传的参数类型的字节码
					Method method = clazz.getMethod(byString, String.class);
					// 执行这个方法，第一个参数是要调用的方法的对象，如果不需要对象调用，就是null，第二个参数是调用的方法中的参数
					by = (By) method.invoke(null, valueString);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				logger.info("已经获取到元素" + driver.findElement(by).getText());
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
	protected WebElement getElement(String keyword, Class<?> clazz) {
		return getElement(5, keyword, clazz);
	}

	/**
	 * 只能等待，访问当前类的字节码对象
	 * 
	 * @param keyword
	 *            元素的desc
	 * @return
	 */
	protected WebElement getElement(String keyword) {
		return getElement(5, keyword, this.getClass());
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
	protected List<WebElement> getElements(long timeOutInSeconds, String keyword, Class<?> clazz) {
		String className = clazz.getName();
		Locator locator = ReadXMLUtil.pageMap.get(className).get(keyword);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		List<WebElement> elements = wait.until(new ExpectedCondition<List<WebElement>>() {

			@Override
			public List<WebElement> apply(WebDriver arg0) {
				String byString = locator.getBy();
				String valueString = locator.getValue();
				String descString = locator.getDesc();

				
			/*	By by = null;
				if ("id".equals(byString)) {
					by = by.id(valueString);
				} else if ("name".equals(byString)) {
					by = by.name(valueString);
				} else if ("tagName".equals(byString)) {
					by = by.tagName(valueString);
				} else if ("className".equals(byString)) {
					by = by.className(valueString);
				} else if ("linkText".equals(byString)) {
					by = by.linkText(valueString);
				} else if ("partialLinkText".equals(byString)) {
					by = by.partialLinkText(valueString);
				} else if ("cssSelector".equals(byString)) {
					by = by.cssSelector(valueString);
				} else if ("xpath".equals(byString)) {
					by = by.xpath(valueString);
				}*/
				 
				
				// 利用反射来代替上面的if else的各种判断
				By by = null;
				// 不管是id，name，cssSelector...都是by类中的方法
				// 都可以通过字节码得到这些类的细节
				Class<By> clazz = By.class;
				try {
					// 得到对应的方法，byString是方法的名称，后面的参数是所要传的参数类型的字节码
					Method method = clazz.getMethod(byString, String.class);
					// 执行这个方法，第一个参数是要调用的方法的对象，如果不需要对象调用，就是null，第二个参数是调用的方法中的参数
					by = (By) method.invoke(null, valueString);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				return driver.findElements(by);
			}
		});
		return elements;
	}

	/**
	 * 智能等待，默认等待5秒
	 * 
	 * @param by
	 *            by对象
	 * @return 返回元素列表
	 */
	protected List<WebElement> getElements(String keyword, Class<?> clazz) {

		return getElements(5, keyword, clazz);
	}

	/**
	 * 访问当前类的字节码对象
	 * 
	 * @param keyword
	 *            desc描述信息
	 * @return
	 */
	protected List<WebElement> getElements(String keyword) {

		return getElements(5, keyword, this.getClass());
	}

	/**
	 * 访问URL
	 * 
	 * @param urlStr
	 *            key值:从url.properties文件中传入key值，得到value（url地址）
	 */
	protected void to(String urlKey) {
		/*
		 * //创建一个properties对象，每次调用to()方法，都会new一个对象出来，在性能方面会比较耗资源，为了解决此问题，
		 * 将new对象和加载资源放置一个静态代码块中 Properties properties=new Properties(); try {
		 * //以输入流的方式加载文件 properties.load(BasicClass.class.getResourceAsStream(
		 * "/url.properties")); } catch (IOException e) { e.printStackTrace(); }
		 * //通过key来获取对一个的url地址 String url=properties.getProperty(urlKey);
		 * //访问url地址 driver.get(url);
		 */
		String url = PropertiesUtil.getURL(urlKey);
		driver.get(url);
		logger.info("成功访问URL");
	}

	/**
	 * 窗口最大化
	 */
	protected void maximize() {
		driver.manage().window().maximize();
		logger.info("窗口已经最大化");
	}

	/**
	 * 后退
	 */
	protected void back() {
		driver.navigate().back();
		logger.info("已经后退");
	}

	/**
	 * 前进
	 */
	protected void forward(String url) {
		driver.navigate().forward();
		logger.info("已经前进");
	}

	/**
	 * 刷新
	 */
	protected void refresh(String url) {
		driver.navigate().refresh();
		logger.info("已经刷新");
	}

	/**
	 * 输入内容
	 * 
	 * @param content
	 *            要输入的内容
	 * @param webElement
	 *            输入框元素
	 */
	protected void type(String keyword, String content,Class<?> clazz) {
		WebElement webElement = getElement(keyword,clazz);
		webElement.sendKeys(content);
		logger.info("已经往[" + keyword + "]中输入元素[" + content + "]");
	}
	/**
	 * 输入内容,调用当前类的页面元素
	 * 
	 * @param content
	 *            要输入的内容
	 * @param webElement
	 *            输入框元素
	 */
	protected void type(String keyword, String content) {
		type(keyword, content,this.getClass());
	}

	/**
	 * 点击
	 * 
	 * @param webElement
	 *            要点击的元素
	 */
	protected void click(String keyword,Class<?> clazz) {
		WebElement webElement = getElement(keyword,clazz);
		webElement.click();
		logger.info("已经点击元素[" + keyword + "]");
	}
	/**
	 * 点击当前类对应的页面元素
	 * 
	 * @param webElement
	 *            要点击的元素
	 */
	protected void click(String keyword) {
		click(keyword,this.getClass());
	}

	/**
	 * 获取文本信息
	 * 
	 * @param webElement
	 *            要获取文本的元素
	 * @return 返回文本内容
	 */
	protected String getText(String keyword,Class<?> calzz) {
		WebElement webElement = getElement(keyword,calzz);
		String text = webElement.getText();
		logger.info("已经获取到文本[" + keyword + "]的文本，内容是[" + text + "]");
		return text;
	}
	/**
	 * 获取当前类对应的页面文本信息
	 * 
	 * @param webElement
	 *            要获取文本的元素
	 * @return 返回文本内容
	 */
	protected String getText(String keyword) {
		String text = getText(keyword,this.getClass());
		return text;
	}

	// assertTextPresent 断言页面元素文本值为某文本
	/**
	 * 断言实际文本与预期文本
	 * 
	 * @param keyword
	 *            实际文本的关键字
	 * @param expectedText
	 *            预期文本
	 */
	protected void assertTextPresent(String keyword, String expectedText) {
		// 通过关键字可以得到元素的文本信息
		String acturalText = getText(keyword);
		assertEquals(acturalText, expectedText);
	}

	// assertPartTextPresent 断言页面元素文本值包含某文本
	/**
	 * 断言页面元素中包含某文本
	 * 
	 * @param keyword
	 *            传入关键词获取页面元素文本内容
	 * @param expectedText
	 *            预期文本内容
	 */
	protected void assertPartTextPresent(String keyword, String expectedText) {
		// 通过关键字可以得到元素的文本信息
		String acturalText = getText(keyword);
		assertTrue(acturalText.contains(expectedText));
	}

	// assertElementEditable 断言某元素是可用状态
	/**
	 * 断言页面元素是可用的状态
	 * 
	 * @param keyword
	 *            传入关键词获取页面元素
	 */
	protected void assertElementEditable(String keyword) {
		// 通过关键字可以得到元素
		WebElement element = getElement(keyword);
		assertTrue(element.isEnabled());
	}

	// assertElementNotEditable 断言某元素不可编辑
	/**
	 * 断言页面元素是不可用的状态
	 * 
	 * @param keyword
	 */
	protected void assertElementNotEditable(String keyword) {
		// 通过关键字可以得到元素
		WebElement element = getElement(keyword);
		assertFalse(element.isEnabled());
	}

	// assertURLContains 断言当前URL包含
	/**
	 * 断言当前URL地址中包含某内容
	 * 
	 * @param expectedURLContent
	 */
	protected void assertURLContains(String expectedURLContent) {
		String currentUrl = driver.getCurrentUrl();
		assertTrue(currentUrl.contains(expectedURLContent));
	}

	/**
	 * 断言获取到的文本信息不包含预期文本信息
	 * 
	 * @param expectedURLContent
	 */
	// assertTextNotPresent
	protected void assertTextNotPresent(String keyword, String expectedText) {
		// 通过关键字可以得到元素的文本信息
		String acturalText = getText(keyword);
		assertNotEquals(acturalText, expectedText);
	}

	// assertElementAttributeValueEquals
	/**
	 * 断言元素的属性值和预期的属性值相等
	 * 
	 * @param keyword
	 *            获取元素的关键字
	 * @param attributeStr
	 *            元素的属性名称
	 * @param expectedAttributeValue
	 *            预期元素的属性值
	 */
	protected void assertElementAttributeValueEquals(String keyword, String attributeStr,
			String expectedAttributeValue) {
		// 通过关键字可以得到元素的文本信息
		String attributeValue = getElement(keyword).getAttribute(attributeStr);
		assertEquals(attributeValue, expectedAttributeValue);
	}

	// assertElementAttributeValueNotEquals
	/**
	 * 断言元素的属性值和预期的属性值不相等
	 * 
	 * @param keyword
	 *            获取元素的关键字
	 * @param attributeStr
	 *            元素的属性名称
	 * @param expectedAttributeValue
	 *            预期元素的属性值
	 */
	protected void assertElementAttributeValueNotEquals(String keyword, String attributeStr,
			String expectedAttributeValue) {
		// 通过关键字可以得到元素的文本信息
		String attributeValue = getElement(keyword).getAttribute(attributeStr);
		assertNotEquals(attributeValue, expectedAttributeValue);
	}

}
