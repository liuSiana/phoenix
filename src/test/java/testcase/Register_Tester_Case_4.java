package testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass;
import util.ExcelUtil2;

public class Register_Tester_Case_4 extends BasicClass{
	@Test(dataProvider="provideData")//dataProvidor的值，就是调用的方法名称
	public void test_case(String mobilephone,String password,String pwdconfirm,String verifycode,String expectedTips) {
		driver.get("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		driver.findElement(By.id("mobilephone")).sendKeys(mobilephone);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("pwdconfirm")).sendKeys(pwdconfirm);
		driver.findElement(By.id("verifycode")).sendKeys(verifycode);
		driver.findElement(By.id("signup-button")).click();
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	/**
	 * 数据解耦：
	 * key-value--->property属性文件
	 * 对象、实体信息--》xml、json、property属性文件
	 * 多个对象、实体信息--》xml、Excel
	 * 几行几列---》Excel--》读取文件--》解析文件内容
	 * @return
	 */
	@DataProvider
	public Object[][] provideData() {
		Object[][] data=ExcelUtil2.readExcel("/register.xlsx",0, 2, 7, 1, 5);
		return data;
	}

}
