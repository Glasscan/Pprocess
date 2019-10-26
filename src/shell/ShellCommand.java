package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommand {
  private final static String command = "powershell.exe  " +
  "get-process | where {$_.MainWindowTitle} | select Description, ProcessName, CPU | Format-List";

  private static Process process;
  private static InputStreamReader in;
  static BufferedReader out;

  public static void start() {

    ShellManager manager = new ShellManager();
    new Thread(manager, "Shell Manager").start();
    
  }

  static void runCommand() throws IOException {
    process = Runtime.getRuntime().exec(command);
    //retrieve the output from stdout/inputStream
    //bytes to chars
    in = new InputStreamReader(process.getInputStream());
    out = new BufferedReader(in);
  }

  static void closeStreams () throws IOException{
    process.getOutputStream().close();
    in.close();
    out.close();
  }
}
