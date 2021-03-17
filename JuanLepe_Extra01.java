
/*
METROPOLITAN COMMUNITY COLLEGE
CSIS 222–OBJECT-ORIENTED PROGRAMMING IN JAVA
Author: Juan Ramon Lepe Manzo
Student ID: S1448233 
Date: Sep/2020
Instructor: Carol Hull
Programming Project #1 – EXTRA CREDIT OPORTUNITY
Description: 
1. Create three variables to hold three randomly generated numbers, 1 to 9, including 1 and 9.

2. Prompt the user for three numbers, 1-9, using one of the looping structures we learned in
Chapters 3 and 4. Duplicate numbers are allowed. (For example, 7, 3, 7 is a valid pick, as is
8,2,5 and 1, 1, 1).

3. Then need to check the three numbers entered (order is not important) against the three
generated random numbers.

4. After checking the user’s number selection against the randomly generated numbers,
determine how many, if any numbers matched and then display the appropriate win/loss
message (see challenge above), then show the numbers picked AND the generated numbers.
Label them so it’s clear which are the user’s input and which are the random generated.

 ----------------------***END OF PROGRAM ***
*/
import java.util.Scanner;           // Import the Scanner class
import java.security.SecureRandom;  // import randorm class

public class JuanLepe_Extra01 {         // beginning of class 

    // CONSTANT VARIABLES
    static String _message[]= new String[99];
    static int STARTNUM = 1;
    static int ENDNUM = 9;

    public static void main (String[] args) {

        // Variables and instances
        boolean wantToplay = true;
        JuanLepe_Extra01 var_lottery_game = new JuanLepe_Extra01();
        var_lottery_game.tableMessage();

        // Call lottery game
        do{System.out.println("");}while(var_lottery_game.lottery_game());
        System.out.println(_message[0]);

    } // end main method

    //-----------------------
    // Method to play lottery
    //-----------------------
    public boolean lottery_game()
    {

        Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
        SecureRandom ranNbr = new SecureRandom();  //new instance secure random called ranNbr

        // declare variables
        int[] randonValue = new int[3]; 
        int[] numberToPlay = new int[3]; 
        String strValue= "";
        
        // Display Instructions
        String answer = "";
		System.out.println(_message[1]);
		System.out.println("");
		System.out.println(_message[2]);
		System.out.println(_message[3]);
		System.out.println(_message[4]);
        System.out.println(_message[5]);
        System.out.println(_message[6]);
        System.out.println( "");
        System.out.print( _message[7]);
        answer = keyIn.next();

        // while to play
        while (!(answer.equals("y")||answer.equals("Y")))
        {
            if (answer.equals("n") || answer.equals("N")) {
                return false;
            }
            System.out.print( _message[8]);
            answer = keyIn.next();
        }

        for (int i=0;i<3;i++) randonValue[i] = ranNbr.nextInt((ENDNUM - STARTNUM) + 1) + STARTNUM;

        // Play Game
        playGame(numberToPlay,randonValue);
        
        // Look numbers on ticket
        matchNumber(numberToPlay,randonValue);

        answer = keyIn.next();

        return true;

    } // end lottery_game method

    //-----------------------
    // Method to play the game
    //-----------------------
    public void playGame(int[] numberToPlay, int[] randonValue){

        String strValue= "";
        Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
         
        // Randon numbers on ticket
        System.out.println(_message[13]);
        System.out.println( _message[9]);
        
        // Initialitation user values
        for (int i=0;i<3;i++) numberToPlay[i] = 0;

        // User numbers
        do
        {
            System.out.printf(_message[10], STARTNUM, ENDNUM);
            for (int i=0;i<3;i++) {strValue = keyIn.next();numberToPlay[i] = isNumeric(strValue)? Integer.parseInt(strValue):-1;};
            
        }while((numberToPlay[0]<0)||(numberToPlay[1]<0)||(numberToPlay[2]<0));
  
        // Display user numbers and ticket numbers 
        System.out.println(_message[13]);
        System.out.printf(_message[11], randonValue[0],randonValue[1],randonValue[2]);
        System.out.printf(_message[12], numberToPlay[0],numberToPlay[1],numberToPlay[2]);
        
    }

    //----------------------------------------
    // Method to verify how many match numbers
    //----------------------------------------
    public void matchNumber(int[] numberToPlay, int[] randonValue){
        int guessedNumbers=0;

        for(int i=0;i<3;i++)
        {
            // ticket numbers  vs user numbers 
            for(int ii=0;ii<3;ii++) 
            {
                numberToPlay[i]=(numberToPlay[i]==randonValue[ii])?-1:numberToPlay[i];// validate randon numbers on ticket and user numbers
                if (numberToPlay[i]==-1) {guessedNumbers++;randonValue[ii]=-1;break;} // count numbers founded
            };
        }

        System.out.println(_message[13]);
        //Verify how many match
        switch (guessedNumbers)
        {
        case 1:    //match ONE
            System.out.println(_message[14]);
            break;
        case 2:    //match TWO
            System.out.println(_message[15]);
            break;
        case 3:    //match THREE
            System.out.println(_message[16]);
            break;
        default:    // lose
            System.out.println(_message[17]);
            break;
        } // end switch
            
        System.out.println(_message[13]);
        System.out.printf( _message[18]);

    }

    //--------------------------------
    // Method to validate numbers
    //--------------------------------
    public boolean  isNumeric(String strNum) {
        //  numbers to check
        String a="01234567890";
        int intIndex = a.indexOf(strNum);

        //Validate result
        if(intIndex == - 1){
            return false;
        }else{
            return true;
        }
    }

    //--------------------------------
    // Table of messages
    //--------------------------------
    public void  tableMessage(){

        _message[0]="Thank you.";
        _message[1]="--------  * * * L O T T E R Y   G A M E  * * *  --------";
        _message[2]="Instructions:";
	    _message[3]="1. If you able to match ONE of the three numbers on the ticket, you win $2.";
	    _message[4]="2. If you able to match TWO of the three numbers on the ticket, you win $20.";
        _message[5]="3. If you able to match THREE of the three numbers on the ticket, you win $50.";
        _message[6]="4. The order of the numbers is not important.";
        _message[7]="Do you want to play. Y/N ";
        _message[8]="Please select. Y/N ";
        _message[9]="Numbers on the ticket (?) (?) (?)";
        _message[10]="Enter three numbers between %d and %d separate for space.\n";
        _message[11]="Numbers on the ticket are (%d) (%d) (%d)\n";
        _message[12]="Your selected numbers are (%d) (%d) (%d)\n";
        _message[13]="----------------------------------------------------------------------";
        _message[14]="Match ONE of the three numbers on the ticket, you won $2.";
        _message[15]="Match TWO of the three numbers on the ticket, you won $20.";
        _message[16]="You WIN!!!!! Match THREE of the three numbers on the ticket, you won $50.";
        _message[17]="Sorry, loser.";
        _message[18]="=> End of the game. <= ";

    }

}  // end of class 

