
/*
METROPOLITAN COMMUNITY COLLEGE
CSIS 222–OBJECT-ORIENTED PROGRAMMING IN JAVA
Author: Juan Ramon Lepe Manzo
Student ID: S1448233 
Date: Oct/2020
Instructor: Carol Hull
HOMEWORK 02 – MID TERM PROJECT – COUNTS AS EXAM GRADE
Description: 

1. Build a program that will display the results of a typical bowling game. One game of bowling consists of 10 
 frames, with a minimum score of zero and a maximum of 300. 
3. Each frame consists of two chances to knock down ten pins. 
4. Knocking down all ten pins on your first ball is called a strike, denoted by an X on the score sheet. 
5. If it takes two shots to knock down all ten pins, it’s called a spare, denoted by a /. If, after two shots, at
6. least one pin is still standing, it’s called an open frame

 ----------------------***END OF PROGRAM ***
*/

// Import classes
import java.util.Scanner;           
import java.io.*;
import java.util.*;

//------------------------
// Public Class bowlingGame
//------------------------
public class JuanLepe_HW02
{

      // CONSTANT VARIABLES
    static  int FRAME[] = new int[10];
    static  int ALL_FRAME[][] = new int[2][12];
    static String _MESSAGE[]= new String[99];


  public static void main(String... arg) throws IOException, InterruptedException {

        Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
        String doPlay = "";

        JuanLepe_HW02 gameInstance = new JuanLepe_HW02();
        //Set Messages
        gameInstance.tableMessage();
        // Clean screen
        gameInstance.cleanScreen();

        do {

          // Message Welcome
          gameInstance.displayMessage(6,0);
          // Play game
          doPlay = gameInstance.play(); 
              
        } while(doPlay.toUpperCase().charAt(0) == 'Y');
        
        gameInstance.displayMessage(12,0);
        

  }

  //------------------------
  // Play game
  //------------------------
  private String play() throws IOException, InterruptedException
  {

    Scanner keyIn = new Scanner(System.in);  // new instance Scanner object

    JuanLepe_HW02  gameInstance = new JuanLepe_HW02();

    // Init frames
    for(int i = 0; i < 10; i++) FRAME[i] = 0;
    for(int i = 0; i < 12; i++) ALL_FRAME[0][i] = ALL_FRAME[1][i] = 0;
    

    try {

      // Display score 
      gameInstance.gameScore();

      // Play balls
      for(int i = 0; i < 10; i++) {
  
        boolean strike = false;
        
        System.out.printf(_MESSAGE[7], new Object[] {Integer.valueOf(i + 1)	});

        //Play ball 1
        strike = gameInstance.playBall(i,0);
        //Play ball 2
        strike = (!strike) ? gameInstance.playBall(i,1): false;
        // Display score 
        gameInstance.gameScore();

      }

      // Extra turn
      gameInstance.extraTurn();

      // Update Array
      gameInstance.updateData();

      // Print game score 
      gameInstance.gameScore();

      //Print Score
      gameInstance.displayMessage(24,0);
      int RUNNING_TOTAL= 0;
      for(int i = 0; i < 10; i++) System.out.printf("%d\t", new Object[] {RUNNING_TOTAL=RUNNING_TOTAL+FRAME[i] });

      System.out.printf(_MESSAGE[13]);

      String s = keyIn.next();
      keyIn.nextLine();

      return s;
      
    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Exception thrown  : error_play" + e);
      return "N";      
    }
    
  }

