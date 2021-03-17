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
actiongame.java: This file will be running the game
***************
 **/

// Import classes
import java.util.Scanner;           
import java.io.*;
import java.util.*;
import java.security.SecureRandom;  // import randorm class
import java.util.Collections;

/**
 * Write a description of class actiongame here.
 *
 * @author (Juan R Lepe Manzo)
 * @version (11-05-2020)
 */
public class actiongame extends gameboard
{
    // instance variables - replace the example below with your own
    private String _AXIS_X_Y[][]=new String[20][20]; // Rows,Cols in board
    private String _LEVELS_DESCRIPTION[]=new String[3]; // Levels in the game
    private int _LEVELS_DETAILS[][]=new int[3][3]; // Details rows,cols, missles
    private String _SHIP_DATA[][]=new String[5][7]; // Data ships
    private String letterRows = ""; // What letter are in the board
    private int numLevel = 0; // number of the level in the game
    int colsLevel =0; // Cols in level game
    int rowsLevel =0; // Rows in level game
    int misslesUsed=0; // Missles used in the game
    int maxMissles=0; // Max numerbers to used in the game
    int hitsToWin = 0; // Numbers of hits to win game
    
    /**
     * Constructor for objects of class actiongame
     */
    public actiongame()
    {
        // init instance variables
        setLevels(new String[]{"Beginner", "Standard", "Advance"}); 
        letterRows = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        //ID ,Description, Position, lenght, XY_1, XY_2, XY_3, XY_4, XY_5, recordShuts
        _SHIP_DATA = new String[][] { 
            {"A","Aircraft Carrier","5","","","","","","0"}, // Aircraft Carrier
            {"B","Battleship","4","","","","","","0"}, // Battleship
            {"D","Destroyer","3","","","","","","0"}, // Destroyer
            {"S","Submarine","3","","","","","","0"}, // Submarine
            {"P","Patrol","2","","","","","","0"}, // Patrol
        };
        
        //Set detail level
        _LEVELS_DETAILS = new int[][] {
            {6, 6, 30}, // begginer
            {9, 9, 50}, // standard
            {12,12,75}, // advance
        };
        
        //set total hits to win game
        for (int i=0;i<_SHIP_DATA.length;i++) setHitsToWin(Integer.parseInt(_SHIP_DATA[i][2]));
        
    }

    public void setGame ()
    {
        Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
        gameboard objGameBoard = new gameboard(); // instance class from GameBoard
        String gameLevel = ""; // Varible local for game level
        String descLevel = ""; // variable local for level desciption
       
        objGameBoard.showLevelGame(_LEVELS_DESCRIPTION); // Show level
        
        boolean optionSelect = false;
        
        // loop while is 0
         do {

          // select level of the game
          objGameBoard.displayMessage(2,0);
            
            //get value selected
            gameLevel = keyIn.next();
            keyIn.nextLine();
            //verify is valid
            optionSelect = isLevel(gameLevel);
              
        } while(!optionSelect);
        
        // set values for selected level
        setNumLevel(gameLevel);
        // get details of the level selected
        int[] argLevelDetails = _LEVELS_DETAILS[getNumLevel()];

        // set number of columns level selected
        setColsLevel(argLevelDetails[0]);
        
        // set number of rows level selected
        setRowsLevel(argLevelDetails[1]);

        // set max missles in the game
        setMaxMissles(_LEVELS_DETAILS[getNumLevel()][2]);
        
        // set ship properties and ship position on board
        setShipData();

        // debug positions ship 
        //showShip();

    }

    //Setup ship position on board
    public void setShipData()
    {
        
         int col =0; // variable to set position column of the ship in panel
         int row = 0; //variable to set position row of the ship in panel
         int cols = getColsLevel(); // variable to get numbers of columns in panel
         int rows = getRowsLevel(); //variable to  get rows of columns in panel
         int sizeShip=0; // variable ship size
         
         //loop to set position for each 
         //ID ,Description, length, Position, XY_1, XY_2, XY_3, XY_4, XY_5
         for (int i = 0; i < _SHIP_DATA.length; i++) {

             // Ship Size 
             sizeShip = Integer.parseInt(_SHIP_DATA[i][2]);

             // loop verify and assign position
             do{
                 // loop get row and col
                 do {
                         // get random row for shit position 
                         row = getRanNum(rows-1);
                         // get random col for shit position 
                         col = getRanNum(cols);                 
                    }while(row<=0 && col<=0);
                 
                }while(!isPositionsAvailable(row, col, rows, cols, i, sizeShip));
            
         }    

    }
    
