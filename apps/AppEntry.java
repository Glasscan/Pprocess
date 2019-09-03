package apps;

import java.util.ArrayList;

public class AppEntry{
  static ArrayList<AppEntry> entryList = new ArrayList<AppEntry>();
  String description;
  String processName;


  public String getDesc(){
    return this.description;
  }

  public String getProcName(){
    return this.processName;
  }

  public AppEntry(){
  }

  public AppEntry(String desc, String procName){
    this.description = desc;
    this.processName = procName;
  }

  public static void addEntry(AppEntry entry){
    entryList.add(entry);
  }

  public static void printEntries(){
    for(int i = 0; i < entryList.size(); i++){
      System.out.println(entryList.get(i).getDesc() + "\t"
        + entryList.get(i).getProcName());
    }
  }

  public static ArrayList<AppEntry> getEntries(){
    return entryList;
  }

  public static void clearEntries(){
    entryList.clear();
  }
}
