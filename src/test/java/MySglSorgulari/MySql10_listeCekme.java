package MySglSorgulari;

import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySql10_listeCekme {

    @Test
    public void test01() throws Exception {
        // her sorgu testinde ortak adımlar
        // 1,2,4,6, adımlar ortak fakat
        // sorgu türüne göre değişiklikler arz edebilir.
        //


        // Adım 1: Veritabanı bağlantısını kur.
        DatabaseUtility.createConnection();

        // Adım 2: Çalıştırılacak sorguyu hazırla.
        String query = "SELECT username from saucedemo";

        // Adım 3: Sorguyu çalıştır ve sonuçları al.
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery(query);

        // 4. adım sonuçları listeye kayt etmişlikmişlik
        List<String> userNamesList = new ArrayList<>();

        while (resultSet.next()) {

            userNamesList.add(resultSet.getString("username"));
            // resultSet.getString("username"), imlecin o an bulunduğu satırdaki "username" sütununun değerini String olarak alır.
            // Alınan kullanıcı adı konsola yazdırılır.
            System.out.println(resultSet.getString("username"));
        }

        System.out.println(userNamesList);

        String expectedUserName = "standard_user";
        Assert.assertTrue(userNamesList.contains(expectedUserName),"liste istenen ismi içermiyor");
        // Adım 5: Veritabanı kaynaklarını kapat.
        resultSet.close();
        statement.close();
        DatabaseUtility.closeConnection();


    }
}
