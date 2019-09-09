package sqlReader;

import java.io.FileInputStream;
import java.io.IOException;

import java.sql.*;

import java.util.Scanner; //temporary for testing
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

import sqlReader.StmtManager;


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
        System.out.println("If db.properties does not exist check the readme for more info");
    }

  }

  public static void start(){
    BlockingQueue<String> statements = new LinkedBlockingQueue<String>();
    Scanner dogs = new Scanner(System.in);
  /*  try{
      DriverManager.registerDriver(
        (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
      );
    }
    catch(ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e){
      e.printStackTrace();
    }*/
    setup(); //retrieve database properties

    try {
        Connection con = DriverManager.getConnection(
          sqlControl.db_url, sqlControl.user, sqlControl.password);
        StmtManager manager = new StmtManager(statements, con);
        new Thread(manager).start();

        String myQuery = manager.setStatement();
        while(true){
          statements.put(myQuery);
          myQuery = dogs.nextLine();
          if(myQuery.equals("cats")) break;
        }
        con.close();
       }
       // Handle any errors that may have occurred.
       catch (SQLException | InterruptedException e) {
           e.printStackTrace();
       }
    }

}
