@if "%DEBUG_CMD%" == "" @echo off

set JVM_ARGS="-Xmx512M"
set MODULE_PATH="%OPENJFX_HOME%\lib;lib;modules;."
set ADD_MODULES=--add-modules javafx.controls,javafx.graphics,javafx.fxml

%JAVA_HOME%\bin\java.exe %JVM_ARGS% --module-path %MODULE_PATH% %ADD_MODULES% -m javamm.ide/academy.devonline.javamm.ide.JavammIDELauncher