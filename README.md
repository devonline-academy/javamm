# Javamm project

Compiler, interpreter, standard library, virtual machine, 
console launcher and simple IDE for javamm programming language.

*Repository of javamm project (http://devonline.academy/javamm)*

## Features

#### Java-- language supports:
- **Complex expressions**;
- **Operations:** *println, var, if, for, while, do while, switch, 
  continue, break, return, simple block, 
  assignment and expression operations*;
- **Developer functions and stack**.

#### Virtual machine contains:
- **Compiler**;
- **Interpreter**.

#### Simple IDE supports:
- **Actions:** new, open, save, exit, undo, redo, format, run, terminate;
- **Features:** code autocomplete.

## Last release

[1.0 release](https://github.com/devonline-academy/javamm/releases/tag/v1.0)

## Build instructions

#### 1. Clone git repository:

~~~~
git clone --progress -v "https://github.com/devonline-academy/javamm" "javamm"
~~~~
-----------------------

#### 2. Download and unzip Open JDK 11 or later for your platform (From https://jdk.java.net/11/)

#### 3. Download and unzip Open JDK 11 or later for all other platforms (From https://jdk.java.net/11/)
***FYI:** This is optional instruction.*

This instruction should be done only if you want to build distributions for all platform (Windows, Linux, Mac OS).

If you want to build distribution for current platform only, just skip this instruction

#### 4. Set environment variables

###### Required variables:
- **JAVA_HOME** - the path to the OpenJDK 11 folder or later (From https://jdk.java.net/11/);

###### Optional variables:

***FYI:** This is optional instruction.*

This instruction should be done only if you want to build distributions for all platform (Windows, Linux, Mac OS)

- **JDK_WIN_JMODS** - the path to the jmods folder for Java Windows OpenJDK 11 or later (From https://jdk.java.net/11/);
- **JDK_LIN_JMODS** - the path to the jmods folder for Java Linux OpenJDK 11 or later  (From https://jdk.java.net/11/);
- **JDK_MAC_JMODS** - the path to the jmods folder for Java MacOS OpenJDK 11 or later  (From https://jdk.java.net/11/);

#### 5. Build distributions

~~~~
mvn clean package
~~~~
-----------------------

#### 6. Generate project reports

~~~~
mvn clean install site site:stage
~~~~
-----------------------


### Readme tutorial

- https://guides.github.com/features/mastering-markdown/
- https://help.github.com/categories/writing-on-github/