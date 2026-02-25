# Prerequisites
* jdk 24 to view and compile the soources
# Building application
Gradle build system is used for building and project is equipped with gradle-wrapper
* to build use ./gradlew build to build with tests 
# Running application
Application runs in docker container or it can be executed from command line with java,
application needs to be built prior.
* to run the applicaiton in docker: docker compose -f docker-compose.yml up --build
* to run the application from command line: java -jar netgroup-server-0.0.1-SNAPSHOT.jar from build/libs direcotory of netgroup-server module
* application is available from url: http://localhost:8081
* admin username and password are stored in the database
and insecure NoOpPasswordEncoder is used just for demo purposes
the password and username can be changed in import.sql
# Deploying application to server environemnt
* Application is equipped with Dockerfile that builds the docker image
* Image can be run with docker
* Alternatively the application can be run with jre in any server environemnt (linux, win) check the "Running application" section

# Known issues
* add i18n to react views
* add open-api specifications to rest endpoints
* set up React proxy to run UI and server in different containers separately
* fix sonarqube warnings
# Information about application
Application uses JWT token that is stored in httpOnly cookie for authentication.