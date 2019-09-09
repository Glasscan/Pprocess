package sqlReader;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.sql.*;

import sqlReader.StmtManager;

class StmtManager implements Runnable{
  private final BlockingQueue<String> statements;
  private final Connection con; //connection must be passed on

  public void run() {
    try {
      while (true) {
        consume(statements.take()); //remove Queue item
      }
    } catch (InterruptedException ex) {
      System.out.println(ex);
    }
  }

  void consume(String statement) {
    System.out.println("DEVOURING: " + statement);
    try{
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(statement);
      while(rs.next())
        System.out.println(rs.getString(1));
    }catch (SQLException e) {e.printStackTrace();}
  }


  public static String setStatement(){
    String statement = "";
    statement = "SELECT * FROM dogs";
    return statement;
  }

  StmtManager(BlockingQueue<String> q, Connection con) {
    this.con = con;
    statements = q;
  }
}
