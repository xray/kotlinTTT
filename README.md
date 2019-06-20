# Tic-Tac-Toe (Kotlin)
Kotlin Version 1.3.40 | JDK 11
  
## Requirements
* PostgreSQL
    * A user `postgres` with no password
   

## Before Use
* Create two new PostgreSQL DBs with `createdb tictactoe; createdb tictactoe_test`
* From the project folder run `./gradlew jar`
* Next run `./gradlew build`
  
## Usage
* Create two new PostgreSQL DBs with the command `createdb tictactoe; createdb tictactoe_test`
* From the project folder, run `java -jar build/libs/tic_tac_toe.jar`
 
## Tests
How to run the tests:  
* Run `./gradlew test`
