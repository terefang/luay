set JCMD="javaw"

if exist %~dp0\java\bin\javaw.exe (

set JAVA_HOME="%~dp0\java"
set JCMD="%~dp0\java\bin\javaw.exe"

)

"%JCMD%" --add-opens=java.desktop/com.sun.java.swing.plaf.windows=ALL-UNNAMED %JAVA_OPTS% -jar "%~dpnx0"

exit /B %errorlevel%
