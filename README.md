# Idx Demo Projects
Demo project/showcase/template, mostly for experiments.

## Demo project: Idx Contacts
Simple CRUD-style demo application, realized with a **Java EE** backend and an **AngularJS** frontend. Can be used as a template- or reference project / technology stack.

## Build
To build this project, you need [Gradle](https://gradle.org/gradle-download/).
Usage:

    gradle build
	
The default build target when executing `gradle` without any targets is `clean build install`.
To generate Javadoc for the whole project, use `gradle aggregateJavadoc`.

### Checking dependency updates
- Use `gradle dependencyUpdates` to check and list available updates for depdendencies
- For keeping the *Node.js* modules/dependencies up-to-date, use the `ncu` [npm-check-updates](https://www.npmjs.com/package/npm-check-updates) command in the frontend project.

