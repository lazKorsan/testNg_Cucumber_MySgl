package MySglSorgulari;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql06_addUser {

    /**
     * Bu test metodu, doğrudan veritabanına yeni bir kullanıcı ekleme işlemini uçtan uca test eder.
     * Adımlar:
     * 1. Benzersiz test verisi (kullanıcı adı, email, şifre) oluşturulur.
     * 2. Düz metin şifre, uygulamanın kullandığı BCrypt algoritması ile hash'lenir.
     * 3. Veritabanına INSERT sorgusu ile yeni kullanıcı, hash'lenmiş şifre ile birlikte eklenir.
     * 4. Ekleme işleminin başarılı olduğu (1 satırın etkilendiği) doğrulanır.
     * 5. Eklenen kullanıcının veritabanından geri okunabildiği ve bilgilerinin doğru olduğu doğrulanır.
     */
    @Test
    public void testAddUserDirectlyToDB() throws SQLException {

        // --- ADIM 1: Test Verisini Hazırlama ---
        // Her test çalıştığında benzersiz veri kullanmak, testin tekrar edilebilirliğini sağlar ve "bu email zaten var" gibi hataları önler.
        long uniqueId = System.currentTimeMillis();
        String username = "direct_user_" + uniqueId;
        String email = "direct_" + uniqueId + "@db.com";
        String plainTextPassword = "MySecurePassword123"; // Bu, kullanıcının gerçek, düz metin şifresidir.

        // Güvenlik gereği, şifreler veritabanına asla düz metin olarak kaydedilmez.
        // BCryptPasswordEncoder, şifreyi tek yönlü bir algoritma ile hash'ler (şifreler).
        // Bu, veritabanı sızsa bile gerçek şifrelerin açığa çıkmasını engeller.
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);

        System.out.println("Oluşturulacak Yeni Kullanıcı: " + username);
        System.out.println("Veritabanına Kaydedilecek Hash'lenmiş Şifre: " + hashedPassword);

        // --- ADIM 2: Veritabanına Ekleme (INSERT) ---
        DatabaseUtility.createConnection();
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement();

        // SQL INSERT sorgusu, Java'nın String.format() metodu ile dinamik olarak oluşturulur.
        // Değerler ('%s') sırasıyla username, email ve en önemlisi HASH'LENMİŞ ŞİFRE ile doldurulur.
        // NOW() fonksiyonu, kayıt ve güncelleme zamanlarını otomatik olarak ayarlar.
        String insertQuery = String.format(
                "INSERT INTO users (username, email, password, created_at, updated_at) VALUES ('%s', '%s', '%s', NOW(), NOW())",
                username, email, hashedPassword
        );

        // executeUpdate(), veritabanında değişiklik yapan (INSERT, UPDATE, DELETE) sorgular için kullanılır.
        // Metot, sorgudan etkilenen satır sayısını bir tamsayı (int) olarak döndürür.
        int rowCount = statement.executeUpdate(insertQuery);

        // --- ADIM 3: Doğrulama (Assertion) ---
        // Test otomasyonunun en kritik adımı, yapılan işlemin sonucunu doğrulamaktır.

        // 1. Doğrulama: Ekleme işleminin başarılı olduğunu teyit et.
        // Başarılı bir INSERT işlemi tam olarak 1 satır eklemelidir.
        Assert.assertEquals(rowCount, 1, "Kullanıcı ekleme sorgusu 1 satır eklemeliydi, ancak sonuç farklı!");
        System.out.println("Doğrulama Başarılı: INSERT sorgusu beklendiği gibi 1 satır ekledi.");

        // 2. Doğrulama: Eklenen verinin bütünlüğünü kontrol et.
        // "Trust but verify" (Güven ama doğrula) prensibiyle, eklediğimiz kullanıcıyı veritabanından geri okuruz.
        String selectQuery = "SELECT * FROM users WHERE email = '" + email + "'";
        ResultSet resultSet = statement.executeQuery(selectQuery);

        // resultSet.next() metodu, bir sonuç bulunup bulunmadığını kontrol eder.
        Assert.assertTrue(resultSet.next(), "Az önce eklenen kullanıcı veritabanında sorguyla bulunamadı!");
        
        // Bulunan kaydın içindeki kullanıcı adının, bizim eklediğimiz kullanıcı adıyla aynı olup olmadığını kontrol ederiz.
        String dbUsername = resultSet.getString("username");
        Assert.assertEquals(dbUsername, username, "Veritabanındaki kullanıcı adı, beklenen değerle eşleşmiyor.");
        System.out.println("Doğrulama Başarılı: Yeni kullanıcı veritabanında bulundu ve bilgileri doğru.");

        // --- ADIM 4: Kaynakları Kapatma ---
        // Veritabanı bağlantılarını ve kaynaklarını kapatmak, performans ve güvenlik için zorunludur.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();
    }
}
