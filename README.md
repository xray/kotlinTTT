# Tic-Tac-Toe (Kotlin)
Kotlin Version 1.3.40 | JDK 11
  
## Requirements
* Java JDK 11 or 12
* Gradle 5.4.1
* PostgreSQL
    * A user `postgres` with no password
   
## Game
* Create the db with `createdb tictactoe`
* From the project folder run `gradle build`
* Next, run `java -jar build/libs/tic_tac_toe.jar`
 
## Tests
How to run the tests:
* Create the test db with `createdb tictactoe_test`
* Run `gradle test`
