package apps;

import java.util.ArrayList;

public class AppEntry{
  public static final ArrayList<AppEntry> entryList = new ArrayList<>();
  private final String description;
  private final String processName;
  private double initial_cpu_time; //necessary for tracking total session CPU Time
  private double cpu_time;
  private long application_start_time;
  private Boolean renew;

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

//for debugging
  public static void printEntries(){
    System.out.printf("%-30s %-30s  %-10s  %-10s \n",
    "|Process Name|", "|Description|", "|CPU Time|", "|Session Time|");
    for(AppEntry entry : entryList){
      System.out.printf("%-30.30s %-30.30s  %-10s  %-10s \n",
      entry.getProcName(), entry.getDesc(), entry.getCPUTime() - entry.getInitialCPUTime(), entry.getSessionTime());
    }
    System.out.println("-----------------------------------------");
  }

  public static Boolean containsEntry(String procName, String desc, double cpuTime){
    for(AppEntry entry : entryList) {
      if(entry.processName.equals(procName) && entry.description.equals(desc)){
        entry.renew = true;
        entry.cpu_time = cpuTime; //update the CPU Time
        return true;
      }
    }
    return false;
  }

  public static void checkEntries(){ //remove all entries that are no longer seen by the shell
    entryList.removeIf((n) -> (!n.renew));
    clearRenew();
  }

  private static void clearRenew(){
    entryList.forEach((x) -> x.renew = false);
  }

  public static void clearEntries(){
    entryList.clear();
  }
}
