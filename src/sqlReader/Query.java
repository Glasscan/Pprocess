package sqlReader;

import apps.AppEntry;

class Query {
  private static final int MAX_LENGTH = 40; //max length in the database
  private final String statement;
  private final QueryType type;

  Query(String stmt){
    this.statement = stmt;
    this.type = getType(stmt);
  }

  String getStatement(){
    return statement;
  }

  QueryType getType(){
    return type;
  }

  private QueryType getType(String stmt){
    if(stmt.substring(0,6).toUpperCase().equals("INSERT") ||
        stmt.substring(0,6).toUpperCase().equals("UPDATE") ||
          stmt.substring(0,6).toUpperCase().equals("DELETE") ){
      return QueryType.UPDATE;
    }
    else if(stmt.substring(0,6).toUpperCase().equals("SELECT") ){
      return QueryType.EXECUTE;
    }
    else{
      System.out.println("This heck is this ?: " + stmt.substring(0,5).toUpperCase());
      return QueryType.EXECUTE;
    }
  }



  //build a new select statement from input
  /*
  public static String newSelectQuery(){
    return "It ain't ready yet!";
  }
*/
  //build a new insert/update statement

  static Query newUpdateQuery(AppEntry entry){
    String statement;
    String processName = entry.getProcName();
    String description = entry.getDesc();
    double cpuTime = entry.getCPUTime() - entry.getInitialCPUTime();
    long time = entry.getSessionTime();
    entry.resetTime();
    processName = processName.substring(0, Math.min(processName.length(), MAX_LENGTH));
    description = description.substring(0, Math.min(description.length(), MAX_LENGTH));
    statement = String.format("INSERT INTO " +
      "processes(Process_Name, Description, Total_Time, CPU_Time) " +
      "VALUES('%s', '%s', '%d', '%f') " +
      "ON DUPLICATE KEY UPDATE " +
      "Total_Time = Total_Time + %d, CPU_Time = CPU_Time + %f;"
      , processName, description, time, cpuTime, time, cpuTime);

    return new Query(statement);
  }

  public Query(){
    this.statement = "SELECT * FROM test.processes";
    this.type = QueryType.EXECUTE;
  }
}

enum QueryType {
  UPDATE, //INSERT, UPDATE, DELETE, etc
  EXECUTE //SELECT
}
