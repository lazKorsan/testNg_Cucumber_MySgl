package MySglSorgulari;

import org.testng.annotations.Test;

import java.sql.*;

public class MySql01_KullaniciSorgusu {

    @Test
    public void TC01() throws ClassNotFoundException, SQLException {
        // Bu test, 'loantech' uygulamasını kullanan kullanıcıların listesini veritabanından çeker ve konsola yazdırır.

        // --- ADIM 1: Veritabanı Sürücüsünü Yükleme ---
        // Java'nın MySQL veritabanı ile iletişim kurabilmesi için gerekli olan JDBC (Java Database Connectivity) sürücüsünü belleğe yükler.
        // 'com.mysql.cj.jdbc.Driver' modern MySQL sürücüsünün sınıf adıdır.
        Class.forName("com.mysql.cj.jdbc.Driver");

        // --- ADIM 2: Veritabanı Bağlantısını Kurma ---
        // Bağlantı için gerekli olan URL, kullanıcı adı ve şifre bilgileri tanımlanır.
        String URL = "jdbc:mysql://195.35.59.18/u201212290_qaloantec"; // Veritabanı sunucu adresi ve şeması
        String username = "u201212290_qaloanuser"; // Veritabanı kullanıcı adı
        String password = "HPo?+7r$"; // Veritabanı şifresi

        // DriverManager, yüklenen sürücüyü kullanarak ve verilen bilgilerle veritabanına bir bağlantı (Connection) oturumu açar.
        Connection connection = DriverManager.getConnection(URL, username, password);

        // --- ADIM 3: SQL Sorgusunu Hazırlama ---
        // Veritabanında çalıştırılacak olan SQL sorgusu bir String değişkenine atanır.
        // Bu sorgu, 'users' tablosundaki tüm kullanıcıların 'username' sütunundaki değerleri seçecektir.
        String query = "select username from users";

        // --- ADIM 4: Sorguyu Çalıştırma ve Sonuçları Alma ---
        // Connection üzerinden bir Statement (ifade) nesnesi oluşturulur. Bu nesne, SQL sorgularını veritabanına göndermek için kullanılır.
        // ResultSet.TYPE_SCROLL_INSENSITIVE: Sonuç kümesinde imlecin ileri-geri hareket ettirilebilmesini sağlar.
        // ResultSet.CONCUR_READ_ONLY: Sonuç kümesinin sadece okunabilir olmasını, yani verilerin değiştirilememesini sağlar.
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        // Hazırlanan SQL sorgusu, statement.executeQuery() metodu ile çalıştırılır.
        // Dönen sonuçlar, satır satır işlenebilen bir ResultSet (sonuç kümesi) nesnesinde saklanır.
        ResultSet resultSet = statement.executeQuery(query);

        // --- ADIM 5: Sonuçları İşleme ---
        // Dönen sonuç kümesindeki (ResultSet) verileri satır satır okumak için bir while döngüsü kullanılır.
        // resultSet.next() metodu, imleci bir sonraki satıra taşır. Okunacak satır olduğu sürece 'true', aksi halde 'false' döner.
        while (resultSet.next()) {
            // resultSet.getString("username"), imlecin o an bulunduğu satırdaki "username" sütununun değerini String olarak alır.
            // Alınan kullanıcı adı konsola yazdırılır.
            System.out.println(resultSet.getString("username"));
        }

        // --- ADIM 6: Bağlantıyı Kapatma ---
        // Veritabanı kaynaklarının (bellek, ağ bağlantısı vb.) boşa çıkması için açılan nesnelerin kapatılması çok önemlidir.
        // Kaynak sızıntısını (resource leak) önlemek için bu işlem mutlaka yapılmalıdır.
        // Kapatma işlemi, genellikle açılış sırasının tersi şeklinde yapılır: önce ResultSet, sonra Statement, en son Connection.
        resultSet.close();
        statement.close();
        connection.close();
    }
}