  //------------------------
  // Push balls
  //------------------------
  private boolean playBall(int frame, int ball)
  {
    Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
    JuanLepe_HW02 gameInstance = new JuanLepe_HW02();

    boolean chk = false;
    int Pins = 0;
    String strValue = "";

    try {

        while(!chk) {

            gameInstance.displayMessage(ball,0);
            do {strValue = keyIn.next(); Pins = isNumeric(strValue)? Integer.parseInt(strValue):-1; }while(Pins==-1);

            if(Pins <= 10 && Pins >= 0) {

                //Verify what ball
                switch (ball)
                {
                case 0:    // BALL ONE
                    ALL_FRAME[ball][frame] = Pins;
                    chk = true;
                    if(Pins == 10 ) {
                      gameInstance.displayMessage(8,1); // STRIKE
                      return true;
                    }
                    break;
                case 1:    // BALL TWO
                  if(Pins + ALL_FRAME[ball-1][frame] < 11) {
                    ALL_FRAME[ball][frame] = Pins;

                    if(Pins + ALL_FRAME[ball-1][frame] == 10) 
                      gameInstance.displayMessage(9,1); // SPARE

                    else if(ALL_FRAME[ball-1][frame] < 4 || Pins < 4)
                      gameInstance.displayMessage(10,1); // LOOSER

                      chk = true;
                  }else{
                    gameInstance.displayMessage(11,1); // MORE PINS THAN 10

                  }
                  break;
                case 2: //EXTRA BALL 1
                  ALL_FRAME[0][10] = Pins;
                  chk = true;
                  break;
                case 3: //EXTRA BALL 2 (FRAME 9/BALL 1 AND FRAME 10) = 10
                  ALL_FRAME[0][11] = Pins;
                  chk = true;
                  break;
                case 4: //EXTRA BALL 2 (FRAME 9/BALL 1 AND FRAME 10) != 10
        
                  if(Pins + ALL_FRAME[0][10] < 11)
                  {
                      ALL_FRAME[1][10] = Pins;
                      chk = true;
                  }
                  break;
                case 5: //EXTRA BALL 2 (FRAME 9/BALL 1 AND FRAME 9/BALL 2) = 10

                  ALL_FRAME[0][10] = Pins;
                  chk = true;
                  break;

                } // end switch
            }
        }  
        return false;

    } catch (Exception e) {
        //TODO: handle exception
        System.out.println("Exception thrown  : error_playBall" + e);
        return true;      
    }  



  }

  //------------------------
  // Play extra turns
  //------------------------
  private void extraTurn()
  {

    boolean result = true;
    JuanLepe_HW02 gameInstance = new JuanLepe_HW02();

    
    try{

      if(ALL_FRAME[0][9] == 10) {
  
        result = playBall(0,2); // EXTRA BALL 1
  
        // start frame 10  
        if(ALL_FRAME[0][10] == 10) {
  
          gameInstance.displayMessage(15,0); // STRIKE EXTRA BALL 1
  
          if(ALL_FRAME[0][11] == 10) gameInstance.displayMessage(16,0);  // STRIKE EXTRA BALL 2
            
          result = playBall(0,3); // EXTRA BALL 2
  
        }else {
            result = playBall(0,4); // EXTRA BALL 2
        }// end frame 10
  
  
      }else if(ALL_FRAME[0][9] + ALL_FRAME[1][9] == 10){

        result = playBall(0,5); // EXTRA BALL 2
  
      }  
    }
    catch (Exception e) {
      //TODO: handle exception
      System.out.println("Exception thrown  : error_extraTurn" + e);

    }
    
  }

  //------------------------
  // Update scores
  //------------------------
  private void updateData()
  {

    for(int i = 0; i < 10; i++) FRAME[i] = getFrameScore(i);

  }

  //------------------------
  //Display Scores 
  //------------------------
  private void gameScore() throws IOException, InterruptedException
  {

            // Clean screen
            JuanLepe_HW02  gameInstance = new JuanLepe_HW02();
            gameInstance.cleanScreen();

            gameInstance.displayMessage(6,0);
            gameInstance.displayMessage(14,0);

            //Print Score
             System.out.print("\n\n\t FRAME: \t");
             for(int i = 1; i < 11; i++) System.out.printf("%d\t", new Object[] {Integer.valueOf(i) });
 
             if(ALL_FRAME[0][9] == 10) {
               if(ALL_FRAME[0][10] == 10) 
                gameInstance.displayMessage(19,0);
               else
                gameInstance.displayMessage(20,0);
     
             }else if(ALL_FRAME[0][9] + ALL_FRAME[1][9] == 10)
              gameInstance.displayMessage(21,0); // XTRA

             // Print next line with Ball 1 and 2 values
             gameInstance.displayMessage(22,0); // RESULT
   
             // Display values balls 
             for(int i = 0; i < 10; i++){
               // Verify if ball 1 was strike
                String valueBall1 = (ALL_FRAME[0][i]==10)? "X":Integer.toString(ALL_FRAME[0][i]);
                // Verify if ball 2 was spare
                String valueBall2 = (((ALL_FRAME[0][i]+ALL_FRAME[1][i])==10)&& valueBall1 !="X")? "/": Integer.toString(ALL_FRAME[1][i]);
                System.out.printf("%s-%s\t",valueBall1,valueBall2);            
              };
             // check last frame it was strike   
             if(ALL_FRAME[0][9] == 10)	{
              if(ALL_FRAME[0][10] == 10)
                   System.out.printf("%4d-%4d", new Object[] {
                   Integer.valueOf(ALL_FRAME[0][10]), Integer.valueOf(ALL_FRAME[0][11]) });
                 else
                   System.out.printf("%4d", new Object[] {
                   Integer.valueOf(ALL_FRAME[0][10]) });
       
             }else{

              if(ALL_FRAME[0][9] + ALL_FRAME[1][9] == 10)
              System.out.printf("%4d", new Object[] {
                Integer.valueOf(ALL_FRAME[0][10])
              });

             }

            //Print last chance and extra point  
            if(ALL_FRAME[0][9] == 10 && ALL_FRAME[0][10] != 10)
            System.out.printf("%4d", new Object[] {
              Integer.valueOf(ALL_FRAME[1][10])
            });

            // Print final Score in the game
            gameInstance.displayMessage(23,0); // FRAME SCORE
            for(int i = 0; i < 10; i++)
            System.out.printf("%d\t", new Object[] {
              Integer.valueOf(FRAME[i])
            });

            System.out.printf(_MESSAGE[14]); // display line
       
   
  }

