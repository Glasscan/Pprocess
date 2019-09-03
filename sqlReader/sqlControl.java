package sqlReader;

import java.io.FileInputStream;
import java.io.IOException;

import java.sql.*;
import javax.sql.DataSource;
import java.util.Properties;

public class sqlControl{
  private static String user = "";
  private static String password = "";
  private static String db_url = "";

  public static void setup(){

    try{
      Properties DBProps = new Properties();
      FileInputStream in = new FileInputStream("sqlReader/db.properties");
      DBProps.load(in);

      sqlControl.user = DBProps.getProperty("MYSQL_DB_USERNAME");
      sqlControl.password = DBProps.getProperty("MYSQL_DB_PASSWORD");
      sqlControl.db_url = DBProps.getProperty("MYSQL_DB_URL");

      in.close();
    }
    catch(IOException e){
        e.printStackTrace();
        System.out.println("Could not setup DB properties.");
    }

  }

  public static void start(){

  /*  try{
      DriverManager.registerDriver(
        (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
      );
    }
    catch(ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e){
      e.printStackTrace();
    }*/
    setup();
    String myURL = "jdbc:mysql://localhost:3306/test";
    try {
        Connection con = DriverManager.getConnection(
          sqlControl.db_url, sqlControl.user, sqlControl.password);
        Statement stmt = con.createStatement();

        String myQuery = "SELECT * FROM dogs";
        ResultSet rs = stmt.executeQuery(myQuery);
        while(rs.next())
          System.out.println(rs.getString(1));
        con.close();
       }
       // Handle any errors that may have occurred.
       catch (SQLException e) {
           e.printStackTrace();
       }
    }
}
