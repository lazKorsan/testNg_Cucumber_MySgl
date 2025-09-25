package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SaucedemoLoginTest {

    // Test verilerini doğrudan sınıf içinde sabit (final) olarak tanımlamak en temiz yöntemdir.
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testSuccessfulLogin() {
        // Adım 1: Login sayfasına git
        driver.get(BASE_URL);

        // Adım 2: Kullanıcı adını gir
        driver.findElement(By.id("user-name")).sendKeys(USERNAME);

        // Adım 3: Şifreyi gir
        driver.findElement(By.id("password")).sendKeys(PASSWORD);

        // Adım 4: Login butonuna tıkla
        driver.findElement(By.id("login-button")).click();

        // Adım 5: Başarılı girişi doğrula
        // Başarılı giriş sonrası yönlendirilen sayfanın URL'ini kontrol ediyoruz.
        String expectedUrl = "https://www.saucedemo.com/inventory.html";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Login sonrası URL beklenen gibi değil!");

        System.out.println("Başarılı bir şekilde Saucedemo sitesine giriş yapıldı.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
