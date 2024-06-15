
package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
      Connection con = null;

    public Connection GetConnectDB() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Project;";
            String user = "sa";
            String password = "1";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connect successfully");
        } catch (Exception e) {
            System.out.println("Cannot connect");
        }
        return con;
    }
}
