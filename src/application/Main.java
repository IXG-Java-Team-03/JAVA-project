package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// Create wordlist
        WordSet myWords = new WordSet("GR", 2, 5, "words/");
        for (int i = myWords.minLength(); i <= myWords.maxLength(); i++) {
            ArrayList<String> myList = new ArrayList<String>();
            myList = myWords.WordsDB.get(i);
            System.out.println("- "+myList.size()+" λέξεις "+i+" γραμμάτων.");
//            //print some
//            for (int x = 0; x<10;x++){
//                System.out.println(myList.get(x));
//            }
        }
        // String with the letters we have available. Can be given randomly or via an existing word.
        String myLetters = "ΑΒΓΔΕ";
        ArrayList<String> currentGameSet = myWords.AssembleWordGameSet(myLetters);
        System.out.println("Με τα γράμματα \""+myLetters+"\" φτιάχνονται "+currentGameSet.size()+" λέξεις");
        for (int x = 0; x<currentGameSet.size();x++){
        	System.out.print(currentGameSet.get(x));
        	if (x<currentGameSet.size()-1) System.out.print(", ");
        }
        System.out.println("");
        
        // show stage
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
			Scene scene = new Scene(root,460,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
