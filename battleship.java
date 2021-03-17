
/**
 
 METROPOLITAN COMMUNITY COLLEGE
 CSIS 222–OBJECT-ORIENTED PROGRAMMING IN JAVA
 Author: Juan Ramon Lepe Manzo
 Student ID: S1448233 
 Date: nov/2020

In this version of the game of battleship, the computer positions random set of ships of various sizes on a twodimensional board. Each square of the board is called a cell. Each ship occupies either a horizontal or vertical
line of cells. The user attempts to guess where the ships are hidden by choosing a cell on the board. If the user’s
guess hits a location occupied by a ship, the program indicates that a ship has been hit. Otherwise, the location
is marked as a miss. If all of the ship's cells have been hit, the ship is marked as sunk. To make the game more
interesting, the user will have a limited number of missiles to sink all of the ships. The game ends when the user
sinks all of the ships (the user wins) or runs out of missiles (the computer wins). For variety, each ship is of a
particular model (battleship, aircraft carrier, rowboat, etc.)
***************
battleship.java: This will be the main file that will run the game. This will call the actiongame.java file to run. 
***************
 **/
public class battleship extends actiongame
{
    // instance variables - replace the example below with your own
    

    /**
     * Constructor for objects of class battleship
     */
    public battleship()
    {
        // initialise instance variables
        
    }

    public static void main(String[] args) {
        
        String selectLevel = "";
        actiongame objGame = new actiongame();
        
        // set parameters game
        objGame.setGame();
        // Set Board
        objGame.boardGame();
        // Star play
        objGame.Attack();
        // Destroy objects
        System.gc();
        //Finish game
        System.exit(0);

    }
    

     
}
