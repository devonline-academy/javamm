#! /bin/sh

JVM_ARGS="-Xmx512M"
MODULE_PATH="modules:."

${JAVA_HOME}/bin/java ${JVM_ARGS} --module-path ${MODULE_PATH} -m javamm.cmd/academy.devonline.javamm.cmd.JavammCMDLauncher $1
