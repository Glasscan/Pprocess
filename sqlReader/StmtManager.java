package sqlReader;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.sql.*;

public class StmtManager implements Runnable{
  private final BlockingQueue<Query> statements;
  private final Connection con; //connection must be passed on
  static volatile boolean flag = true;

  public void run() {
    try {
      while (flag) {
        consume(statements.take()); //remove Queue item
      }
      System.out.println("Shutting down Statement Manager...");
      return;
    } catch (InterruptedException ex) {
      System.out.println(ex);
    }
  }

  void consume(Query query) {
    String statement = query.getStatement();
    QueryType type = query.getType();
    try{
      if(statement.equals("exodus")) {
        Thread.sleep(1000); //buffer
        return;
      }
      int i = 1;
      Statement stmt = con.createStatement();
      ResultSet rs;
      if(type == QueryType.EXECUTE){
        rs = stmt.executeQuery(statement);
      }
      else{
        stmt.executeUpdate(statement);
        //System.out.println("cats"); //for debugging
        return;
      }
      ResultSetMetaData rsData = rs.getMetaData();

      while(rs.next()){
        while(i <= rsData.getColumnCount()){
          System.out.print(rs.getString(i) + " ");
          i++;
        }
        i = 1;
        System.out.println();
      }
    }catch (SQLException | InterruptedException e) {e.printStackTrace();}
  }


  static void setFlag(boolean value){
    flag = value;
  }

  StmtManager(BlockingQueue<Query> q, Connection con) {
    this.con = con;
    statements = q;
  }
}
