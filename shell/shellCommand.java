package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import apps.AppEntry;

public class ShellCommand {
  private static Boolean running = false;

  public static void getProcs() throws IOException {

    String command = "powershell.exe  " +
      "get-process | where {$_.MainWindowTitle} | select Description, ProcessName | Format-List";

    //excute command in seperate process
    Process proc = Runtime.getRuntime().exec(command); //IOException

    //retrieve the output from stdout/inputStream

    //bytes to chars
    InputStreamReader in = new InputStreamReader(proc.getInputStream());
    BufferedReader out = new BufferedReader(in);

    String line;
    String formatLine[];

    Stack<String> descriptions = new Stack<String>();
    Stack<String> processNames = new Stack<String>();

    while((line = out.readLine()) != null){
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
      else
        System.out.println("Something went wrong...");
    }

    if(descriptions.size() != processNames.size())
      System.out.println("Something is amiss...");

    while(!descriptions.isEmpty() && !processNames.isEmpty()){
      AppEntry newEntry = new AppEntry(descriptions.pop(), processNames.pop());
      if(!AppEntry.containsEntry(newEntry.getProcName(), newEntry.getDesc())){
        AppEntry.addEntry(newEntry); //do not add duplicate entries
      }
    }

    proc.getOutputStream().close();
    out.close();

    if(!running){
      ShellManager manager = new ShellManager();
      new Thread(manager).start();
      running = true;
    }

  }
}
