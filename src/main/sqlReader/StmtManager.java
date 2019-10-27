package main.sqlReader;

//import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.sql.*;

class StmtManager implements Runnable{
  private final BlockingQueue<Query> statements;
  private final Connection con; //connection must be passed on
  private static boolean flag = true;

  public void run() {
    try {
      while (flag || !statements.isEmpty()) {
        consume(statements.take()); //remove Queue item
      }
      System.out.println("Shutting down Statement Manager...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void consume(Query query) {
    String statement = query.getStatement();
    QueryType type = query.getType();
    try{
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
    }catch (SQLException e) {e.printStackTrace();}
  }

  static void flipFlag(){
    flag = !flag;
  }

  StmtManager(Connection con) {
    this.con = con;
    statements = sqlControl.statements;
  }
}
