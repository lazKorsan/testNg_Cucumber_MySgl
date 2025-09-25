package MySglSorgulari;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
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

public class MySql05_createUser_lfc {

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
    public void createUser() throws SQLException, InterruptedException {

        // Adım 1: Veritabanı bağlantısını kur.
        DatabaseUtility.createConnection();

        // Adım 2: Çalıştırılacak sorguyu hazırla.
        String query = "select username,email,password from users Where id = 1";

        // Adım 3: Sorguyu çalıştır ve sonuçları al.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);

        String username = null;
        String email = null;
        String password = null;
        String comfirmPassword = password;

        if (resultSet.next()) { // .next() imleci ilk sonuca taşır ve veri varsa true döner.
            username = resultSet.getString("username");
            email = resultSet.getString("email");
            password = resultSet.getString("password");
            System.out.println("Veritabanından Bulunan Username: " + username);
            System.out.println("Veritabanından Bulunan Email: " + email);
            System.out.println("Veritabanından Bulunan Password: " + password);
        }

        // Adım 5: Veritabanı kaynaklarını kapat.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();

        driver.get("https://qa.loyalfriendcare.com/register");


        WebElement userNameBox = driver.findElement(By.xpath("//input[@id='name']"));
        userNameBox.sendKeys(username);



        WebElement mailBox = driver.findElement(By.xpath("//input[@id='email']"));
        mailBox.sendKeys(email);

        WebElement passwordBox = driver.findElement(By.xpath("//input[@id='password']"));
        passwordBox.sendKeys(password);


        WebElement comfirmPasswordBox = driver.findElement(By.xpath("//input[@id='password-confirm']"));
        comfirmPasswordBox.sendKeys(password);

        WebElement signUpButton = driver.findElement(By.xpath("/html/body/div/div[2]/div/form/button"));
        signUpButton.click();


    Thread.sleep(30000);


    }



}
