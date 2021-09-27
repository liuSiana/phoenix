package testcase;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BasicClass;

public class Register_Tester_Case_2 extends BasicClass{
	@BeforeMethod
	public void beforeClass() {
		driver.get("http://test.lemonban.com/lmcanon_web_auto/mng/register.html");
		
	}
	@Test
	public void test_case_001() {
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="用户名不能为空";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	@Test
	public void test_case_002() {
		driver.findElement(By.id("mobilephone")).sendKeys("18210342582");
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="密码不能为空";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	@Test
	public void test_case_003() {
		driver.findElement(By.id("mobilephone")).sendKeys("18210342582");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="重复密码不能为空";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	@Test
	public void test_case_004() {
		driver.findElement(By.id("mobilephone")).sendKeys("18210342582");
		driver.findElement(By.id("password")).sendKeys("123456");
		driver.findElement(By.id("pwdconfirm")).sendKeys("12345");
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="密码不一致";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	} 
	@Test
	public void test_case_005() {
		driver.findElement(By.id("mobilephone")).sendKeys("18210342582");
		driver.findElement(By.id("password")).sendKeys("1234");
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="密码长度至少为6位";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	@Test
	public void test_case_006() {
		driver.findElement(By.id("mobilephone")).sendKeys("18210342");
		driver.findElement(By.id("signup-button")).click();
		String expectedTips="非法的手机号";
		String actualTips=driver.findElement(By.className("tips")).getText();
		assertEquals(actualTips, expectedTips);
	}
	@DataProvider
	public Object[][] provideDta() {
		Object[][] data={};
		return data;
	}

}
