# Mantas Tamonis homework

## To run application
* Checkout the project
* Building application requires maven installation and JDK 1.8
* Build project with maven. In project root directory:

        mvn package

* Copy produced JAR file (from target directory), disc_config.properties and input.txt to one folder
* In terminal (assuming that input.txt is in the same dir):

        java -jar tamonis_homework-1.0.jar input.txt

## To run test

        mvn test

* Code coverage report can be found in target\site\jacoco\index.html