package listener;

import java.io.File;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;


import util.ScreenshotUtil;

public class PhoenixListener extends TestListenerAdapter {
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		System.out.println("进入监听器");
		//得到surfire的路径
		//D:\workspace\phoenix_web_auto\target\surefire-reports\Suit
		String surfireDir=tr.getTestContext().getOutputDirectory();
		//截取到最后一个反斜杠\
		//截取到D:\workspace\phoenix_web_auto\target\surefire-reports
		String surfireoutputDir=surfireDir.substring(0, surfireDir.lastIndexOf("\\"));
		//得倒testng中的test的名称
		String testName=tr.getTestContext().getCurrentXmlTest().getName();
		//最后拼接到截取屏幕的路径中，这样可以将每个测试用例的测试失败的截屏分目录保存
		String screeshotDir=surfireoutputDir+File.separator+"screenshot"+File.separator+testName;
		System.out.println(screeshotDir);
		//使用工具类进行截图，截图所得到的是一个File类型的文件
		File screenshotFile=ScreenshotUtil.takeScreenshot(screeshotDir);
		//得到图片的本地存放绝对路径
		String imageAbsolute =screenshotFile.getAbsolutePath();
		//部署到服务器上的基本路径
		String baseURL="http://localhost:80/";
		//截取本地路径中需要替换的部分
		String oldChar=imageAbsolute.substring(0, imageAbsolute.indexOf("screenshot"));
		//将本地路径替换为服务器访问地址，得到服务器访问图片的地址
		String imageTemp=imageAbsolute.replace(oldChar, baseURL);
		//将所有的反斜杠替换为正斜杠
		String imageURL=imageTemp.replace("\\", "/");
		//将图片的访问地址放置在页面标签中
		String imageLogTag = "<img src='"+imageURL+"' hight='100' width='100'><a href='"+imageURL+"' target='_blank'>点击查看大图</a></img>";
		//以log的形式嵌套进报告页面中
		Reporter.log(imageLogTag);
	}

}
