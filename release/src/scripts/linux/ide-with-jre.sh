#! /bin/sh

JVM_ARGS="-Xmx512M"
MODULE_PATH="lib:modules:."

ADD_MODULES="--add-modules javafx.controls,javafx.graphics,javafx.fxml"

jre/bin/java ${JVM_ARGS} --module-path ${MODULE_PATH} ${ADD_MODULES} -m javamm.ide/academy.devonline.javamm.ide.JavammIDELauncher
