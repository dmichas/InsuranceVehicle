package insurance.utilities.database;

import java.sql.*;

public class Database {

    //private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://192.168.33.10/VehicleProject";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin123";

    private Connection con = null;

    public void dbConnect(){
        try{
            con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch(SQLException e){
            System.out.println("**Database connection failed");
        }
    }

    public void dbClose(){
         try{
             con.close();
         }catch(SQLException e){
            e.printStackTrace();
         }
    }

    public Connection getConnection(){
        return con;
    }
}
