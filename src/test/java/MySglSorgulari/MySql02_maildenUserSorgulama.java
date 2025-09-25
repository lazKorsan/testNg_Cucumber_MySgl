package MySglSorgulari;

import org.testng.annotations.Test;
import utilities.DatabaseUtility; // DatabaseUtility sınıfını projemize dahil ediyoruz.

import java.sql.*;

public class MySql02_maildenUserSorgulama {

    @Test
    public void TC01() throws SQLException {
        // Loantech uygulamasini kullanan kullanicilar arasinda
        // email adresi mcenk@gmail.com olan kullanici oldugunu test edin

        // --- ADIM 1 & 2: Veritabanı Bağlantısını Utility ile Kurma ---
        // Artık sürücü yükleme ve bağlantı bilgilerini manuel olarak yazmıyoruz.
        // Bu işlemlerin tamamı DatabaseUtility sınıfı tarafından merkezi olarak yönetiliyor.
        DatabaseUtility.createConnection();

        // --- ADIM 3: SQL Sorgusunu Hazırlama ---
        String query = "select username, email from users where email = 'mcenkk@gmail.com'";

        // --- ADIM 4: Sorguyu Çalıştırma ve Sonuçları Alma ---
        // Statement nesnesini, Utility'den aldığımız Connection üzerinden oluşturuyoruz.
        // Bu sayede createStatement için özel parametrelerimizi kullanmaya devam edebiliriz.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // Sorguyu çalıştırıp sonuçları alıyoruz.
        ResultSet resultSet = statement.executeQuery(query);

        // --- ADIM 5: Sonuçları İşleme ---
        // Dönen bir sonuç olup olmadığını kontrol etmek ve kullanıcı adını yazdırmak.
        // Örneğin, bir 'Assert' ile kullanıcının varlığını doğrulayabiliriz.
        boolean userFound = false;
        while (resultSet.next()) {
            userFound = true;
            System.out.println("Bulunan Kullanıcı: " + resultSet.getString("username"));
        }
        // Assert.assertTrue(userFound, "mcenkk@gmail.com email adresine sahip kullanıcı bulunamadı!");

        // --- ADIM 6: Kaynakları ve Bağlantıyı Kapatma ---
        // Bu test metodu içinde oluşturduğumuz ResultSet ve Statement nesnelerini kendimiz kapatmalıyız.
        resultSet.close();
        statement.close();

        // DatabaseUtility tarafından açılan genel bağlantıyı yine Utility üzerinden kapatıyoruz.
        DatabaseUtility.closeConnection();
    }
}
