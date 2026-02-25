# Prerequisites
* jdk 24 to view and compile the soources
# Running application
Application runs in docker container 
* to run applicaiton: docker compose -f docker-compose.yml up --build
* application is available from url: http://localhost:8081
# Building application
Gradle build system is used for building and project is equipped with gradle-wrapper
* to build use ./gradlew build to build with tests
# Known issues
* add i18n to react views
* add open-api specifications to rest endpoints
* set up React proxy to run UI and server in different containers separately
# Information about application
Application uses JWT token that is stored in httpOnly cookie for authentication.
