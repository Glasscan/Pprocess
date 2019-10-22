Pprocess

This is a Windows desktop application that I made to track the usage of
applications (not background processes) that are running or have run on the
system. A list is generated using a power shell command and the info is stored
in a (local) MySQL server. Using the GUI (not yet implemented) one can
retrieve their history of used apps and their total run time as well as when
they were last used. There are plans to include more information such as CPU
time.


The system works as follows:

---apps package---

-AppEntry.java-

Contains the titular class for storing application info which currently includes
the process name, description, and the current session time. The entries are
contained in an ArrayList which can be accessed by the other classes.

*Note that some applications do not include a description.

In addition to some helper functions the file also includes printEntries() for
debugging purposes. The other methods will be talked about when discussing the
shell command.


---client package---

-Client.java-

Meant to provide the GUI. Very WIP.

-Main.java-

Main function to be called; Starts the GUI, the shell thread, and the SQL
thread.


---shell package----

-ShellCommand.java-

Houses all the logic for running the PowerShell command and processing the
collected information. Some functions in the AppEntry.java file help in this
regard, namely the containsEntry() and the various "renew" functions.
containsEntry() is needed to avoid storing duplicates of an application and
renewing is needed for ShellManager.java.

-ShellManager.java-

The shell manager is the thread that is created in ShellCommand.java. It mainly
serves two purposes:
  - To continuously call the getProcs() function of ShellCommand.java.
  - To "renew" or dispose of the entries of the current AppEntry list.
To renew an entry is to make sure that the process is still running. If an
application is no longer running, then it will NOT be renewed and is removed in
the next cycle.


---sqlReader package---

-db.properties-
The properties file should look something like this:

MYSQL_DB_DRIVER_CLASS=com.mysql.cj.jdbc.Driver
MYSQL_DB_URL=jdbc:mysql://localhost:3306/[MYDATABASE]
MYSQL_DB_USERNAME=root
MYSQL_DB_PASSWORD=[MYPASSWORD]


-sqlControl.java-

When the application starts, the setup() routine pulls values from db.properties
which are needed to establish the connection with the MySQL database(localhost).
Once connected, the StmtManager thread is started which continuously accepts
queries until the end of time (or you force shut down).

-StmtManager.java-

The statement manager constantly waits until a new query is available to be
processed. Since there is a need to both send and retrieve data from the
database, it is necessary to utilize both executeQuery() and executeUpdate().
Statement are differentiated using the Query.java file.

-Query.java-

Its only function is to discern between INSERT, UPDATE, DELETE, and SELECT for a
provided statement. It then assigns a "QueryType" which will determine the
function to be used by the statement manager.


-THE MySQL database-
The creation of the table looks like this:

CREATE TABLE mydatabase.processes (
	  Process_Name VARCHAR(40) NOT NULL,
    Description VARCHAR(40) NOT NULL,
    Total_Time INTEGER(10) DEFAULT(0),
    Created TIMESTAMP DEFAULT NOW(),
    Updated TIMESTAMP DEFAULT NOW(),
    CPU_Time INTEGER(10) DEFAULT(0),
    PRIMARY KEY(Process_Name, Description));
