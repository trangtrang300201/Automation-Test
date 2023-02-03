package exercise;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Document_Type_Create {
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
	
	public void Create(String code, String name, String note) throws InterruptedException {
		Login();
		// Vào QL Loại văn bản
		driver.findElement(By.xpath("//div[contains(text(),'Quản lý công ty')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'QL loại văn bản')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'Thêm mới')]")).click();
		Thread.sleep(2000);
		//Thực hiện Thêm mới
		WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã loại văn bản']"));
		code_field.sendKeys(code);
		WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên loại văn bản']"));
		name_field.sendKeys(name);
		WebElement note_field = driver.findElement(By.xpath("//textarea[@placeholder='Mô tả']"));
		note_field.sendKeys(note);
		Thread.sleep(2000);
		
	}

	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url_login);
	}
	@Test( priority = 1)
	public void CreateWithCodeBlank() throws InterruptedException {
		String statusTestcase = "Fail";		
		try {	
			Create("", "Loại văn bản Trang test", "");
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản là bắt buộc nhập')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản là bắt buộc nhập");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 1: Bỏ trống trường Mã văn bản: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 2)
	public void CreateWithValidateCodeIncorrect1() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "Mã";
			Create(text, "Loại văn bản Trang test", "");			
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 2: Nhập Mã văn bản là tiếng việt có dấu: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 3)
	public void CreateWithValidateCodeIncorrect2() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "ma loai";
			Create(text, "Loại văn bản Trang test", "");			
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 3: Nhập Mã văn bản là có dấu cách: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test( priority = 4)
	public void CreateWithValidateCodeIncorrect3() throws InterruptedException {
		String statusTestcase = "Fail";	
//		String regex = "[a-zA-Z0-9_-]";
		try {	
			String text = "ma loai !@#$%^&*()__+`[ưƠ}|;':,./<>?";
			Create(text, "Loại văn bản Trang test", "");			
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng A')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản chỉ gồm ký tự bảng chữ cái tiếng Anh và số, không chứa khoảng trắng và ký tự đặc biệt");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 4: Nhập Mã văn bản là có kí tự đặc biệt: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 5)
	public void createWithValidateLengthCode() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "zxcvbnmasdfghjklqwertyuiop1234567890ZXCVBNMASDFGHJKLQWERTYUIOP";
			Create(text, "Loại văn bản Trang test", "");
			WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã loại văn bản']"));
			String code_value = code_field.getAttribute("value");
			boolean check = false;
			if (code_value.length()==50) check = true;
			Assert.assertTrue(check);
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 5: Nhập Mã văn bản lớn hơn 50 kí tự: " + statusTestcase);
		
	}
	@Test(priority = 6)
	public void createWithExistingCode_1() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "sssa";
			Create(text, "Loại văn bản Trang test", "");
			//Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản đã bị trùng, vui lòng kiểm tra lại')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản đã bị trùng, vui lòng kiểm tra lại!");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 6: Nhập Mã văn bản trùng với mã đã tồn tại(giống nhau tất cả) : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 7)
	public void createWithExistingCode_2() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "SSSA";
			Create(text, "Loại văn bản Trang test", "");
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Mã loại văn bản đã bị trùng, vui lòng kiểm tra lại')]")).getText();
			Assert.assertEquals(noti, "Mã loại văn bản đã bị trùng, vui lòng kiểm tra lại");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 7: Nhập Mã văn bản trùng với mã đã tồn tại(không phân biệt hoa thường) : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test(priority = 8)
	public void WhitespaceWithCode() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "          Maloaivanban";
			Create(text, "Loại văn bản Trang test", "");
			Thread.sleep(2000);
			WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã loại văn bản']"));
			String code_value = code_field.getAttribute("value");
			Assert.assertEquals(code_value, text.trim());
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 8: Nhập khoảng trắng Mã văn bản đầu dòng : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test( priority = 9)
	public void CreateWithNameBlank() throws InterruptedException {
		String statusTestcase = "Fail";		
		try {	
			Create("Maloaivanban", "", "");
			String noti = driver.findElement(By.xpath("//div[contains(text(),'Tên loại văn bản là bắt buộc nhập')]")).getText();
			Assert.assertEquals(noti, "Tên loại văn bản là bắt buộc nhập");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 9: Bỏ trống trường tên văn bản: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	@Test(priority = 10)
	public void createWithValidateLengthName() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "Lê Thị Trang đang thêm mới loại văn bản có định dạng như sau: !@##$%^&**()_+>?\":{}|<>?~`1234567890-=ơ]\\;',./";
			Create("Maloaivanban", text, "");
			WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên loại văn bản']"));
			String name_value = name_field.getAttribute("value");
			boolean check = false;
			if (name_value.length()<=100) check = true;
			Assert.assertTrue(check);
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 10: Nhập Tên văn bản lớn hơn 100 kí tự: " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test(priority = 11)
	public void WhitespaceWithName() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "          Loại văn bản Trang test";
			Create("Maloaivanban", text, "");
			Thread.sleep(2000);
			WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên loại văn bản']"));
			String name_value = name_field.getAttribute("value");
			Assert.assertEquals(name_value, text.trim());
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 11: Nhập khoảng trắng Tên văn bản đầu dòng : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	@Test( priority = 12)
	public void CreateWithNoteBlank() throws InterruptedException {
		String statusTestcase = "Fail";		
		try {	
			Create("Maloaivanban", "Tên loại văn bản", "");
			driver.findElement(By.xpath(btn_create)).click();
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti, "Thêm mới loại văn bản thành công");
			Assert.assertEquals(driver.getCurrentUrl(), "http://sidoc-test.demo2.siten.vn:43803/enterprise/company-management/document-type-management");
			statusTestcase = "Pass";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 12: Bỏ trống trường Mô tả: " + statusTestcase );
		Assert.assertEquals(statusTestcase, "Pass");
	}
	
	@Test(priority = 13)
	public void createWithValidateLengthNote() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "Lê Thị Trang đang thêm mới loại văn bản có định dạng như sau: !@##$%^&**()_+>?\":{}|<>?~`1234567890-=ơ]\\;',./ Lê Thị Trang đang thêm mới loại văn bản có định dạng như sau: !@##$%^&**()_+>?\\\":{}|<>?~`1234567890-=ơ]\\\\;',./";
			Create("Maloaivanban", "Tên loại văn bane", text);
			driver.findElement(By.xpath("//div[@class='card p-6']")).click();
			Thread.sleep(2000);
			WebElement note_field = driver.findElement(By.xpath("//textarea[@placeholder='Mô tả']"));
			String note_value = note_field.getAttribute("value");
			boolean check = false;
			if (note_value.length()==200) check = true;
			Assert.assertTrue(check);
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.printStackTrace();
		}
		
		System.out.println("Test 13: Nhập Mô tả > 200 kí tự: " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@Test(priority = 14)
	public void WhitespaceWithNote() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			String text = "         Note này để trang test";
			Create("Maloaivanban", "Tên loại văn bản", text);
			driver.findElement(By.xpath("//div[@class='card p-6']")).click();
			Thread.sleep(2000);
			WebElement note_field = driver.findElement(By.xpath("//textarea[@placeholder='Mô tả']"));
			String note_value = note_field.getAttribute("value");
			Assert.assertEquals(note_value, text.trim());
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 14: Nhập mô tả có khoảng trắng đầu dòng : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	@Test(priority = 15)
	public void Create() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			Create("Loaivanban", "Tên loại văn bản", "Mô tả loại văn bản");
			driver.findElement(By.xpath(btn_create)).click();
			Thread.sleep(2000);
			String code = "";
			String name = "";
			String note = "";
			WebElement htmltable=driver.findElement(By.xpath("//div[@class='pt-4']//app-table"));
			List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
			List<WebElement> columns=rows.get(1).findElements(By.tagName("td"));
			code = columns.get(1).getText();
			name = columns.get(2).getText();
			note = columns.get(3).getText();
//			System.out.println(code);
//			System.out.println(name);
//			System.out.println(note);
			Assert.assertEquals("Loaivanban", code);
			Assert.assertEquals("Tên loại văn bản", name);
			Assert.assertEquals("Mô tả loại văn bản", note);			
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			e.getStackTrace();
		}
		System.out.println("Test 15: Thêm mới loại văn bản thành công, scroll vị trí : " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
