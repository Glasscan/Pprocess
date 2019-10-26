javac -cp "C:\javalib\mysql-connector-java-8.0.16.jar;." src/client/*.java src/sqlReader/*.java src/apps/*.java src/shell/*.java^
  && java -cp "src;C:\javalib\mysql-connector-java-8.0.16.jar" client.Main^
   || EXIT 1