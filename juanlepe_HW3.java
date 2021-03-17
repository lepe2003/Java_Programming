
//-------------------------------
// Import packegs to used from javaFX
//-------------------------------

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
Author: Almas Baimagambetov
Reference: https://www.youtube.com/watch?v=Uj8rPV6JbCE&t=60s

METROPOLITAN COMMUNITY COLLEGE
CSIS 222–OBJECT-ORIENTED PROGRAMMING IN JAVA
Student Name: Juan Lepe
Student ID: S1448233 
Date: NOV/2020
Instructor: Carol Hull
HOMEWORK 03 – Tic Tac Toe with Animation Using JavaFX

Description: 
1. Compile and run your program – output to the screen.
2. Ensure it is working correctly. TEST all scenarios – wins in each row/column and diagonally. Test to
ensure it can also detect when there’s no clear winner. 
3. FULLY document your source code. Since you have been given the code for this project, the
majority of your grade will be derived from the quality and amount of the documentation. All
lines need to be commented accurately for full credit with the exception of those that are absolutely
obvious like main, break, or this settings. If in doubt, COMMENT!
4. When done, upload your project to Blackboard, then kick back and challenge your roomy to a game
of TicTacToe. 

*/


public class juanlepe_HW3 extends Application
{

    //-------------------------
    // Globals  Variables
    //-------------------------
    private boolean playable = true; // Variable to verify the game
    private boolean turnX = true; // Variable to start with X on panel
    private Tile[][] board = new Tile[3][3]; // Array columns and rows
    private List<Combo> combos = new ArrayList<>(); // ArrayList for panel game

    private Pane root = new Pane(); // Create object for game panel

    //----------------------------------
    // Create superclass to create content and setup properties 
    //---------------------------------
    private Parent createContent() {

        root.setPrefSize(600, 600); // Setup size panel (width, height)

        // Move the object to the axis X(columns) and Y(rows) 
        for (int i = 0; i < 3; i++) { // loop axis X (columns)
            for (int j = 0; j < 3; j++) { // loop  Axis Y (rows)
                Tile tile = new Tile(); // Create object Tile with properties (color, font, etc)
                tile.setTranslateX(j * 200); // Set position or move object to the rigth
                tile.setTranslateY(i * 200); // Set position or move from the top-left corner to the point

                root.getChildren().add(tile); // Add object to panel layout

                board[j][i] = tile; // Add object tile to each of the element in the array
            }
        }

        // Loop to add horizontal lines   
        for (int y = 0; y < 3; y++) {

            combos.add(new Combo(board[0][y], board[1][y], board[2][y]));// add object Combo with dimentions 1,2 3
        }

        // Loop to add vertical lines 
        for (int x = 0; x < 3; x++) {
            combos.add(new Combo(board[x][0], board[x][1], board[x][2]));// add object Combo with dimentions 1,2 3 
        }

        // create diagonal lines left to right in the panel
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        // create diagonal lines right to left in the panel
        combos.add(new Combo(board[2][0], board[1][1], board[0][2]));

        return root;
    }

