# Contributing
## How to test
* Unit tests: `mvn test`
* Manual testing: `mvn compile exec:java -Dexec.mainClass=org.openstreetmap.gui.jmapviewer.Demo`

## How to report a bug
Please open a ticket at [https://josm.openstreetmap.de/newticket?component=JMapViewer](https://josm.openstreetmap.de/newticket?component=JMapViewer).

## How to submit changes
Open a ticket at [https://josm.openstreetmap.de/newticket?component=JMapViewer](https://josm.openstreetmap.de/newticket?component=JMapViewer).
See [https://josm.openstreetmap.de/wiki/DevelopersGuide/PatchGuide](https://josm.openstreetmap.de/wiki/DevelopersGuide/PatchGuide) for how to create the patch file and submit it.

## Check patch conformance
* `mvn validate`
  * `mvn checkstyle:checkstyle`
* `mvn spotbugs:check` -- this should be done by `mvn validate` as well, but it 
  wasn't possible at time of writing due to existing spotbugs bugs in JMapViewer.

## Ant targets -> Maven targets
* `ant clean` -> `mvn clean`
* `ant build` -> `mvn compile`
* `ant test` -> `mvn test`
* `ant svn_info` -> manual pom.xml editing or `mvn release:update-versions -DdevelopmentVersion=$(svn propget ReleaseVersion .)-SNAPSHOT`
* `ant pack` -> `mvn package` (note: slightly different source filename, doesn't merge source and class jars)
* `ant create_run_jar` -> not migrated (use `mvn exec:java -Dexec.mainClass="org.openstreetmap.gui.jmapviewer.Demo"` instead)
* `ant spotbugs` -> `mvn spotbugs:check`
* `ant checkstyle` -> `mvn checkstyle:checkstyle`
* `ant javadoc` -> `mvn javadoc:javadoc`
* `ant create_release_zip` -> `mvn jar:jar`
* `ant create_source_release_zip` -> `mvn source:jar`
* `ant checkdepsupdate` -> `mvn versions:display-dependency-updates` (note: please try to keep the version information in sync)
