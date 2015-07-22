How to build source initially
=============================

Assuming SOURCE_HOME is the directory containing this readme.txt

1. Copy build.properties.glassfish to build.properties
2. Edit build.properties and set jsf.build.home to SOURCE_HOME
3. Make sure JAVA_HOME is set and points to a JDK8 install 
   e.g. on Ubuntu put JAVA_HOME=/opt/jdk8 in /etc/environment 
4. From SOURCE_HOME run ONCE (on the commandline) ant main clean main



How to make changes using Eclipse
=================================

1. Make changes as needed in .java files, but note that the Eclipse compiled result in SOURCE_HOME/bin must be ignored
2. From SOURCE_HOME run (on the command line)

   ./build

This will execute the following two commands that can also be used manually: 

ant clean main
ant mvn.deploy.snapshot.local

In SOURCE_HOME/jsf-ri/build/lib/ will be:

javax.faces.jar

In SOURCE_HOME/jsf-ri/build/mvn/target will be:

javax.faces-2.3.0-m03-SNAPSHOT.jar
javax.faces-2.3.0-m03-SNAPSHOT-sources.jar
javax.faces-2.3.0-m03-SNAPSHOT-javadoc.jar
javax.faces-2.3.0-m03-SNAPSHOT-documentation.jar



How the parent repository for this SVN mirror was created
=========================================================

1. git svn clone -T trunk https://svn.java.net/svn/mojarra~svn
2. cd mojarra~svn/
3. git remote add github git@github.com:javaeekickoff/mojarra.git
4. git push github master



How to run tests
================

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

2. From SOURCE_HOME run: 

    ./build

This will build the source jars in addition to the binary jar, and will install these in the local m2 repository.

3. cd into SOURCE_HOME/test 

4. Init the test system ONCE or whenever there are changes to the main JSF version (e.g. m04 becomes m05), or whenever
   the /unit or /util folders have incomming changes: 

   ./init-tests-run-once
   
5. Execute ALL tests that run locally on GlassFish:

   ./run-tests
   
6. ALTERNATIVELY, run specific tests only by enumerating them after the command. Names correspond with the folders found
   in /test. E.g.
   
   ./run-tests javaee8 javaee7 




What steps does "run-tests" take for each test folder?
======================================================

For each folder (e.g. javaee8) where tests are run, the following steps are
performed. If needed they can be executed manually as well (from the designated 
test folder, e.g. from test/javaee8/):

1. Copy updated JSF jar to GlassFish
mvn -N -Pglassfish-patch validate

2. Start GlassFish
mvn -N -Pglassfish-cargo cargo:start

3. Compile test wars and install in .m2
mvn clean install

5. Deploy test wars
mvn -Pglassfish-cargo cargo:redeploy

6. Run tests 
mvn -Pintegration verify

7. Stop GlassFish
mvn -N -Pglassfish-cargo cargo:stop



How to update the mirror from the (local) SVN checkout
======================================================

1. git svn rebase
2. git push github master

This will update the base mirror https://github.com/javaeekickoff/mojarra



How to update the github fork using github 
==========================================

1. Request https://github.com/javaeekickoff/mojarra/compare/omnifaces:master...javaeekickoff:master
2. Create a pull request for the changes
3. Accept and confirm the pull request

Note that github doesn't let you resolve conflicts



How to update the github fork using git locally
===============================================

Do once:
1.  git remote add upstream git@github.com:javaeekickoff/mojarra.git

Then:
2. git fetch upstream
3. git merge upstream/master
4. If there are conflicts: git mergetool
5. git commit
6. git push



How to create changebundle.txt and newfiles.zip to send to upstream Mojarra project
===================================================================================

Do once:
1. Make new dir, e.g. mojarra-cb, cd into that and run: svn checkout https://svn.java.net/svn/mojarra~svn/trunk

Note that this is the third local repo if all steps of this readme have been followed.

2. svn update
3. In the git repo where changes have been made (SOURCE_HOME); git diff --name-only [hash of commit from which bundle needs to be created]~1
4. Copy resulting filenames from previous step to the clean SVN repo created in step 1
5. svn add [new files]
6. ant cb
7. [send changebundle.txt and newfiles.zip to Mojarra or attach them to JIRA issue]
8. clean up repo: svn revert --recursive .
9. rm [new files] (use svn status to see new files)