    //-----------------------------------------------
    // Start the execution of the class 
    //-----------------------------------------------
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setup content on the stage with size, position, others
        primaryStage.setScene(new Scene(createContent()));
        // Display application
        primaryStage.show();
    }

    // --------------------------------------
    // Method to check all the possibles combos in the game 
    //---------------------------------------
    private void checkState() {
        // loop to check position on the stage
        for (Combo combo : combos) {
            // verify if there is a winner 
            if (combo.isComplete()) {

                playable = false; // turn off variable playable
                playWinAnimation(combo); // show animation for winner
                break; //exit of the loop to stop game
            }
        }
    }

    // --------------------------------------
    // Method to create animation with a line
    //---------------------------------------
    private void playWinAnimation(Combo combo) {
        
        Line line = new Line(); // create object line
        line.setStartX(combo.tiles[0].getCenterX()); // set start position to display line on axis X
        line.setStartY(combo.tiles[0].getCenterY()); //set end position to start the line on axis Y
        line.setEndX(combo.tiles[0].getCenterX()); // set start position to end the line on axis X
        line.setEndY(combo.tiles[0].getCenterY()); // set end position to end the line on axis Y

        root.getChildren().add(line); // Add line to the panel

        Timeline timeline = new Timeline(); // Create object for animation 
        
        // Animate values and add frame Get point in times to set values (duration of the animation, keyvalue in the axis X, keyvalue in the axis Y)
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.tiles[2].getCenterX()),
                new KeyValue(line.endYProperty(), combo.tiles[2].getCenterY())));
        // Play amimation         
        timeline.play();
    }

    //---------------------------
    // Data structure Combo
    //-------------------
    private class Combo {
        private Tile[] tiles; // create array tiles
        
        // constructor will takes array tiles
        public Combo(Tile... tiles) {
            this.tiles = tiles; // assign tiles to array
        }

        //Method to check if the combo is complete to deside is a row or column is win
        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty()) // Verify value in array. If it's empty it is not complete.
                return false;
                
            // return values of others two tites to verify again values on these.
            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }

    //---------------------------------------------------------------
    // Class to create object tile and extend the class SpackPane
    //  to create label text or rectangle, and add it in the stage
    //---------------------------------------------------------------
    private class Tile extends StackPane {
        
        private Text text = new Text(); // create object text

        // function to create all of the properties in the object rectangle(tile)
        public Tile() {
            // Create object rectangle size 200x200 
            Rectangle border = new Rectangle(200, 200); 
            // Set fill with default color or not color
            border.setFill(null);
            // Setup border color with Black 
            border.setStroke(Color.BLACK);
            // Setup font size to 72px on object text panel
            text.setFont(Font.font(72));
            // aligment elements in the panel
            setAlignment(Pos.CENTER);
            //Add objects(boder and text) to stage 
            getChildren().addAll(border, text);

            // Setup event click on mouse with method 'setOnMouseClicked'
            setOnMouseClicked(event -> {
                if (!playable) // verify is it is payabled or stop process
                    return;

                // verify left button on mouse
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX) // if flag 'X' is turn off don't do anything
                        return;

                    drawX();// print 'X' on grid 
                    turnX = false; // turn off 'X' to play with 'O'
                    checkState();// Verify winner 
                }
                // verify right button on mouse
                else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX) // if flag 'X' is on don't do anything 
                        return;

                    drawO();// print 'O' on grid
                    turnX = true; // turn on 'X' to play with 'X'
                    checkState(); // Verify winner
                }
            });
        }
        
        //--------------------------------------------------------
        // Function to Get the center position in the x coordinate
        //--------------------------------------------------------
        public double getCenterX() {
            // return the position of the value of the property translateX. 
            return getTranslateX() + 100;
        }

        //--------------------------------------------------------
        // function to Get center position in 'Y' coordinate
        //--------------------------------------------------------
        public double getCenterY() {
            // return the position of the value of the property translateY. 
            return getTranslateY() + 100;
        }

        //--------------------------------------------------------
        // Function to Get value from the object text in the panel 
        //--------------------------------------------------------
        public String getValue() {
            // Return text values from text object
            return text.getText();
        }

        //--------------------------------------------------------
        // Function to set value 'X' to text object and Draw it on the panel 
        //--------------------------------------------------------
        private void drawX() {
            // Set X on text object
            text.setText("X");
        }

        //--------------------------------------------------------
        // Function to set value 'O' to text object and Draw it on the panel 
        //--------------------------------------------------------
        private void drawO() {
            // Set O on text object
            text.setText("O");
        }
    }

    //----------------
    // Main Function
    //----------------
    public static void main(String[] args) 
    {
        
        // Method to create the object its call method start
        launch(args);  
    }

}
