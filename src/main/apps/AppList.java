package main.apps;

import main.sqlReader.Query;
import main.sqlReader.sqlControl;

import java.util.ArrayList;
import java.util.List;

public class AppList {
    private static AppList appList = null;
    private static List<AppEntry> entryList;

    private AppList(){
        entryList = new ArrayList<>();
    }

    public static AppList getList(){
        if (appList == null)
            appList = new AppList();
        return appList;
    }

    public static List<AppEntry> getEntryList(){ return entryList;}

    public static void addEntry(AppEntry entry){
        entryList.add(entry);
    }

    public static void printEntries(){
        System.out.printf("%-30s %-30s  %-10s  %-10s \n",
                "|Process Name|", "|Description|", "|CPU Time|", "|Session Time|");
        for(AppEntry entry : entryList){
            System.out.printf("%-30.30s %-30.30s  %-10s  %-10s \n",
                    entry.getProcName(), entry.getDesc(), entry.getCPUTime() - entry.getInitialCPUTime(), entry.getSessionTime());
        }
        System.out.println("-----------------------------------------");
    }

    public static Boolean containsEntry(String procName, String desc, double cpuTime){
        for(AppEntry entry : entryList) {
            if(entry.getProcName().equals(procName) && entry.getDesc().equals(desc)){
                entry.willRenew();
                entry.setCPU_Time(cpuTime); //update the CPU Time
                return true;
            }
        }
        return false;
    }

    public static void checkEntries(){ //remove all entries that are no longer seen by the shell
        for ( AppEntry entry : entryList){
            if(entry.suspendEntry()){
                sqlControl.newStatement(Query.newUpdateQuery(entry)); //upload it first
            }
        }
        entryList.removeIf(AppEntry::suspendEntry); //remove after pushing -> In future, place onto a suspended list
        clearRenew();
    }

    private static void clearRenew(){
        entryList.forEach(AppEntry::doNotRenew);
    }

    public static void clearEntries(){
        entryList.clear();
    }

}
