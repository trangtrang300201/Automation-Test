package exercise;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Logout {
	String URL_dashBoard = "http://sidoc-test.demo2.siten.vn:43803/enterprise/dashboard";
	String url_login = "http://sidoc-test.demo2.siten.vn:43803/auth/login";
	String user_login = "//input[@id='username']";
	String user_pass = "//input[@id='password-field']";
	String btn_Login = "//button[@type='submit']";
	String btn_Logout = "//span[contains(text(),'Đăng xuất')]";
	String btn_combobox_name = "//div[@class='ant-dropdown-trigger flex items-center h-full']";

	WebDriver driver;

	public void Login(String username, String password) {
		driver.findElement(By.xpath(user_login)).sendKeys(username);
		driver.findElement(By.xpath(user_pass)).sendKeys(password);
		driver.findElement(By.xpath(btn_Login)).click();

	}
	public void Logout() {
		driver.findElement(By.xpath(btn_combobox_name)).click();
		driver.findElement(By.xpath(btn_Logout)).click();

	}

	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url_login);
	}

	//case  1
	@Test( priority = 1)
	public void CheckNotiLogout() throws InterruptedException {

		String statusTestcase = "Fail";
		try {
			Login("0110210190_trangltt", "Trang123@");
			Thread.sleep(5000);
			Logout();
			Thread.sleep(5000);
			Assert.assertEquals(driver.getCurrentUrl(), url_login);
			statusTestcase = "Pass";

		} catch (Exception e) {

			e.getStackTrace();
		}
		System.out.println("Test 1: Check Noti Logout and URL: " + statusTestcase );
	}

	//case  2
	@Test( priority = 2)
	public void CheckLocalstorage() throws InterruptedException {

		String statusTestcase = "Fail";
		try {
			Login("0110210190_trangltt", "Trang123@");
			Thread.sleep(5000);
			Logout();
			LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
			Thread.sleep(5000);
			if (localStorage.size()==0) {
				statusTestcase = "Pass";
			}
			Assert.assertEquals(statusTestcase, "Pass");
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("Test 2: Check Local Storage: " + statusTestcase );

	}
	//case  3
	@Test( priority = 3)
	public void CheckSessionstorage() throws InterruptedException {

		String statusTestcase = "Fail";
		try {
			Login("0110210190_trangltt", "Trang123@");
			Thread.sleep(5000);
			Logout();
			SessionStorage sessionStorage = ((WebStorage) driver).getSessionStorage();
			Thread.sleep(5000);
			if (sessionStorage.size()==0) {
				statusTestcase = "Pass";
			}
			Assert.assertEquals(statusTestcase, "Pass");
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("Test 3: Check Session Storage: " + statusTestcase );

	}
	//case  4
	@Test( priority = 4)
	public void CheckCookies() throws InterruptedException {

		String statusTestcase = "Fail";
		try {
			Login("0110210190_trangltt", "Trang123@");
			Thread.sleep(5000);
			Logout();
			Set<Cookie> cookiesList =  driver.manage().getCookies();
			Thread.sleep(5000);
			if (cookiesList.size()==0) {
				statusTestcase = "Pass";
			}
			Assert.assertEquals(statusTestcase, "Pass");
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("Test 4: Check Cookie: " + statusTestcase );

	}
	//case  5
		@Test( priority = 5)
		public void CheckSession() throws InterruptedException {

			String statusTestcase = "Fail";
			try {
				Login("0110210190_trangltt", "Trang123@");
				Thread.sleep(20000);
				driver.findElement(By.cssSelector("body > app-root:nth-child(1) > app-layout:nth-child(1) > div:nth-child(1) > div:nth-child(1) > app-header:nth-child(1) > header:nth-child(1) > div:nth-child(2) > div:nth-child(1) > app-header-menu-list:nth-child(1) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1)")).click();
				Assert.assertEquals(driver.getCurrentUrl(), url_login);
				statusTestcase = "Pass";
				
			} catch (Exception e) {
				e.getStackTrace();
			}
			System.out.println("Test 5: Check Session: " + statusTestcase );

		}

		@AfterMethod
		public void tearDown() {
			driver.quit();
		}
}
