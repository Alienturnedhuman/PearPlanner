# RaiderPlanner Development Toolchain

This document outlines the recommended toolchain for developers wishing to contribute to the RaiderPlanner project.

The recommendations are based on the tools used by the instructor. You are welcome to use whatever tools you prefer, but you will be on your own in terms of help and support for problems with alternative tools.

The basic tools consist of a JDK, the Eclipse IDE, and the Git version control system.

## JDK

If you are using Linux, then you likely have OpenJDK already installed. If not, then install it with this command or something similar:

```
sudo apt-get install openjdk-8-jdk
```

If you are using Windows or Mac and your system does not have Java installed on it. You will need to [download an installer package](http://www.oracle.com/technetwork/java/javase/downloads/index.html) from Oracle.

Whether you install via a package manager on Linux or via an Oracle installer on Windows or Mac, make sure that you install a **Java development kit (JDK)**. A Java runtime environment (JRE) will not provide all the necessary tools.

After you have installed the JDK, you should be able to run the command `javac -version` and see the version of the specific JDK you installed:

```
user@debian:~$ javac -version
javac 1.8.0_151
```

or

```
C:\>javac -version
javac 1.8.0_151
```

If you see an error message, then you either did not install the correct package, or your system configuration requires adjustment:

```
user@debian:~$ javac -version
bash: javac: command not found
```

or

```
C:\>javac -version
Can't recognize 'javac -version' as an internal or external command, or batch script.
```

## IDE

> NOTE: The instructor of the course uses Eclipse. Students have mostly used Eclipse, though students in the past have used NetBeans or IntelliJ IDEA if they were more comfortable with those IDEs. You are welcome to use any IDE that you like, but if you encouter problems the instructor will not be able to assist you in resolving them.

Download and install the latest version of the [Eclipse IDE](https://www.eclipse.org).

Additionally, using the `Help -> Install New Software...` menu option and/or `Help -> Eclipse Marketplace...`, install at a minimum the following plugins:

 * Gradle BuildShip
 * Data Tools Platform SDK/Data Tools Platform Extender SDK
 * CheckStyle
 * FindBugs

Eclipse is a rather complex piece of software, as is any IDE. If you are not familiar with it then it is recommended that you read these articles:

 * [Eclipse IDE - Tutorial](http://www.vogella.com/tutorials/Eclipse/article.html)
 * [Eclipse Shortcuts - Tutorial](http://www.vogella.com/tutorials/EclipseShortcuts/article.html)

If you intend to work on any database/JDBC aspects of the project, then this article will be helpful:

 * [Managing databases with Eclipse and the Database Tools - Tutorial](http://www.vogella.com/tutorials/EclipseDataToolsPlatform/article.html)

## Git

While it is possible to perform most Git tasks through the GUI provided by your IDE, there are occasions when it is necessary to use the Git command line tool in order to perform a task more efficiently or to resolve a problem. It is best to install and configure Git prior to beginning project work.

If you are using Linux, then you likely have Git already installed. If not, then install it with this command or something similar:

```
sudo apt-get install git
```

If you are using Windows or Mac your system most likely does not have Git installed on it. You will need to [download an installer package for Windows](http://git-scm.com/download/win) or [download an installer package for Mac](http://git-scm.com/download/mac). Note that chapter 1 of the [Pro Git Book](https://git-scm.com/book/en/v2) covers installing and setting up Git.

After you have installed Git, you should be able to run the following command:

```
user@debian:~$ git --version
git version 2.11.0
```

or

```
C:\>git --version
git version 2.11.0
```

If you see an error message, then you either did not install the correct package, or your system configuration requires adjustment:

```
user@debian:~$ git --version
bash: git: command not found
```

or

```
C:\>git --version
Can't recognize 'git --version' as an internal or external command, or batch script.
```

If you are unfamiliar with Git, then it is strongly recommended that you read the [Pro Git Book](https://git-scm.com/book/en/v2), chapters 1-3, and 5-6. (Incidentally, this is assigned reading for the first week of class.)

Additionally, the GitHub help site provides several informative articles on [setting up Git](https://help.github.com/categories/setup/).

**WARNING**: Make sure that you have properly configured your system to [deal with line endings](https://help.github.com/articles/dealing-with-line-endings/). Not every developer on the project works from the same platform, so you must understand this issue and ensure your system is configured properly. Failure to properly configure your system may result in your contributions being rejected.

