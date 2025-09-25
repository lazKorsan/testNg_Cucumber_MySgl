package MySglSorgulari;

import utilities.DatabaseUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySql07_addTable {

    public static void main(String[] args) throws Exception {


            DatabaseUtility.createConnection();


            String query = "CREATE TABLE IF NOT EXISTS xyzt ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "username VARCHAR(50) NOT NULL UNIQUE,"
                    + "password VARCHAR(50) NOT NULL)";

            // 3. Sorgu çalıştırma
            Connection connection = DatabaseUtility.getConnection();
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(query);

            System.out.println((result == 0) ? "Tablo zaten var" : "Tablo oluşturuldu");


        }
    }

