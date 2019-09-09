package shell;

import apps.AppEntry;
import java.io.IOException;

class ShellManager implements Runnable{

  public void run(){
    monkey();
  }

  private void monkey(){
    while(true){
    try{
      Thread.sleep(5000);
      ShellCommand.getProcs();
      AppEntry.checkEntries();
      AppEntry.printEntries();
      System.out.println("-----------------------------------------");

    } catch(InterruptedException | IOException e){
      e.printStackTrace();
    }

    }
  }

  ShellManager(){

  }

}
