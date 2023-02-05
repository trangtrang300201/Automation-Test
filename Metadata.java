package exercise;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Metadata {
	String URL_dashBoard = "http://sidoc-test.demo2.siten.vn:43803/enterprise/dashboard";
	String url_login = "http://sidoc-test.demo2.siten.vn:43803/auth/login";
	String user_login = "//input[@id='username']";
	String user_pass = "//input[@id='password-field']";
	String btn_Login = "//button[@type='submit']";
	String btn_create = "//button[@class='ant-btn ant-btn-primary ng-star-inserted']//span[@class='ng-star-inserted'][contains(text(),'Thêm mới')]";
	
	WebDriver driver;

	public void Login() throws InterruptedException {
		String username = "0110210190_trangltt";
		String password = "Trang123@";
		driver.findElement(By.xpath(user_login)).sendKeys(username);
		driver.findElement(By.xpath(user_pass)).sendKeys(password);
		driver.findElement(By.xpath(btn_Login)).click();
		Thread.sleep(2000);		
	}
	
	public void Create_Department(String code, String name) throws InterruptedException {
		Login();
		// Vào QL Phòng ban
		driver.findElement(By.xpath("//div[contains(text(),'Quản lý công ty')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[normalize-space()='QL phòng ban']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
		Thread.sleep(2000);
		//Thực hiện Thêm mới phòng ban
		WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã phòng ban']"));
		code_field.sendKeys(code);
		WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên phòng ban']"));
		name_field.sendKeys(name);
		Thread.sleep(2000);	
	}
	public void Create_User(String name, String email, String username, String department) throws InterruptedException {
		driver.findElement(By.xpath("//span[contains(text(),'QL người dùng')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Họ và tên']")).sendKeys(name);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(email);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Tên đăng nhập']")).sendKeys(username);
		Thread.sleep(2000);
		WebElement department_field = driver.findElement(By.xpath("//nz-select[@class='ant-select ng-tns-c110-1374 ng-tns-c119-1373 ant-select-in-form-item ant-select-show-arrow ant-select-show-search ant-select-allow-clear ant-select-single ng-untouched ng-pristine ng-valid ng-star-inserted']"));
		Select department_select = new Select(department_field);
		department_select.selectByIndex(0);
		
	}
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url_login);
	}
	@Test( priority = 1)
	public void CreateWithCodeDepartmentBlank() throws InterruptedException {
		String statusTestcase = "Fail";		
		try {	
			Create_Department("", "Phòng ban Trang Trang");
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã phòng ban là bắt buộc nhập')]")).getText();
			Assert.assertEquals(noti, "Mã phòng ban là bắt buộc nhập");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 1: Bỏ trống trường Mã phòng ban: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 2)
	public void CreateWithValidateCodeDepartmentIncorrect1() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "Mã";
			Create_Department(text, "Phòng ban Trang Trang");			
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 2: Nhập Mã phòng ban là tiếng việt có dấu: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 3)
	public void CreateWithValidateCodeDepartmentIncorrect2() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "ma loai";
			Create_Department(text, "Phòng ban Trang Trang");				
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 3: Nhập Mã phòng ban là có dấu cách: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 4)
	public void CreateWithValidateCodeInDepartmentorrect3() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "ma loai !@#$%^&*()__+`[ưƠ}|;':,./<>?";
			Create_Department(text, "Phòng ban Trang Trang");				
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã phòng ban chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 4: Nhập Mã phòng ban là có kí tự đặc biệt: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 5)
	public void createWithValidateLengthCodeDepartment() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "zxcvbnmasdfghjklqwertyuiop1234567890ZXCVBNMASDFGHJKLQWERTYUIOP";
			Create_Department(text, "Phòng ban Trang Trang");	
			WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã phòng ban']"));
			String code_value = code_field.getAttribute("value");
			boolean check = false;
			if (code_value.length()==50) check = true;
			Assert.assertTrue(check);
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 5: Nhập Mã phòng ban lớn hơn 50 kí tự: " + statusTestcase);
		
	}
	@Test(priority = 6)
	public void createWithExistingCodeDepartment_1() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "PB010";
			Create_Department(text, "Phòng ban Trang Trang");	
			driver.findElement(By.xpath(btn_create)).click();
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti, "Mã phòng ban đã tồn tại");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 6: Nhập Mã phòng ban trùng với mã đã tồn tại(giống nhau tất cả) : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 7)
	public void createWithExistingCodeDepartment_2() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "pb010";
			Create_Department(text, "Phòng ban Trang Trang");	
			driver.findElement(By.xpath(btn_create)).click();
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti, "Mã phòng ban đã tồn tại");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 7: Nhập Mã văn bản trùng với mã đã tồn tại(không phân biệt hoa thường) : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test(priority = 8)
	public void WhitespaceWithCodeDepartment() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "          PB011";
			Create_Department(text, "Phòng ban Trang Trang");
			Thread.sleep(2000);
			WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã phòng ban']"));
			String code_value = code_field.getAttribute("value");
			Assert.assertEquals(code_value, text.trim());
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 8: Nhập khoảng trắng Mã phòng ban đầu dòng : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test( priority = 9)
	public void CreateWithNameDepartmentBlank() throws InterruptedException {
		String statusTestcase = "Fail";		
		try {	
			Create_Department("PBTRANG", "");
			driver.findElement(By.xpath("//div[@class='card p-6']")).click();
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Tên phòng ban là bắt buộc nhập')]")).getText();
			Assert.assertEquals(noti, "Tên phòng ban là bắt buộc nhập");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 9: Bỏ trống trường tên phòng ban: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 10)
	public void createWithValidateLengthNameDepartment() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "Lê Thị Trang đang thêm mới phòng ban có định dạng như sau: !@##$%^&**()_+>?\":{}|<>?~`1234567890-=ơ]\\;',./";
			Create_Department("PBTRANG", text);
			driver.findElement(By.xpath("//div[@class='card p-6']")).click();
			Thread.sleep(2000);
			WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên phòng ban']"));
			String name_value = name_field.getAttribute("value");
			boolean check = false;
			if (name_value.length()<=100) check = true;
			Assert.assertTrue(check);
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 10: Nhập Tên phòng ban hơn 100 kí tự: " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test(priority = 11)
	public void WhitespaceWithNameDepartment() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "          Tên phòng ban Trang nhập";
			Create_Department("PBTRANG", text);
			driver.findElement(By.xpath("//div[@class='card p-6']")).click();
			Thread.sleep(2000);
			WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên phòng ban']"));
			String name_value = name_field.getAttribute("value");
			Assert.assertEquals(name_value, text.trim());
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 11: Nhập khoảng trắng Tên phòng ban đầu dòng : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	@Test(priority = 12)
	public void MetaData() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			Create_Department("PBTRANG", "Phòng ban Trang Trang");
			driver.findElement(By.xpath(btn_create)).click();
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti, "Thêm mới phòng ban thành công");
			//Thêm mới người dùng
			driver.findElement(By.xpath("//span[contains(text(),'QL người dùng')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Họ và tên']")).sendKeys("Lê Thị Trang");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("trang@gmail.com");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Tên đăng nhập']")).sendKeys("trangtrang");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//body/app-root[@id='root']/app-layout[1]/div[1]/div[2]/div[2]/div[2]/app-manage[1]/app-home-enterprises[1]/app-company-management[1]/app-user-management[1]/app-add-user[1]/div[1]/app-card[1]/div[1]/div[1]/div[1]/div[1]/menu-expansion-panel[1]/div[2]/div[1]/div[1]/form[1]/div[1]/nz-form-item[1]/nz-form-control[1]/div[1]/div[1]/nz-input-group[1]/nz-select[1]/nz-select-top-control[1]")).click();
			driver.findElement(By.xpath("//div[normalize-space()='Phòng ban Trang Trang']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
			Thread.sleep(2000);
			String noti_user = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti_user, "Thêm mới người dùng thành công!");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 12: Thêm mới thành công : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
