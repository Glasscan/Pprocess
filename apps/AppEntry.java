package apps;

import java.util.ArrayList;
import client.Client;

public class AppEntry{
  public static ArrayList<AppEntry> entryList = new ArrayList<AppEntry>();
  String description;
  String processName;
  double initial_cpu_time; //necessary for tracking total session CPU Time
  double cpu_time;
  long application_start_time;
  Boolean renew;

  public String getDesc(){
    return this.description;
  }

  public String getProcName(){
    return this.processName;
  }

  public double getInitialCPUTime(){
    return this.initial_cpu_time;
  }

  public double getCPUTime(){
    return this.cpu_time;
  }

  public long getSessionTime(){ //in seconds
    return Math.abs(System.nanoTime()/1000000000 - application_start_time);
  }

  public void resetTime(){ //after an update, must reset the session times
    this.initial_cpu_time = this.cpu_time;
    this.application_start_time = System.nanoTime()/1000000000;
  }

  public AppEntry(){
  }

  public AppEntry(String desc, String procName, double cpuTime){
    this.description = desc;
    this.processName = procName;
    this.cpu_time = initial_cpu_time = cpuTime;
    this.application_start_time = System.nanoTime()/1000000000;
    this.renew = true;
  }

  public static void addEntry(AppEntry entry){
    entryList.add(entry);
  }

  public static void printEntries(){
    System.out.printf("%-30s %-30s  %-10s  %-10s \n",
    "|Process Name|", "|Description|", "|CPU Time|", "|Session Time|");
    for(int i = 0; i < entryList.size(); i++){
      System.out.printf("%-30.30s %-30.30s  %-10s  %-10s \n",
      entryList.get(i).getProcName(),
        entryList.get(i).getDesc(),
           entryList.get(i).getCPUTime() - entryList.get(i).getInitialCPUTime(),
             entryList.get(i).getSessionTime());
    }
    System.out.println("-----------------------------------------");
  }

  public static Boolean containsEntry(String procName, String desc, double cpuTime){
    for(int i = 0; i < entryList.size(); i++){
      AppEntry entry = entryList.get(i);
      if(entry.processName.equals(procName) && entry.description.equals(desc)){
        entry.renew = true;
        entry.cpu_time = cpuTime;
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
