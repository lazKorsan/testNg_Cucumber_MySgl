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
import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class MySql03_usernamedenMailSorgusu {

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

    @Test
    public void TC01_findEmailAndSearchInGoogle() throws SQLException, InterruptedException {
        // --- VERİTABANI BÖLÜMÜ ---

        // Adım 1: Veritabanı bağlantısını kur.
        DatabaseUtility.createConnection();

        // Adım 2: Çalıştırılacak sorguyu hazırla.
        String query = "select email from users where username = 'acenk'";

        // Adım 3: Sorguyu çalıştır ve sonuçları al.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);

        // Adım 4: Sonucu bir değişkende sakla.
        String userEmail = null;
        if (resultSet.next()) { // .next() imleci ilk sonuca taşır ve veri varsa true döner.
            userEmail = resultSet.getString("email");
            System.out.println("Veritabanından Bulunan Email: " + userEmail);
        }

        // Adım 5: Veritabanı kaynaklarını kapat.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();

        // --- WEB OTOMASYON BÖLÜMÜ ---

        // Adım 6: E-posta adresi bulunduysa Google'da arama yap.
        if (userEmail != null && !userEmail.isEmpty()) {
            // Google'a git.
            driver.get("https://www.google.com/");

            // Arama kutusunu bul ve bulunan e-posta adresini yaz.
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(userEmail + Keys.ENTER);

            // Arama sonuçlarının gelmesi için kısa bir bekleme (gösterim amaçlı).
            System.out.println("Google'da arama yapıldı. Sayfa başlığı: " + driver.getTitle());
            Thread.sleep(3000);

        } else {
            System.out.println("Veritabanında belirtilen kullanıcı için e-posta bulunamadı.");
        }
    }

    @AfterClass
    public void tearDown() {
        // Test bittikten sonra tarayıcıyı kapat.
        if (driver != null) {
            driver.quit();
        }
    }
}
