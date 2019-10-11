package shell;

import apps.AppEntry;
import java.io.IOException;

class ShellManager implements Runnable{

  public void run(){
    scanApps();
  }

  private void scanApps(){
    while(true){
    try{
      Thread.sleep(1000);
      ShellCommand.getProcs();
      AppEntry.checkEntries();
      AppEntry.printEntries(); //for debugging

    } catch(InterruptedException | IOException e){
        e.printStackTrace();
      }

    }
  }
  ShellManager(){

  }
}
