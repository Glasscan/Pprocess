package shell;

import apps.AppEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class ShellManager implements Runnable{
  static volatile boolean flag = true;

  public void run(){
    scanApps();
    return;
  }

  private void scanApps(){

    while(flag){
    try{
      Thread.sleep(3000);
      getProcs();
      AppEntry.checkEntries(); //remove closed applications
      AppEntry.printEntries(); //for debugging

    } catch(InterruptedException | IOException e){
        e.printStackTrace();
      }
    }
    System.out.println("Shutting down Shell Manager...");
    return;
  }

  private void getProcs() throws IOException{

    String line;
    String formatLine[];

    Stack<String> descriptions = new Stack<String>();
    Stack<String> processNames = new Stack<String>();
    Stack<Double> cpuTimes = new Stack<Double>();

    ShellCommand.runCommand();

    while((line = ShellCommand.out.readLine()) != null){
      if(line.isEmpty()) continue; //ignore the whitespace
      formatLine = line.split(":"); //Expect size=2 (second entry may be empty)

      if(formatLine[0].trim().equals("Description")){
        if(formatLine[1].trim().isEmpty())
          descriptions.push("-no description-");
        else
          descriptions.push(formatLine[1].trim());
      }
      else if(formatLine[0].trim().equals("ProcessName")){
        if(formatLine[1].trim().isEmpty())
          processNames.push("-no process name-");
        else
          processNames.push(formatLine[1].trim());
      }
      else if(formatLine[0].trim().equals("CPU")){//assumes cpu time not empty
        cpuTimes.push(Double.parseDouble(formatLine[1].trim()));
      }

      else
        System.out.println("Something went wrong...");
    }

    if(descriptions.size() != processNames.size())
      System.out.println("Something is amiss...");

    while(!descriptions.isEmpty() && !processNames.isEmpty()){
      AppEntry newEntry = new AppEntry(
        descriptions.pop(), processNames.pop(), cpuTimes.pop());
      if(!AppEntry.containsEntry(
          newEntry.getProcName(), newEntry.getDesc(), newEntry.getCPUTime())
        ){
        AppEntry.addEntry(newEntry); //do not add duplicate entries
      }
      else{
        //instead just update the CPU time by doing nothing
      }
    }
  }

  public static void setFlag(boolean value){
    flag = value;
  }

  ShellManager(){

  }
}
