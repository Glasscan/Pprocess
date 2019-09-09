package apps;

import java.util.ArrayList;
import client.Client;

public class AppEntry{
  public static ArrayList<AppEntry> entryList = new ArrayList<AppEntry>();
  String description;
  String processName;
  long application_start_time;
  Boolean renew;

  public String getDesc(){
    return this.description;
  }

  public String getProcName(){
    return this.processName;
  }

  public long getSessionTime(){
    return Math.abs(System.nanoTime()/1000000000 - application_start_time);
  }

  public AppEntry(){
  }

  public AppEntry(String desc, String procName){
    this.description = desc;
    this.processName = procName;
    this.application_start_time = System.nanoTime()/1000000000;
    this.renew = true;
  }

  public static void addEntry(AppEntry entry){
    entryList.add(entry);
  }

  public static void printEntries(){
    for(int i = 0; i < entryList.size(); i++){
      System.out.println(entryList.get(i).getDesc() + "\t"
        + entryList.get(i).getProcName() + '\t'
          + entryList.get(i).getSessionTime());
    }
  }

  public static Boolean containsEntry(String procName, String desc){
    for(int i = 0; i < entryList.size(); i++){
      AppEntry entry = entryList.get(i);
      if(entry.processName.equals(procName) && entry.description.equals(desc)){
        entry.renew = true;
        return true;
      }
    }
    return false;
  }

  public void renew(){
    this.renew = true;
  }

  public static void checkEntries(){ //remove all entries that are no longer seen by the shell
    entryList.removeIf((n) -> (n.renew == false));
    clearRenew();
  }

  private static void clearRenew(){
    entryList.forEach((x) -> x.renew = false);
  }

  public static void clearEntries(){
    entryList.clear();
  }
}
