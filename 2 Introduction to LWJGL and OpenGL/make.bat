@echo off

REM change the following line to your JDK directory
set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_241"

echo Compiling Java File: %1

%JAVA_HOME%\bin\javac -sourcepath . -classpath ./lib/* -d ./build/ %1

IF %errorlevel%==0 (
  echo Program compiled successfully!
) ELSE (
  echo -----------------------------------------------
  echo Program compile errors; aborting run.
  pause
  exit
)
echo Running main() from class: %2 
echo -----------------------------------------------
%JAVA_HOME%\bin\java -classpath .;./build/;./lib/* %2

echo -----------------------------------------------
echo Program finished.
pause
exit
