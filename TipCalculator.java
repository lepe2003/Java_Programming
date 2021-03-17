import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


/**
 * Write a description of JavaFX class TipCalculator here.
 *
 * @author (Juan Lepe)
 * @version (11-02-2020)
 */

public class TipCalculator extends Application {
   @Override
   public void start(Stage stage) throws Exception {
      Parent root = 
         FXMLLoader.load(getClass().getResource("juan_lepe_HW4.fxml"));

      Scene scene = new Scene(root); // attach scene graph to scene
      stage.setTitle("Tip Calculator"); // displayed in window's title bar
      stage.setScene(scene); // attach scene to stage
      stage.show(); // display the stage
   }

   public static void main(String[] args) {
      // create a TipCalculator object and call its start method
      launch(args); 
   }
}
