package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

//import java.awt.Dimension;

public class Main extends Application {
	
	private Scene gameScene;
	
	private final static double screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final static double screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	@Override
	public void start(Stage primaryStage) {
		try {
		
			    BorderPane root = new BorderPane(); 
			
				     
				//Creating Buttons 
			    Button startGame = new Button("Start Playing"); 
			    Button quitGame = new Button("Quit");  
			    
			    
			    
			    startGame.setPrefWidth(200.0);
			    quitGame.setPrefWidth(200.0);
				
				
			    // Create down BorderPane
			    BorderPane borderPane = new BorderPane();
			    
			    // Set the Buttons to their Location
	            BorderPane.setAlignment(startGame,Pos.BOTTOM_LEFT);
			    BorderPane.setAlignment(quitGame,Pos.BOTTOM_RIGHT);
			    
			    
			    borderPane.setLeft(startGame);
			   
			    borderPane.setRight(quitGame);
			    
			    root.setBottom(borderPane); 
			    root.setCenter(addHBox());
			  
				Scene scene = new Scene(root,screenWidth/2.0f,screenHeight/2.0f);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				
				//add actions for each Button when clicked
				hanbleButtonAction(startGame,primaryStage);
				hanbleButtonAction(quitGame,primaryStage);
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public HBox addHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    
	    Text gameName = new Text("Word Builder");
		
        gameName.setFont(Font.font(null, FontWeight.BOLD, 72));
        
        
        // DropShadow for gameName
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        gameName.setEffect(dropShadow);
        
        
        Reflection r = new Reflection();
        r.setFraction(0.9f);
        gameName.setFill(Color.WHITE);
        gameName.setEffect(r);
        
        // Adding ID's
        gameName.setId("gameStartLogo");
        hbox.setAlignment(Pos.TOP_CENTER);
        
	    hbox.getChildren().add(gameName);
	   

	    return hbox;
	}
	
	
	public void hanbleButtonAction(Button btn,Stage primaryStage) {
		
		
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	switch(btn.getText()) {
		    	    
		    	    case "Start Playing":
		    	    	//fill in with code to generate the new
		    	    	//Stage, pre-processing word data
		    	    	//and fill in progress bar
		    	    	((Node)(e.getSource())).getScene().getWindow().hide();
		    	    	
		    	    	BorderPane root = new BorderPane(); 
		    	    	
		    	    	gameScene = new Scene(root,screenWidth/2.0f,screenHeight/2.0f);
		    	    	
		    	    	primaryStage.setScene(gameScene);
						primaryStage.setResizable(true);
						primaryStage.show();
		    	    	
		    	    	
		    	    	break;
		    		
		    	    case "Quit":
		    	    	((Node)(e.getSource())).getScene().getWindow().hide();
		    	    	Platform.exit();
		    	    	break;
		    	    	
		    	    default:
		    	    	break;
		    	
		    	}
		        
		    }
		});
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
