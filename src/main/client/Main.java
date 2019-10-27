package main.client;

import main.sqlReader.sqlControl;
import main.shell.ShellCommand;

class Main{
  public static void main(String[] args) {

    //Client.GUI(); //work on this last
    ShellCommand.start();
    sqlControl.start();

  }
}
