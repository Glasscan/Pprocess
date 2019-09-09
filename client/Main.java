package client;

import sqlReader.sqlControl;
import shell.ShellCommand;
import java.io.IOException;

public class Main{
  public static void main(String[] args) throws IOException{

    //Client.GUI(); //work on this last
    ShellCommand.getProcs();
    sqlControl.start();

  }
}
