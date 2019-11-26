rem set IGNITE_HOME=c:\utils\apache-ignite-2.7.6-bin
rem set JAVA_OPTS=-Xmx8g -XX:-UseGCOverheadLimit 
rem set JAVA_OPTS=-XX:-UseGCOverheadLimit
c:\utils\jdk1.8.0_221\bin\java.exe -XX:-UseGCOverheadLimit -Xmx6g -jar .\target\TextAnalizer-0.4.war
pause