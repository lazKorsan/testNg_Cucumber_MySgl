package MySglSorgulari;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.DatabaseUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySql14_emailSorgusu {
    ResultSet resultSet;


    //Given kullanici loantech database'ine baglanir
    //    When subscribers tablosundaki "email" bilgilerini sorgular
    //    Then "email" sutununda "ayilmaz@gmail.com" kaydının bulunduğunu test eder
    //    And database baglantisini kapatir

    // 1nci adım database bağla
    @Test
    public void TC01() throws SQLException {
        DatabaseUtility.createConnection();
        String query = "select email from subscribers;";
        DatabaseUtility.executeQuery(query);
        resultSet = DatabaseUtility.resultSet;


        List<String> emailList = new ArrayList<>();
        while (resultSet.next()) {
            emailList.add(resultSet.getString("email"));
        }
        System.out.println(emailList);
        DatabaseUtility.closeConnection();

        Assert.assertTrue(emailList.contains("ayilmaz@gmail.com"));


        try {
            Assert.assertTrue(emailList.contains("ayilmaz@gmail.com"));

        } catch (Exception e) {

            System.out.println("2");
            throw new RuntimeException(e);
        }


        Assert.assertTrue(emailList.contains("ayilmaz@gmail.com"));


    }

    @Test
    public void emailKaydiTesti_KlasikYontem() throws SQLException {
        // 1. Veritabanı bağlantısı ve sorgu çalıştırma
        DatabaseUtility.createConnection();
        // "your_table_name" yerine kendi tablo adınızı yazın
        String query = "select email from subscribers;";
        DatabaseUtility.executeQuery(query);
        resultSet = DatabaseUtility.resultSet;
        // 2. Bayrak (flag) tanımlama
        boolean kayitBulundu = false;
        String arananEmail = "ayilmaz@gmail.com";

        System.out.println("Döngü başlıyor: " + arananEmail + " aranacak...");

        // 3. while döngüsü ile satırları gezme
        while (resultSet.next()) {
            String mevcutEmail = resultSet.getString("email");

            // 4. if bloğu ile kontrol
            if (mevcutEmail.equals(arananEmail)) {
                System.out.println("Kayıt bulundu!");
                kayitBulundu = true; // Bayrağı kaldır
                break; // Aradığımızı bulduk, döngüden çık
            }
        }

        System.out.println("Döngü bitti. Sonuç kontrol ediliyor.");

        // 5. Testin sonucunu doğrulama
        Assert.assertTrue(kayitBulundu, "HATA: '" + arananEmail + "' kaydı veritabanında bulunamadı!");

        // Bağlantıyı kapatmayı unutmayın
        DatabaseUtility.closeConnection();
    }

    @Test
    public void TC03() throws SQLException {

        DatabaseUtility.createConnection();
        // "your_table_name" yerine kendi tablo adınızı yazın
        String query = "select email from subscribers;";
        DatabaseUtility.executeQuery(query);
        resultSet = DatabaseUtility.resultSet;

        boolean kayitBulundu = false;
        String arananEmail = "ayilmaz@gmail.com";

        System.out.println("Döngü başlıyor: " + arananEmail + " aranacak...");

        // 3. while döngüsü ile satırları gezme
        while (resultSet.next()) {
            String mevcutEmail = resultSet.getString("email");

            // 4. if bloğu ile kontrol
            if (mevcutEmail.equals(arananEmail)) {
                System.out.println("Kayıt bulundu!");
                kayitBulundu = true; // Bayrağı kaldır
                break; // Aradığımızı bulduk, döngüden çık
            }
        }

        System.out.println("Döngü bitti. Sonuç kontrol ediliyor.");

        // 5. Testin sonucunu doğrulama
        Assert.assertTrue(kayitBulundu, "HATA: '" + arananEmail + "' kaydı veritabanında bulunamadı!");

        // Bağlantıyı kapatmayı unutmayın
        DatabaseUtility.closeConnection();


    }

}


