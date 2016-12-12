IDX ARTEFACT DOWNLOADER
~~~~~~~~~~~~~~~~~~~~~~~

This project helps downloading the release build artefacts from the build repository.
After a release build has been performed, the artefacts can be downloaded by executing
the following command in this directory:

   mvn clean install -Drelease.version={release-version}

The artefacts are then downloaded to './target/download'.