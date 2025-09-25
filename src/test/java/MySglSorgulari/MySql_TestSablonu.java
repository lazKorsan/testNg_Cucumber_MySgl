package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class MySql_TestSablonu {

    // WebDriver nesnesini sınıf seviyesinde tanımlıyoruz ki tüm metotlar erişebilsin.
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // WebDriverManager, sisteminize uygun Chrome sürücüsünü otomatik olarak indirir ve kurar.
        WebDriverManager.chromedriver().setup();
        // Sınıf seviyesindeki driver nesnesini başlatıyoruz.
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }



}
