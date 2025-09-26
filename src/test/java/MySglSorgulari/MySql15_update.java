package MySglSorgulari;

import manageQueries.LoantechQueries;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.DatabaseUtility;
import utilities.JDBCReusableMethods;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySql15_update {
   /*

  Feature: US1005 users tablosu soyisim testi

    Scenario: TC05 kullanici soyisimleri buyuk harfe cevirebilmeli

      Given kullanici loantech database'ine baglanir
      When users tablosunda username'i "mabally" olan kaydın soyadini büyük harfe çevirir
      Then Update işlemi sonucunda username'i "mabally" olan kaydın soyisim değerinin büyük harfe dönüştüğünü test eder
      And database baglantisini kapatir

    */
   ResultSet resultSet;
    @Test
    public void TC05() throws SQLException {


        DatabaseUtility.createConnection();
        // < -- ===vvvvvv birinci kısım tabloda var olan veriyi çekmekliklik
       String userNameSorgusu = "";
        //JDBCReusableMethods.executeMyQuery(soyisimSorgusu);
        //JDBCReusableMethods.executeMyQuery(Isimsorgusu);
        resultSet.next(); // ilk satırı atlatıp ikinci değeri almakliklik

        String eskiIsim = resultSet.getString("username");

        // < -- === vvv update sorgusu başlıyor

        // sorgu hazırlanır
        String updateIsimSorgusu =
                LoantechQueries
                        .usersTablosundaSoyismiUpdateEtmeSorgusu(eskiIsim,eskiIsim.toUpperCase());

        // SORGUYU çalıştırmaklıklık
        JDBCReusableMethods.executeMyUpdateQuery(updateIsimSorgusu);

        JDBCReusableMethods.executeMyQuery(updateIsimSorgusu);
        resultSet.next();
        String yeniIsim = resultSet.getString("username");

        Assert.assertEquals(yeniIsim, eskiIsim.toUpperCase(),"dönüşüm başarısız ");


    }
}
