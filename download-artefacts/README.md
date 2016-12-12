# Artefact downloader
The artefact downloader project allows you to easily download any build artefacts (as well as their dependencies, transitively).
The artefacts to be downloaded are configured as dependencies in the `build.gradle` file (just add any artefacts you need there).

To run the downloader, you need [Gradle](https://gradle.org/gradle-download/).
Usage:

    gradle clean download

The artefacts will then be downloaded into the `build` folder.