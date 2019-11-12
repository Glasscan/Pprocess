package main.apps;

public class AppEntry{
  //public static final ArrayList<AppEntry> entryList = new ArrayList<>();
  private final String description;
  private final String processName;
  private double initial_cpu_time; //necessary for tracking total session CPU Time
  private double cpu_time;
  private long application_start_time;
  private boolean renew;

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

  boolean suspendEntry(){ return !this.renew; }

  void willRenew(){ this.renew = true;}

  void doNotRenew(){ this.renew = false;}

  void setCPU_Time(double time){ this.cpu_time = time;}

  public void resetTime(){ //after an update, must reset the session times
    this.initial_cpu_time = this.cpu_time;
    this.application_start_time = System.nanoTime()/1000000000;
  }

  public AppEntry(String procName, String desc, double cpuTime){
    this.processName = procName;
    this.description = desc;
    this.cpu_time = initial_cpu_time = cpuTime;
    this.application_start_time = System.nanoTime()/1000000000;
    this.renew = true;
  }

//for debugging

}