  //--------------------------------
  // Table messages
  //--------------------------------
  private void  tableMessage(){

      _MESSAGE[0]="\n\t Ball 1: ";
      _MESSAGE[1]="\t Ball 2: ";
      _MESSAGE[2]="\n\t EXTRA TURN!! \n\n\t Ball 1: ";
      _MESSAGE[3]="\n\t EXTRA TURN!! \n\n\t Ball 2: ";
      _MESSAGE[4]="\n\t EXTRA TURN!! \n\n\t Ball 2: ";
      _MESSAGE[5]="\n\t EXTRA TURN!! \n\n\t Ball 2: ";
      _MESSAGE[6]="\n\t\t\t\t  Bowling Game Board ";
      _MESSAGE[7]="\n\t PLAYING FRAME %2d ";
      _MESSAGE[8]="\n\t\t\tSTRIKE!!";
      _MESSAGE[9]="\n\t\t\tSPARE!!";
      _MESSAGE[10]="\n\t\t\tLOOSER!";
      _MESSAGE[11]="\n\t\t\tYou can not do more than 10 pins";
      _MESSAGE[12]=" --------------- END OF THE GAME. ----------------- ";
      _MESSAGE[13]="\n\n\t\t\tPlay more (Y/N)? ";
      _MESSAGE[14]="\n\t-----------------------------------------------------------------------------------------------------";
      _MESSAGE[15]="\n\t\t Strike in extra ball 1...";
      _MESSAGE[16]="\n\t\t Strike in extra ball 2...";
      _MESSAGE[17]="\n\t\t SPLIT.";
      _MESSAGE[17]="\n\t\t OPEN FRAME.";
      _MESSAGE[18]=" Press Enter key to continue...";
      _MESSAGE[19]=" EXTRA 1 EXTRA 2";
      _MESSAGE[20]=" EXTRA";
      _MESSAGE[21]=" EXTRA";
      _MESSAGE[22]="\n\n\t RESULT: \t";
      _MESSAGE[23]="\n\n\t FRAME SCORE: \t";
      _MESSAGE[24]="\n\t RUNNING TOTAL: ";
      _MESSAGE[25]="";



  }

  //-------------------------
  // Clean screen 
  //------------------------
  private void cleanScreen() throws IOException, InterruptedException {
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

  } 

  //--------------------------------
  // Display Message
  //------------------------------
  private void displayMessage(int numMessage, int pause)
  {

    // Display the message of the table
    System.out.print(_MESSAGE[numMessage]);

    // make a pause if need to wait
    if (pause==1)
    {
      System.out.println(_MESSAGE[18]);
      Scanner s = new Scanner(System.in);
      s.nextLine();
    }

  }

  //--------------------------------
  // Get scores
  //------------------------------
  private int getFrameScore(int frame){
    int valueFrame = 0;
    if(ALL_FRAME[0][frame] == 10) { // STRIKE
      valueFrame = 10;
      for (int i=1;i<3;i++){
        if (ALL_FRAME[0][frame+i]==10){valueFrame=valueFrame+10;}else{ valueFrame= (i==2)? valueFrame + ALL_FRAME[0][frame+i]: valueFrame + ALL_FRAME[0][frame+i]+ALL_FRAME[1][frame+i];break;} 
      }

    }else if((ALL_FRAME[0][frame]+ALL_FRAME[1][frame]) == 10) //SPARE
    {
      valueFrame = 10 + ALL_FRAME[0][frame+1]; 
    }else{ // OPEN FRAME
      valueFrame = ALL_FRAME[0][frame]+ ALL_FRAME[1][frame]; 
    }
    return valueFrame;
  }

  //--------------------------------
  // Validate numbers
  //--------------------------------
  public boolean  isNumeric(String strNum) {
    //  numbers to check
    String a="012345678910";
    int intIndex = a.indexOf(strNum);

    //Validate result
    if(intIndex == - 1){
        return false;
    }else{
        return true;
    }
  }

}


