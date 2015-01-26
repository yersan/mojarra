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



How to run tests

1. In the /.m2 directory in the home of the user that runs the test, create a settings.xml file with the following content
(or add the <profile> tag and child content to an existing settings.xml file) 

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    
  <profiles>
    <profile>
      <id>default</id>
      <activation>
          <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <glassfish.cargo.home>GLASSFISH_LOCATION</glassfish.cargo.home>
        <glassfish.patch.home>GLASSFISH_LOCATION</glassfish.patch.home>
      </properties>
   </profile>
  </profiles>

</settings>

2. From SOURCE_HOME run: ant mvn.deploy.snapshot.local

This will build the source jars in addition to the binary jar, and will install these in the local m2 repository.

3. cd into SOURCE_HOME/test and follow the README.txt there





How to update the mirror from the (local) SVN checkout

1. git svn rebase
2. git push github master

This will update the base mirror https://github.com/javaeekickoff/mojarra



How to update the github fork using github 

1. Request https://github.com/javaeekickoff/mojarra/compare/omnifaces:master...javaeekickoff:master
2. Create a pull request for the changes
3. Accept and confirm the pull request