package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class WebAutomationExample {

    WebDriver driver;

    // Bu metot, test sınıfındaki tüm testlerden önce bir kez çalışır.
    @BeforeClass
    public void setUp() {
        // WebDriverManager, sisteminize uygun Chrome sürücüsünü otomatik olarak indirir ve kurar.
        WebDriverManager.chromedriver().setup();

        // Yeni bir Chrome tarayıcı penceresi açar.
        driver = new ChromeDriver();

        // Tarayıcının elementleri bulmak için beklemesi gereken maksimum süreyi ayarlar.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        // Tarayıcı penceresini tam ekran yapar.
        driver.manage().window().maximize();
    }

    // Bu metot, bir test senaryosunu temsil eder.
    @Test
    public void googleSearchTest() throws InterruptedException {
        // Belirtilen URL'e gider.
        driver.get("https://www.google.com");

        // Arama kutusunu 'name' özelliğine göre bulur.
        WebElement searchBox = driver.findElement(By.name("q"));

        // Arama kutusuna "Selenium WebDriver" yazar ve Enter tuşuna basar.
        searchBox.sendKeys("Selenium WebDriver" + Keys.ENTER);

        // Sonuçların yüklenmesi için 3 saniye bekler (sadece gösterim amaçlı).
        Thread.sleep(3000);

        // Sayfa başlığının "Selenium WebDriver" içerip içermediğini kontrol eder ve sonucu konsola yazdırır.
        String pageTitle = driver.getTitle();
        System.out.println("Sayfa Başlığı: " + pageTitle);
        assert pageTitle.contains("Selenium WebDriver");
    }

    // Bu metot, test sınıfındaki tüm testler bittikten sonra bir kez çalışır.
    @AfterClass
    public void tearDown() {
        // Açık olan tarayıcı penceresini kapatır ve kaynakları serbest bırakır.
        if (driver != null) {
            driver.quit();
        }
    }
}
