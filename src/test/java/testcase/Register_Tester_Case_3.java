package testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass;

public class Register_Tester_Case_3 extends BasicClass{
	@Test(dataProvider="provideData")
	public void test_case(String mobilephone,String password,String pwdconfirm,String expectedTips) {
		driver.get("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		driver.findElement(By.id("mobilephone")).sendKeys(mobilephone);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("pwdconfirm")).sendKeys(pwdconfirm);
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
		Object[][] data={{"","","","用户名不能为空"},
				{"18210342582","","","密码不能为空"},
				{"18210342582","123456","","重复密码不能为空"},
				{"18210342582","123456","123333","密码不一致"},
				{"18210342582","123","","密码长度至少为6位"},
				{"18210","","","非法的手机号"}};
		return data;
	}

}
