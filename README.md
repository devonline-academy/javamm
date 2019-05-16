> README.md tutorial:
>
> https://guides.github.com/features/mastering-markdown/
>
> https://help.github.com/categories/writing-on-github/

# Setup

The **javamm** project requires the following environment variables:

#### Required variables:
 
- **JAVA_HOME** - the path to the OpenJDK folder (From https://jdk.java.net/11/);

#### Optional variables:

- **JDK_WIN_JMODS** - the path to the jmods folder for Java Windows SDK (From https://jdk.java.net/11/);
- **JDK_LIN_JMODS** - the path to the jmods folder for Java Linux SDK (From https://jdk.java.net/11/);
- **JDK_MAC_JMODS** - the path to the jmods folder for Java MacOS SDK (From https://jdk.java.net/11/);

# Build instructions

To clone git repository run:

~~~~
git clone --progress -v "https://github.com/devonline-academy/javamm" "javamm"
~~~~
-----------------------

To build distributions run:

~~~~
mvn clean package
~~~~
-----------------------

To generate project report run:

~~~~
mvn clean install site site:stage
~~~~
-----------------------