/*
    This is the first assigment for systems and security module. 
    Made by Joonas Känsälä in 23.3.2016
*/

package securityassignment;

import java.math.BigInteger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecurityAssignment extends Application {
    
     BigInteger b = new BigInteger("7");
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
