# Releasing
1. Set the `ReleaseVersion` `svn` property on the root directory
2. Update the version in the pom.xml
3. Build the release files
4. Add all changes to the changelist
5. Commit and push to server
6. Go to `https://josm.openstreetmap.de/jenkins/job/Nexus-JMapViewer/build` and enter the `${VERSION}` number.

Sample script for steps 1-5:
```bash
# Set the release version
$ export VERSION="2.20"
# ant reads this property to set the build version information
$ svn propset ReleaseVersion ${VERSION} .
# mvn package requires trivial changes in Jenkins at time of writing
$ ant all
# The Jenkins release script extracts this and uses JMapViewer.jar and JMapViewer_src.jar
$ svn add releases/${VERSION}/JMapViewer-${VERSION}.zip
# Update the maven version
$ mvn release:update-versions -DdevelopmentVersion=$(svn propget ReleaseVersion .)-SNAPSHOT
# Commit the files
$ svn commit pom.xml releases/${VERSION}/JMapViewer-${VERSION}.zip
```