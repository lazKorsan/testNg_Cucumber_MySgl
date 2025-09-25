package MySglSorgulari;

import org.testng.annotations.Test;
import utilities.DatabaseUtility;
import utilities.JDBCReusableMethods;

public class MySql11_MethodKullanimi {

    @Test
    public void TC01(){
        DatabaseUtility.createConnection();
        DatabaseUtility.executeQuery("SELECT * FROM users");
        DatabaseUtility.closeConnection();
    }

    @Test
    public void TC02(){
        JDBCReusableMethods.createMyConnection();
        JDBCReusableMethods.executeMyQuery("SELECT * FROM users");
        JDBCReusableMethods.closeMyConnection();
    }
}
