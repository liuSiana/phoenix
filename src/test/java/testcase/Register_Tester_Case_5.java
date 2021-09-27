package testcase;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass2;
import util.ExcelUtil2;

public class Register_Tester_Case_5 extends BasicClass2{
	@Test(dataProvider="provideData")
	public void test_case(String mobilephone,String password,String pwdconfirm,String verifycode,String expectedTips) {
//		to("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		to("registerURL");
		type(getElement(By.id("mobilephone")), mobilephone);
		type(getElement(By.id("password")), password);
		type(getElement(By.id("pwdconfirm")), pwdconfirm);
		type(getElement(By.id("verifycode")), verifycode);
//		getElement(By.id("signup-button")).click();
		click(getElement(By.id("signup-button")));
		String actualTips=getText(getElement(By.className("tips")));
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
