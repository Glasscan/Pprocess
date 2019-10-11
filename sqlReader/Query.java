package sqlReader;

public class Query {
  String statement;
  QueryType type;

  public Query(String stmt){
    this.statement = stmt;
    this.type = getType(stmt);
  }

  public String getStatement(){
    return statement;
  }

  public QueryType getType(){
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

  public Query(){

  }
}

enum QueryType {
  UPDATE, //INSERT, UPDATE, DELETE, etc
  EXECUTE //SELECT
}
