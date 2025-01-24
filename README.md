In mysql-connector-j 9.2.0, the frank framework tests fail when updating a field in an updatable ResultSet.

This repository sets up a simple scenario to reproduce this, by providing a mysql `docker-compose.yml` with the right structure in `init.sql`. When you start the version as committed, you'll see that 
the unit test will work. This test is a representable scenario, similar to the situation in the frank framework. See the [ResultSet2FileSender](https://github.com/frankframework/frankframework/blob/master/core/src/main/java/org/frankframework/jdbc/ResultSet2FileSender.java#L95)
in the Frank!Framework github repository.

If you umcomment the mysql-connector-j version in `pom.xml`, the unit test will fail. Somehow the number of parameters is calculated differently in 9.2.0 compared to 9.1.0, resulting in the following stacktrace:

```
Caused by: java.sql.SQLException: Parameter index out of range (4 > number of parameters, which is 2).
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:121) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:89) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:81) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:55) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.ClientPreparedStatement.checkBounds(ClientPreparedStatement.java:1482) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.ClientPreparedStatement.getCoreParameterIndex(ClientPreparedStatement.java:1497) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.ClientPreparedStatement.getBytesRepresentation(ClientPreparedStatement.java:1249) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.result.UpdatableResultSet.refreshRow(UpdatableResultSet.java:1083) ~[mysql-connector-j.jar:9.2.0]
	at com.mysql.cj.jdbc.result.UpdatableResultSet.updateRow(UpdatableResultSet.java:1215) ~[mysql-connector-j.jar:9.2.0]
	at org.apache.tomcat.dbcp.dbcp2.DelegatingResultSet.updateRow(DelegatingResultSet.java:1959) ~[tomcat-dbcp.jar:10.1.34]
	at org.apache.tomcat.dbcp.dbcp2.DelegatingResultSet.updateRow(DelegatingResultSet.java:1959) ~[tomcat-dbcp.jar:10.1.34]
	at org.frankframework.jdbc.ResultSet2FileSender.processResultSet(ResultSet2FileSender.java:135) ~[frankframework-core-9.1.0-pr-8274.jar:9.1.0-pr-8274]
	at org.frankframework.jdbc.ResultSet2FileSender.executeStatementSet(ResultSet2FileSender.java:95) ~[frankframework-core-9.1.0-pr-8274.jar:9.1.0-pr-8274]
```

The last entry is the entry point in the Frank!Framework code I linked above.