    // Method to verify positions availables to set ship
    private boolean isPositionsAvailable(int row, int col, int rows, int cols, int i, int sizeShip)
    {
             // set position for the ship (1=Horizontal,2=Vertical)
             int horizontal_vertical = getRanNum(2);
             // set position in array ships
             _SHIP_DATA[i][3] = String.valueOf(horizontal_vertical);
             int startcol = 3;
             boolean isPosAvailable =false; // flag to know if available position
             
            // set position of the ship on panel
             if (horizontal_vertical==1){ // Horzontal/ acostado   
                 
                do{
                     // Verify cols not bigger than  board
                     if ((sizeShip+ col)>=cols) col = cols-sizeShip; 
                     
                     row = (row>=rows)? getRanNum(rows): row;
                     
                     isPosAvailable = true;
                     for(int j=0;j<sizeShip;j++)
                     {
                         
                         //if position is not availale get another row and col
                         if (isCelAvailable(row,col+j)>0)
                         {
                            return false;
                         }

                      }
                      
                }while(!isPosAvailable) ;

             }else // Vertical/ parado
             {
                 
                do{
                     if ((sizeShip+ row)>=rows) row = rows-sizeShip; 
                     col = (col>=cols)? getRanNum(cols): col;
                     
                     isPosAvailable = true;
                     for(int j=0;j<sizeShip;j++)
                     {
                         // verify cell avaiblable 
                         if (isCelAvailable(row+j,col)>0)
                         {
                             
                            return false;
                         }

                      }
                      
                }while(!isPosAvailable) ;
                
             }
             
             //loop to set all condenates for each ship
             for (int j=0;j<sizeShip;j++ )
             {
                 // set position ship
                 _SHIP_DATA[i][startcol] = row+","+col;
                 
                // This line is for developer to see positions of ship
                //_AXIS_X_Y[row][col]= _SHIP_DATA[i][0];

                 if (horizontal_vertical==1) col+=1; else row+=1;
                 startcol++;

             }
             return true;
    }
    
    // get random number
    private int getRanNum(int val)
    {
         SecureRandom ranNum = new SecureRandom();  //instance random Number
        
        return ranNum.nextInt((val - 1) + 1) + 1;
    }
    
    // Verify cell is available 
    private int isCelAvailable(int row,int col)
    {
        for (int i=0;i<_SHIP_DATA.length;i++)
        {
            if (Arrays.asList(_SHIP_DATA[i]).indexOf(row+","+col)>0) return 1;
        }
        return 0;

    }

    // Get numbers of hits
    public int getSuccessfulHits()
    {
        int hits=0;
        for(int i=0;i<_SHIP_DATA.length;i++) hits+=Integer.parseInt(_SHIP_DATA[i][8]);
        return hits;
    }
    
    // set max shut of missles
    public void setMaxMissles(int val)
    {
        maxMissles = val;
    }
    
    // get max shut of missles
    public int getMaxMissles()
    {
        return maxMissles;
    }
    
    // Setup  total hits to win game
    public void setHitsToWin(int val){
        hitsToWin+=val;
        
    }
   
    //get total hits to win game
    public int getHitsToWin(){
        return hitsToWin;
    }
    
    // Setup level selected
    public void setNumLevel(String val)
    {
        numLevel = Integer.parseInt(val);
    }

    // get number of level selected
    public int getNumLevel()
    {
        return numLevel ;
    }
    
    //Set number of columns of level
    public void setColsLevel(int val)
    {
        colsLevel = val;
    }

    // Get number of columns of level
    public int getColsLevel()
    {
        return  colsLevel ;
    }

    //Set number of columns of level
    public void setRowsLevel(int val)
    {
        rowsLevel = val;
    }

    // Get number of columns of level
    public int getRowsLevel()
    {
        return  rowsLevel ;
    }
    
    //Become Letter in column or get num column with letter
    public int getNumRow (String argLetteRow)
    {
        int intIndex = letterRows.indexOf(argLetteRow.toUpperCase());

        return intIndex;
        
    }
    
    // Display board game
    public void boardGame(){
         
        gameboard objGameBoard = new gameboard(); // instance class from GameBoard 
        
        // Display game board 
        objGameBoard.displayBoard(_LEVELS_DESCRIPTION[getNumLevel()],_LEVELS_DETAILS[getNumLevel()], _AXIS_X_Y, letterRows, _SHIP_DATA, misslesUsed);
    }
    
