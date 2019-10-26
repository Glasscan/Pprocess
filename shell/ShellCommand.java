package shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommand {
  final static String command = "powershell.exe  " +
  "get-process | where {$_.MainWindowTitle} | select Description, ProcessName, CPU | Format-List";

  static Process proc;
  static InputStreamReader in;
  static BufferedReader out;

  public static void start() {

    ShellManager manager = new ShellManager();
    new Thread(manager, "Shell Manager").start();
    
  }

  static void runCommand() throws IOException {
    proc = Runtime.getRuntime().exec(command);
    //retrieve the output from stdout/inputStream
    //bytes to chars
    in = new InputStreamReader(proc.getInputStream());
    out = new BufferedReader(in);
  }

  static void closeStreams (Process proc, BufferedReader out) throws IOException{
    proc.getOutputStream().close();
    out.close();
  }
}
