package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
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

public class MySql09_loginSaucedemo {

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
    public void test01() throws SQLException {

        // Adım 1: Veritabanı bağlantısını kur.
        DatabaseUtility.createConnection();

        // Adım 2: Çalıştırılacak sorguyu hazırla.
        String query = "SELECT username, password FROM saucedemo WHERE id = 1001";

        // Adım 3: Sorguyu çalıştır ve sonuçları al.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);

        String users = null ;
        String passwords= null;
        if (resultSet.next()) { // .next() imleci ilk sonuca taşır ve veri varsa true döner.
            users = resultSet.getString("username");
            passwords = resultSet.getString("password");
            System.out.println("Veritabanından Bulunan Username: " + users);
            System.out.println("Veritabanından Bulunan Password: " + passwords);
        }

        // Adım 5: Veritabanı kaynaklarını kapat.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();


        driver.get("https://www.saucedemo.com/");

        WebElement usernameBox = driver.findElement(By.xpath("//input[@id='user-name']"));
        usernameBox.sendKeys(users);


        WebElement passwordBox = driver.findElement(By.xpath("//input[@id='password']"));
        passwordBox.sendKeys(passwords);


        WebElement submitButton = driver.findElement(By.xpath("//input[@id='login-button']"));
        submitButton.click();

        // Driver'ın kontrolünü açık olan alert'e geçirir ve "Tamam" (OK) butonuna tıklar.

        ((JavascriptExecutor) driver).executeScript("window.oncontextmenu = null;");



    }
        }
























