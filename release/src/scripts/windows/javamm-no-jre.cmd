@if "%DEBUG_CMD%" == "" @echo off

set JVM_ARGS="-Xmx512M"
set MODULE_PATH="modules;."

%JAVA_HOME%\bin\java.exe %JVM_ARGS% --module-path %MODULE_PATH% -m javamm.cmd/academy.devonline.javamm.cmd.JavammCMDLauncher %1