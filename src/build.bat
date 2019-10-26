javac -cp ".;C:\javalib\mysql-connector-java-8.0.16.jar" client/*.java sqlReader/*.java apps/*.java shell/*.java^
  && java -cp ".;C:\javalib\mysql-connector-java-8.0.16.jar" client.Main^
   || EXIT 1