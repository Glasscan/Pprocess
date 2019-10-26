package client;

import sqlReader.sqlControl;
import shell.ShellCommand;

public class Main{
  public static void main(String[] args) {

    //Client.GUI(); //work on this last
    ShellCommand.start();
    sqlControl.start();

  }
}
