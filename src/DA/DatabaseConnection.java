package DA;


import java.sql.*;

public class DatabaseConnection {
    private DatabaseConnection(){}

    public static Connection setConnection() throws SQLException {
        Connection conn;
        String url ="jdbc:sqlserver://MSYS3500\\MSSQLPRODUKTIV;databaseName=profiLag; integratedSecurity=true;trustServerCertificate=true";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {

            Class.forName(driver);//.newInstance();
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
