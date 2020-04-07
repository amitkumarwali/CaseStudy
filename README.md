FX Calculator

Console based FX Conversion application
Highlights -
  - Developed using Spring Boot Framework which has been customized to handled CLI arguments. Java 8 is the java version used.
  - Data required to run the application is stored as part of the 'resources'.
  - Used Prototype and Factory design pattern at appplicable places.
  - Jacoco is used as the Code Coverage tool.
  - Application can be run using 2 ways -
    1.  On successfully completing the maven lifecycle for 'mvn clean package', target folder will have the jar with name 'fx-converter-0.0.1-SNAPSHOT.jar'. Run the command as, ' java -jar fx-converter-0.0.1-SNAPSHOT.jar "AUD 1 in USD" '
    2.  Junits can also be used to test the application.
    3.  Using eclipse - provide the CLI arguments while running the application as 'Java application'
