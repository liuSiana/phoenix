package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import base.BasicClass;

/**
 * 截取屏幕的工具类
 * 
 * @author liuxinai
 *
 */
public class ScreenshotUtil {
	private static Logger logger = Logger.getLogger(ScreenshotUtil.class);

	/**
	 * 截取屏幕的方法
	 * 
	 * @param screeshotDir
	 *            保存截取屏幕的文件路径
	 * @return 
	 */
	public static File takeScreenshot(String screenshotDir) {
		//拿到dirver
		WebDriver driver = BasicClass.getDriver();
		//当前时间
		Date date = new Date();
		//获得毫秒值
		long time = date.getTime();
		//文件名称
		String fileName = time + ".jpg";
		//如果这个dirver是ChromeDriver的一个实例
		logger.info("开始截屏");
		//强转为接口类型
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		//源文件
		File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		//创建一个目标文件
		File destFile = new File(screenshotDir +File.separator+ fileName);
		//把截屏保存到screenshotDir这个目录中去
		try {
			logger.info("拷贝截屏到目标路径中去");
			FileUtils.copyFile(screenshotFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile;
	}
}
