package MySglSorgulari;

import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySql08_tabloyoVeriEkleme {

    public static void main(String[] args) throws Exception {


        DatabaseUtility.createConnection();


        String query = "INSERT INTO saucedemo (id, username, password) VALUES\n" +
                "(1001, 'standard_user', 'secret_sauce'),\n" +
                "(1002, 'locked_out_user', 'secret_sauce'),\n" +
                "(1003, 'problem_user', 'secret_sauce'),\n" +
                "(1004, 'performance_glitch_user', 'secret_sauce'),\n" +
                "(1005, 'error_user', 'secret_sauce'),\n" +
                "(1006, 'visual_user', 'secret_sauce')";

        // 3. Sorgu çalıştırma
        Connection connection = DatabaseUtility.getConnection();
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(query);

        System.out.println((result == 0) ? "Tablo zaten var" : "Tablo oluşturuldu");




    }
}

