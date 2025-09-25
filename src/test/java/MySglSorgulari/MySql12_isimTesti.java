package MySglSorgulari;

import org.testng.annotations.Test;

import java.sql.*;

public class MySql12_isimTesti {

    // Veritabanı bağlantı bilgileri - Lütfen kendi bilgilerinizle güncelleyin
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    @Test
    public void TC_01() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. Adım: Veritabanına bağlan
            System.out.println("Veritabanına bağlanılıyor...");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Bağlantı başarılı!");

            // 2. Adım: Sorguyu çalıştır
            statement = connection.createStatement();
            String query = "select name from cron_schedules;";
            resultSet = statement.executeQuery(query);

            // 3. Adım: Sonuçları işle
            if (resultSet.next()) {
                String userName = resultSet.getString("name");
                System.out.println("Bulunan İsim: " + userName);
            }

        } catch (SQLException e) {
            // Hata durumunda hatayı detaylı olarak yazdır
            e.printStackTrace();
        } finally {
            // 4. Adım: Kaynakları her zaman kapat
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Bağlantı kapatıldı.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
