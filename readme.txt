How to build source initially

Assuming SOURCE_HOME is the directory containing this readme.txt

1. Copy build.properties.glassfish to build.properties
2. Edit build.properties and set jsf.build.home to SOURCE_HOME
3. Make sure JAVA_HOME is set and points to a JDK8 install 
   e.g. on Ubuntu put JAVA_HOME=/opt/jdk8 in /etc/environment 
4. From SOURCE_HOME run (on the commandline) ant main clean main

The jsf-api.jar will be in SOURCE_HOME/jsf-api/build/lib and jsf-impl.jar will be in SOURCE_HOME/jsf-ri/build/lib.



How to make changes using Eclipse

1. Make changes as needed in .java files, but note that the Eclipse compiled result in SOURCE_HOME/bin must be ignored
2. From SOURCE_HOME run (on the command line) ant clean main

The jsf-api.jar will be in SOURCE_HOME/jsf-api/build/lib and jsf-impl.jar will be in SOURCE_HOME/jsf-ri/build/lib.



How the parent repository for this SVN mirror was created

1. git svn clone -T trunk https://svn.java.net/svn/mojarra~svn
2. cd mojarra~svn/
3. git remote add github git@github.com:javaeekickoff/mojarra.git
4. git push github master



How to update the mirror from the SVN checkout

1. git svn rebase
2. git push github master