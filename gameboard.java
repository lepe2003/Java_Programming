
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
gameboard.java: This file will contain code to build game board, placement of pieces, tracking score, and other code for the game.
***************
 **/

// Import classes
import java.util.Scanner;           
import java.io.*;
import java.util.*;          

public class gameboard
{
    // instance variables 
    static String _MESSAGE[]= new String[99]; //Messagen in the game
    static  int FRAME[] = new int[10]; // 
    static  int ALL_FRAME[][] = new int[2][12];
    
    /**
     * Constructor for objects of class gameboard
     */
    public gameboard()
    {
        // initialise messages
        tableMessage();
    }

    //------------------------
    //Display level on the board 
    //------------------------
    public void showLevelGame(String[] args){
          System.out.println(_MESSAGE[5]);
           for (int i = 0; i < args.length; i++) {
               System.out.println("\t\t -> "+i+ " - "+args[i] + "\r\n");
            }
            // display line on row
            for(int i = 1; i < 50; i++) System.out.printf("-");

    }
      //------------------------
      //Build game board, tracking score and placement of pieces
      //------------------------
      public void displayBoard(String argDescLevelGame, int[] argLevelDetails, String[][] argDashboardRecord, String argColumsLevel, String[][] arg_SHIP_DATA, int argmisslesUsed) 
      {
    
                gameboard gameInstance = new gameboard();
                System.out.println("\t****************************************************************************************");          
                //Display tracking score
                System.out.println("\n\t Level: "+argDescLevelGame);
                System.out.println("\t Board size: "+argLevelDetails[0]+" x "+argLevelDetails[1]);
                System.out.println("\t Missles: "+(argLevelDetails[2]-argmisslesUsed));
    
                System.out.println("\n\t A = Aircraft Carrier(5 cells) - Hits:"+arg_SHIP_DATA[0][8]+"\t D = Destroyer(3 cells) - Hits:"+arg_SHIP_DATA[2][8]+"\t P = Patrol(2 cells) - Hits:"+arg_SHIP_DATA[4][8]+"");
                System.out.println("\t B = Battleship(4 cells) - Hits:"+arg_SHIP_DATA[1][8]+"\t S = Submarine(3 cells) - Hits:"+arg_SHIP_DATA[3][8]+"");
    
                //Build game board (colums, rows, shuts)
                 System.out.print("\n\n\t  \t");
                 
                 //Dsplay numbers on the top 
                 for(int i = 1; i <= argLevelDetails[0]; i++) System.out.printf("%d\t", new Object[] {Integer.valueOf(i) });
                 
                 System.out.print("\n");
                 for (int j = 0; j < argLevelDetails[1]; ++j) {
                     // display column with letters
                     System.out.print("\n\t "+argColumsLevel.charAt(j)+" \t");
                     // display columns, rows and placement of pieces with separator |
                     for(int i = 1; i <= argLevelDetails[0]; i++) System.out.print(" "+((argDashboardRecord[j][i]==null)?" ":argDashboardRecord[j][i])+"|\t");
                     // display next line and tab
                     System.out.print("\n\t   \t");
                     // display line on row
                     for(int i = 1; i < 100; i++) System.out.printf("-");
                 }
       
      }
  
      // Display end message 
      public void endGame(boolean isWin)
      {
          int val = 0;
          if (isWin) val |= 0x1;
          switch (val){
              case 1:
               System.out.println(_MESSAGE[6]);
               break;
              default:
               System.out.println(_MESSAGE[7]+val);
           }
      }
      
       //**************************************************
       //BONUS POINTS AVAILABLE:  +5 to display positions of ships at start of game (for grading)
       //**************************************************
        public void showShip(String[][] _SHIP_DATA,String argColumsLevel)
        {
            
            System.out.println("\n ");          
            System.out.println("\t****************************************************************************************");          
            System.out.println("\tBONUS POINTS AVAILABLE:  +5 to display positions of ships at start of game (for grading)");          
            System.out.println("\t****************************************************************************************\n "); 
            for (int i=0;i<5;i++) 
            System.out.println("\t"+_SHIP_DATA[i][1]+  " - ( "+(_SHIP_DATA[i][3].length()>0?argColumsLevel.charAt(Integer.parseInt(_SHIP_DATA[i][3].split(",", 2)[0]))+_SHIP_DATA[i][3].split(",", 2)[1]:"")
                                                        + " / "+(_SHIP_DATA[i][4].length()>0?argColumsLevel.charAt(Integer.parseInt(_SHIP_DATA[i][4].split(",", 2)[0]))+_SHIP_DATA[i][4].split(",", 2)[1]:"")
                                                        + " / "+(_SHIP_DATA[i][5].length()>0? argColumsLevel.charAt(Integer.parseInt(_SHIP_DATA[i][5].split(",", 2)[0]))+_SHIP_DATA[i][5].split(",", 2)[1]:"")
                                                        + " / "+(_SHIP_DATA[i][6].length()>0? argColumsLevel.charAt(Integer.parseInt(_SHIP_DATA[i][6].split(",", 2)[0]))+_SHIP_DATA[i][6].split(",", 2)[1]:"")
                                                        + " / "+(_SHIP_DATA[i][7].length()>0? argColumsLevel.charAt(Integer.parseInt(_SHIP_DATA[i][7].split(",", 2)[0]))+_SHIP_DATA[i][7].split(",", 2)[1]:"")
                                                        + " )");
        }
        
       //--------------------------------
      // Display levels on the board
      //--------------------------------
      public static void printLevel(String[] array) {
           for (int i = 0; i < array.length; i++) {
               System.out.print(array[i] + " ");
            }
      }
      
      //--------------------------------
      // Display Message on board
      //------------------------------
      public void displayMessage(int numMessage, int pause)
      {
    
        // Display the message of the table
        System.out.println(_MESSAGE[numMessage]);
    
        // make a pause if need to wait
        if (pause==1)
        {
          System.out.println(_MESSAGE[18]);
          Scanner s = new Scanner(System.in);
          s.nextLine();
        }
    
      }
      
      //--------------------------------
      // Table messages 
      //--------------------------------
      private void  tableMessage(){
    
          _MESSAGE[0]="";
          _MESSAGE[1]="\n\t Please enter correct values. ";
          _MESSAGE[2]="\n\t Enter Number Level to Play? ";
          _MESSAGE[3]="\n\t Enter position (Ejample: A1), 'S' display positions or '0' to Exit.";
          _MESSAGE[4]="\n\t Example: A1";
          _MESSAGE[5]="\n\t Levels Availables \n\n";
          _MESSAGE[6]="\n\n\t CONGRATULATIONS!!!!!! YOU WON.!!!";
          _MESSAGE[7]="\n\n\t Sorry loser. Tray again!!";
    
      }
}
