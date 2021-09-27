package testcase;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass6;
import base.Locator;
import util.ExcelUtil2;
import util.ReadXMLUtil;

public class Register_Tester_Case_11 extends BasicClass6{
	@Test(dataProvider="provideFalureData")
	public void test_case_1(String mobilephone,String password,String pwdconfirm,String verifycode,String expectedTips) {
//		to("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		
		to("registerURL");
		type("手机号码输入框", mobilephone);
		type("密码输入框", password);
		type("确认密码输入框", pwdconfirm);
		type("验证码输入框", verifycode);
		click("注册按钮");
		String actualTips=getText("提示信息");
		assertEquals(actualTips, expectedTips);
	}
	@Test(dataProvider="providesuccessData")
	public void test_case_2(String mobilephone,String password,String pwdconfirm,String verifycode,String expectedTips) {
//		to("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");		
		to("registerURL");
		type("手机号码输入框", mobilephone);
		type("密码输入框", password);
		type("确认密码输入框", pwdconfirm);
		type("验证码输入框", verifycode);
		click("注册按钮");
//		String loginButtonText=getText("登录按钮");
//		driver.findElement(By.id("login")).isDisplayed();
		getElement("登录按钮",LoginTester.class).isDisplayed();
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
	public Object[][] provideFalureData() {
		Object[][] data=ExcelUtil2.readExcel("/register.xlsx",0, 2, 7, 1, 5);
		return data;
	}
	@DataProvider
	public Object[][] providesuccessData() {
		Object[][] data=ExcelUtil2.readExcel("/register.xlsx",0, 8, 8, 1, 5);
		return data;
	}

}
