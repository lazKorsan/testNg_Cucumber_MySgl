package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

public class MySql04_username_emailPassword {

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
    public void testEmailVePasswordCekme() throws SQLException {
        // Adım 1: Veritabanı bağlantısını kur.
        DatabaseUtility.createConnection();

        // Adım 2: Çalıştırılacak sorguyu hazırla.
        String query = "SELECT email, password FROM users WHERE username = 'acenk'";

        // Adım 3: Sorguyu çalıştır ve sonuçları al.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);

        // Adım 4: Sonucu bir değişkende sakla.
        String userEmail = null;
        String userPassword = null;
        if (resultSet.next()) { // .next() imleci ilk sonuca taşır ve veri varsa true döner.
            userEmail = resultSet.getString("email");
            userPassword = resultSet.getString("password");
            System.out.println("Veritabanından Bulunan Email: " + userEmail);
            System.out.println("Veritabanından Bulunan Password: " + userPassword);
        }

        // Adım 5: Veritabanı kaynaklarını kapat.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();

        driver.get("https://qa.loyalfriendcare.com");

        WebElement signInButton = driver.findElement(By.xpath("//ul[@id='top_menu']/li/a/i"));
        signInButton.click();


        WebElement mailBox = driver.findElement(By.xpath("//input[@id='email']"));
        mailBox.sendKeys(userEmail+ Keys.ENTER);

        WebElement passwordBox = driver.findElement(By.xpath("//input[@id='password']"));
        passwordBox.sendKeys(userPassword+Keys.ENTER);

        WebElement loginPageSignInButton = driver.findElement(By.xpath("//button[@type='submit']")) ;

        loginPageSignInButton.click();




    }


}
