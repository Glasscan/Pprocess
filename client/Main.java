package client;

import sqlReader.sqlControl;
import shell.shellCommand;
import java.io.IOException;
import userInterface.gui;

public class Main{
  public static void main(String[] args) throws IOException{

    sqlControl.start();
    shellCommand.getProcs();
    //gui.startUI();
  }
}
