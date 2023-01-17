package exercise;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login {

	String URL_dashBoard = "http://sidoc-test.demo2.siten.vn:43803/enterprise/dashboard";
	String url_login = "http://sidoc-test.demo2.siten.vn:43803/auth/login";
	String user_login = "//input[@id='username']";
	String user_pass = "//input[@id='password-field']";
	String btn_Login = "//button[@type='submit']";

	WebDriver driver;

	public void Login(String username, String password) {
		driver.findElement(By.xpath(user_login)).sendKeys(username);
		driver.findElement(By.xpath(user_pass)).sendKeys(password);
		driver.findElement(By.xpath(btn_Login)).click();

	}

	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url_login);
	}
	//case 
	@Test
	public void loginByAdmin() throws InterruptedException {
		Login("ngothilinh", "12345678");
		Thread.sleep(5000);
		Assert.assertEquals(driver.getCurrentUrl(), URL_dashBoard);
	}
	//Case 1
	@Test
	public void loginWithUsernameBlankField() throws InterruptedException {
		Login("", "12345678");
		Thread.sleep(5000);
		String noti = driver.findElement(By.xpath("//small[@class='text-danger text-sm']")).getText();
		Assert.assertEquals(noti, "Tên đăng nhập là bắt buộc nhập");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}
	//Case 2
	@Test
	public void loginWithPasswordBlankField() throws InterruptedException {
		Login("ngothilinh", "");
		Thread.sleep(5000);
		String noti = driver.findElement(By.xpath("//small[@class='text-danger text-sm']")).getText();
		Assert.assertEquals(noti, "Mật khẩu là bắt buộc nhập");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}
	//Case 3
	@Test
	public void loginWithBlankField() throws InterruptedException {
		Login("", "");
		Thread.sleep(5000);
		String noti_username = driver.findElement(By.xpath("//small[contains(text(),'Tên đăng nhập là bắt buộc nhập')]")).getText();
		String noti_password = driver.findElement(By.xpath("//small[contains(text(),'Mật khẩu là bắt buộc nhập')]")).getText();
		Assert.assertEquals(noti_username, "Tên đăng nhập là bắt buộc nhập");
		Assert.assertEquals(noti_password, "Mật khẩu là bắt buộc nhập");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}

	//Case 4
	@Test
	public void loginWithValidateLengthUsername() throws InterruptedException {
		String text = "zxcvbnmasdfghjklqwertyuiopâăđêôơ~!@#$%^&*()_+-=Ơ}|\\\\\\\\][\\\":;'?><,./123456789";
		Login(text, "12345678");
		Thread.sleep(2000);
		String username_field = driver.findElement(By.xpath(user_login)).getAttribute("value");
		boolean check = false;
		if (username_field.length()==50) check = true;
		Assert.assertTrue(check);
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
		
	}

	//Case 5
	@Test
	public void loginWithValidateLengthPassword() throws InterruptedException {
		String text = "zxcvbnmasdfghjklqwertyuiopâăđêôơ~!@#$%^&*()_+-=Ơ}|\\\\\\\\][\\\":;'?><,./123456789";
		Login("ngothilinh", text);
		Thread.sleep(2000);
		String password_field = driver.findElement(By.xpath(user_pass)).getAttribute("value");
//		System.out.println(password_field);
		boolean check = false;
		if (password_field.length()==50) check = true;
		Assert.assertTrue(check);		
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}

	//Case 6
	@Test
	public void loginWithValidateUsername() throws InterruptedException {
		String text = "Lê Thị Trang";
		Login(text, "12345678");
		Thread.sleep(5000);
		String noti = driver.findElement(By.xpath("//small[@class='text-danger text-sm']")).getText();
		Assert.assertEquals(noti, "Tên đăng nhập không được chứa khoảng trắng");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}
	
	//Case 7
	@Test
	public void loginWithUsenameIncorrect() throws InterruptedException {
		Login("NgoThilinh1", "12345678");
//		Thread.sleep(500);
		String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
		Assert.assertEquals(noti, "Account Invalid");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}
	//Case 8
	@Test
	public void loginWithPasswordIncorrect() throws InterruptedException {
		Login("ngothilinh", "123456789");
//		Thread.sleep(500);
		String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
		Assert.assertEquals(noti, "Account Invalid");
		Assert.assertEquals(driver.getCurrentUrl(), url_login);
	}
	
	//Case 9
//	@Test
//	public void LoginWith5Incorrect() throws InterruptedException {
//		WebElement locator;
//		locator = driver.findElement(By.xpath(btn_Login));
//		Login("ngothilinh", "123456789");
//		for (int i = 0; i < 5;i ++) {
//			driver.findElement(By.xpath(btn_Login)).click();
//		}		
//		Thread.sleep(500);
//		String noti = driver.findElement(By.xpath("//p[@class='text-red-500 italic pb-1 ng-star-inserted']")).getText();
//		
//		boolean ktra = locator.isEnabled();
//		if (!ktra) {
//			Assert.assertEquals(noti, "Đăng nhập sai vượt quá 5 lần, vui lòng đăng nhập lại sau 2 phút");
//		}
//		Thread.sleep(2000);
//		Assert.assertTrue(ktra);
//		Assert.assertEquals(driver.getCurrentUrl(), url_login);
//	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}


}