    // Verify position of ships
    public String verifyPosition(int row, int col)
    {
        int numHits = 0;
        String cellValue= "X";
        int cell1 =0;
        int cell2 =0;
       
        
        for (int i=0;i < _SHIP_DATA.length; i++)
        {

            for (int j=0;j < _SHIP_DATA[i].length; j++)
            {
                ArrayList aList= new ArrayList(Arrays.asList(_SHIP_DATA[i][j].split(",")));
                if (aList.size()==2){cell1=(Integer.parseInt(""+aList.get(0)));cell2=Integer.parseInt(""+aList.get(1));}
                
                if((cell1==row)&&(cell2==col)){
                    numHits=Integer.parseInt(_SHIP_DATA[i][8])+1;
                    _SHIP_DATA[i][8]=String.valueOf(numHits);
                    return _SHIP_DATA[i][0];} 
            }
            
        }
        return cellValue;

    }
    
    // Check cell available
    public boolean checkCellAvailable(int row, int col)
    {
 
           return ((_AXIS_X_Y[row][col])==null) ? true: false;

    }
    
    // Method to select Cell to attack
    public void Attack()
    {
       Scanner keyIn = new Scanner(System.in);  // new instance Scanner object
       gameboard objGameBoard = new gameboard(); // instance class from GameBoard 
       //**************************************************
       //BONUS POINTS AVAILABLE:  +5 to display positions of ships at start of game (for grading)
       //**************************************************
       objGameBoard.showShip(_SHIP_DATA,letterRows);

       String sRow = "";
       String sCol = "";
       int nCol = 0;
       int nRow = 0;
       int HitsToWin = getHitsToWin(); // get hits to win game
       int nMaxMissles = getMaxMissles(); //get number of the missles in the game
       String cellAttack = "";
       boolean isWin = false;
       int numMessage = 0;
       

         do {
             
            // select level of the game
            objGameBoard.displayMessage(3,0);

            cellAttack = keyIn.nextLine();
            cellAttack = cellAttack;
    
            // Verify if exit of the game
            if (!cellAttack.equals("0"))
            {
                
                // Verify length of the input
                if (cellAttack.length()>=2 )
                {
                    //get input row
                    sRow = cellAttack.substring(0,1);
                    //get input col
                    sCol = cellAttack.substring(1);
                    // Verify if input has col and row
                    if (isValueX(sRow) && isValueY(sCol))
                    {

                        nRow = getNumRow(sRow);
                        nCol = Integer.parseInt(sCol);
                        // verify if col and row is a emty
                        if (checkCellAvailable(nRow,nCol))
                        {
                            _AXIS_X_Y[nRow][nCol] = verifyPosition(nRow,nCol);
                            misslesUsed++;
                        }
        
                        numMessage = 0;
                        
                    }else{
                        numMessage= 1;
                    }
                    
                    
                }else
                {
                    numMessage= 1;
                }
                
                
            }
            
            //Display Board Game
            boardGame();
            objGameBoard.displayMessage(numMessage,0);
            
            // show position, if you want.
            if (cellAttack.equals("S") || cellAttack.equals("s")) objGameBoard.showShip(_SHIP_DATA,letterRows);
            
            //Verify number successful hits to win 
            if (getSuccessfulHits()==HitsToWin) {
                isWin=true;
                cellAttack="0";}
            //Verify number of missles used
            if ((nMaxMissles==misslesUsed)&&(!isWin)){cellAttack="0";}
  
        } while(!cellAttack.equals("0"));
        
        objGameBoard.endGame(isWin);
        objGameBoard.showShip(_SHIP_DATA,letterRows);
    }
    
    // Get array o description of level
    public String[] getLevels()
    {
        return _LEVELS_DESCRIPTION;
    }

    // Setup description of levels
    public void setLevels(String[] val)
    {
        //set description level
         _LEVELS_DESCRIPTION=val;

    }
    

    // Verify valid value on col
    public boolean  isValueY(String strNum) {

        try {
                    int i=getColsLevel();
                    int y=Integer.parseInt(strNum);
            
                    //Validate result
                    if((y > i)||(y==0)){
                        return false;
                    }else{
                        return true;
                    }       
        } catch (NumberFormatException nfe){
            return false;
        }        

  }   
   
  //Verify valid value on row
   public boolean  isValueX(String strNum) {
        //  numbers to check
        String b="";
        
        // Get letter from the level game
        b=letterRows.substring(0,getRowsLevel());
        
        //find letter on the string
        int intIndex = b.indexOf(strNum.toUpperCase());
    
        //Validate result
        if(intIndex == - 1){
            return false;
        }else{
            return true;
        }
  }   
    //--------------------------------
    // Validate numbers
    //--------------------------------
  public boolean  isLevel(String strNum) {
        //  numbers to check
        String a="";
        
        for (int i = 0; i < _LEVELS_DESCRIPTION.length; i++) {
           a+=String.valueOf(i);
        }
        
        int intIndex = a.indexOf(strNum);
    
        //Validate result
        if(intIndex == - 1){
            return false;
        }else{
            return true;
        }
  }
}
