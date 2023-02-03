package exercise;

import static org.testng.Assert.assertTrue;

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

public class Document_Type {
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
		driver.findElement(By.xpath(btn_create)).click();
		Thread.sleep(2000);
		String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
		Assert.assertEquals(noti, "Thêm mới loại văn bản thành công");
		
	}
	public void Update(String code, String name, String note) throws InterruptedException {
		driver.findElement(By.xpath("//tbody/tr[1]/td[6]/div[1]/div[1]/button[1]/span[1]")).click();
		Thread.sleep(2000);
		WebElement code_field = driver.findElement(By.xpath("//input[@placeholder='Mã loại văn bản']"));
		code_field.sendKeys(code);
		Thread.sleep(2000);
		WebElement name_field = driver.findElement(By.xpath("//input[@placeholder='Tên loại văn bản']"));
		name_field.sendKeys(name);
		Thread.sleep(2000);
		WebElement note_field = driver.findElement(By.xpath("//textarea[@placeholder='Mô tả']"));
		note_field.sendKeys(note);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'Lưu')]")).click();
		Thread.sleep(2000);
		String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
		Assert.assertEquals(noti, "Cập nhật loại văn bản thành công");
	}
	public boolean Delete() throws InterruptedException {
		boolean check = false;
		WebElement htmltable_before=driver.findElement(By.xpath("//div[@class='pt-4']//app-table"));
		List<WebElement> rows_before=htmltable_before.findElements(By.tagName("tr"));
		driver.findElement(By.xpath("//tbody/tr[1]/td[6]/div[1]/div[2]/button[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[contains(text(),'Xác nhận')]")).click();
		Thread.sleep(2000);
		WebElement htmltable_after=driver.findElement(By.xpath("//div[@class='pt-4']//app-table"));
		List<WebElement> rows_after=htmltable_after.findElements(By.tagName("tr"));
		if (rows_before.size()==rows_after.size()+1) check = true;
		return check;
		
	}
	public boolean TableResult(String code, String name, String note) {
		boolean check = false;
		WebElement htmltable=driver.findElement(By.xpath("//div[@class='pt-4']//app-table"));
		List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
		List<WebElement> columns=rows.get(1).findElements(By.tagName("td"));
		if (code.equals(columns.get(1).getText()) && name.equals(columns.get(2).getText())&& note.equals(columns.get(3).getText())) {
			check = true;
		}
		return check;
	}
	@BeforeMethod
	public void setUp(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url_login);
	}
	@Test
	public void Document_type() throws InterruptedException {
		String statusTestcase = "Fail";
		try {
			Create("Loaivanban", "Tên loại văn bản", "Mô tả loại văn bản");
			Thread.sleep(2000);
			boolean check  = TableResult("Loaivanban", "Tên loại văn bản", "Mô tả loại văn bản");
			Assert.assertTrue(check);
			Update("sausua", " sau khi sửa", " sau khi sửa");
			assertTrue(TableResult("Loaivanbansausua", "Tên loại văn bản sau khi sửa", "Mô tả loại văn bản sau khi sửa"));
			assertTrue(Delete());
			Thread.sleep(2000);
			String noti = driver.findElement(By.xpath("//div[@class='ant-notification ant-notification-topRight']")).getText();
			Assert.assertEquals(noti, "Xóa loại văn bản thành công");
			statusTestcase = "Pass";
		} catch (AssertionError e) {
			
			e.getStackTrace();
		}
		System.out.println("Test: Luồng thêm - sửa - xóa: " + statusTestcase);
		Assert.assertEquals(statusTestcase, "Pass");
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
