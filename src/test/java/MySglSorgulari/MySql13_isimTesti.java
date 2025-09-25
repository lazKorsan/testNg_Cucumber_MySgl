package MySglSorgulari;

import org.testng.annotations.Test;
import utilities.DatabaseUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySql13_isimTesti {

    ResultSet resultSet ;

    @Test
    public void isimSorgusu() throws SQLException {

        try {
            DatabaseUtility.createConnection();
            System.out.println("1");

        }catch (Exception e){
            System.out.println("asdfg");
        }

        String query = "SELECT username from saucedemo ;";
        try {
            DatabaseUtility.executeQuery(query);
            System.out.println("2");

        }catch (Exception e){
            System.out.println("2 calismasi");
        }


try {
    resultSet = DatabaseUtility.resultSet;
    System.out.println("3");
}catch (Exception e){
    System.out.println("3 calismasi");
}
        while (resultSet.next()) {
            System.out.println("4");
            // resultSet.getString("username"), imlecin o an bulunduğu satırdaki "username" sütununun değerini String olarak alır.
            // Alınan kullanıcı adı konsola yazdırılır.
            System.out.println(resultSet.getString("username"));
            System.out.println("5");        }

    }

    @Test
   public void test02() throws SQLException {
        DatabaseUtility.createConnection();
       // String query = "select name from cron_schedules;";

        DatabaseUtility.executeQuery("select name from cron_schedules;");

        resultSet = DatabaseUtility.resultSet;

        List<String> userNamesList = new ArrayList<>();

        while (resultSet.next()) {
            userNamesList.add(resultSet.getString("name"));

        }

        System.out.println(userNamesList);
        DatabaseUtility.closeConnection();



    }
}
