@if "%DEBUG_CMD%" == "" @echo off

set JVM_ARGS="-Xmx512M"
set MODULE_PATH="lib;modules;."
set ADD_MODULES=--add-modules javafx.controls,javafx.graphics,javafx.fxml

jre\bin\java.exe %JVM_ARGS% --module-path %MODULE_PATH% %ADD_MODULES% -m javamm.ide/academy.devonline.javamm.ide.JavammIDELauncher