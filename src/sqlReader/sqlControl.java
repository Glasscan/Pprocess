package sqlReader;

import apps.AppEntry;

import java.io.FileInputStream;
import java.io.IOException;

import java.sql.*;

import java.util.Scanner; //temporary for testing
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class sqlControl{
  private static String user = "";
  private static String password = "";
  private static String db_url = "";
  static final BlockingQueue<Query> statements = new LinkedBlockingQueue<>();


  private static void setup(){

    try{
      Properties DBProps = new Properties();
      FileInputStream in = new FileInputStream("src/sqlReader/db.properties");
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
    Scanner newSTMT = new Scanner(System.in); //remove this later
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
        StmtManager manager = new StmtManager(con);
        new Thread(manager, "Statement Manager").start();

        String queryStmt;
        while(true){ //currently requires input rather than automatic
          queryStmt = newSTMT.nextLine();
          if(queryStmt.length() < 6) continue; //so it doesn't break
          Query myQuery = new Query(queryStmt);
          statements.put(myQuery);
          if(queryStmt.equals("exodus")) break; //temporary for debugging
        }

        updateOnExit();
        StmtManager.flipFlag();
        shell.ShellManager.flipFlag();
        Thread.sleep(1000); //add a buffer
        con.close();
      }
       catch (SQLException | InterruptedException e) {
           e.printStackTrace();
       }

    }

    private static void updateOnExit(){
      AppEntry.entryList.forEach(x -> {
        try{
          statements.put(Query.newUpdateQuery(x));
        } catch (InterruptedException e) {e.printStackTrace();}
      });
    }
}
