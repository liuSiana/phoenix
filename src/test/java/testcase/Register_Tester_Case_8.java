package testcase;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass4;
import base.Locator;
import util.ExcelUtil2;
import util.ReadXMLUtil;

public class Register_Tester_Case_8 extends BasicClass4{
	@Test(dataProvider="provideData")
	public void test_case(String mobilephone,String password,String pwdconfirm,String verifycode,String expectedTips) {
//		to("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		to("registerURL");
//		Locator locator=ReadXMLUtil.pageMap.get(this.getClass().getName()).get("手机号码输入框");
		type(getElement("手机号码输入框"), mobilephone);
		type(getElement("密码输入框"), password);
		type(getElement("确认密码输入框"), pwdconfirm);
		type(getElement("验证码输入框"), verifycode);
		click(getElement("注册按钮"));
		String actualTips=getText(getElement("提示信息"));
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
