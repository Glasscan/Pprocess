package shell;

import apps.AppEntry;

import java.io.IOException;
import java.util.Stack;

public class ShellManager implements Runnable{
  private static boolean flag = true;

  public void run(){
    scanApps();
  }

  private void scanApps(){

    while(flag){
    try{
      Thread.sleep(3000);
      getProcesses();
      AppEntry.checkEntries(); //remove closed applications
      AppEntry.printEntries(); //for debugging

    } catch(InterruptedException | IOException e){
        e.printStackTrace();
      }
    }
    try {
      ShellCommand.closeStreams();
    } catch (IOException e){e.printStackTrace();}
    System.out.println("Shutting down Shell Manager...");
  }

  private void getProcesses() throws IOException{

    String line;
    String[] formatLine;

    Stack<String> descriptions = new Stack<>();
    Stack<String> processNames = new Stack<>();
    Stack<Double> cpuTimes = new Stack<>();

    ShellCommand.runCommand();

    while((line = ShellCommand.out.readLine()) != null){
      if(line.isEmpty()) continue; //ignore the whitespace
      formatLine = line.split(":"); //Expect size=2 (second entry may be empty)

      switch (formatLine[0].trim()) {
        case "Description":
          if (formatLine[1].trim().isEmpty())
            descriptions.push("-no description-");
          else
            descriptions.push(formatLine[1].trim());
          break;
        case "ProcessName":
          if (formatLine[1].trim().isEmpty())
            processNames.push("-no process name-");
          else
            processNames.push(formatLine[1].trim());
          break;
        case "CPU": //assumes cpu time not empty
          cpuTimes.push(Double.parseDouble(formatLine[1].trim()));
          break;
        default:
          System.out.println("Something went wrong...");
          break;
      }
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
      //instead just update the CPU time by doing nothing

    }
  }

  public static void flipFlag(){
    flag = !flag;
  }

  ShellManager(){

  }
}
