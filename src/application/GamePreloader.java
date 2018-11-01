package application;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GamePreloader extends Preloader{
		    
	    private static BorderPane root;
	    private ProgressBar pb; 
	    private Label progress;
	    
	    final static double WIDTH = 1048;// java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2.0f;
		final static double HEIGHT = 768; // java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2.0f;
	    
        private VBox vb;	   
        private static Stage GamePreloadStage;

	    private Scene scene;
	   
	    

	    public GamePreloader() {
	        // Constructor is called before everything.
	    	pb = new ProgressBar(0.4);
	    	root = new BorderPane();
	        System.out.println(WordBuilderGame.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());
	    }
	    
	    
	    
	    @Override
	    public void init() throws Exception {
	        System.out.println(WordBuilderGame.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: " + Thread.currentThread().getName());

	        
	        // If preloader has complex UI it's initialization can be done in MyPreloader#init
	        Platform.runLater(() -> {
	            Label title = new Label("Loading, please wait...");
	            title.setTextAlignment(TextAlignment.CENTER);
	            title.setTextFill(Color.WHITE);
	            title.setStyle("-fx-font-size: 21px;");
	            
	            progress = new Label("0%");
	            progress.setTextFill(Color.WHITE);
	            progress.setStyle("-fx-font-size: 21px;");

	            vb = new VBox(title, progress);
	            vb.setAlignment(Pos.BOTTOM_CENTER);
	            
	            pb.setProgress(0);
	            
	            pb.setPrefWidth(200);

	            
	        });
	        
              
	    }
	    

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        System.out.println(WordBuilderGame.STEP() + "MyPreloader#start (showing preloader stage), thread: " + Thread.currentThread().getName());

	       
	        
	        GamePreloadStage = primaryStage;
	        
            try {
	    		
			    
			    //this is for the logo
			    VBox box = addVBox();
			    
			    box.getChildren().add(vb);
			    
			    box.getChildren().add(pb);
			    
			    
			    
			    //set the log at the center of the center
			    root.setCenter(box);
			    
			   
			    
			    //root.setCenter(box);
			  
				scene = new Scene(root,WIDTH,HEIGHT);
				
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				
				
		} catch(Exception e) {
			e.printStackTrace();
	    }        
    	 primaryStage.setScene(scene);
    	 primaryStage.setResizable(false);
    	 primaryStage.show();
		
    	//add actions for each Button when clicked
		//hanbleButtonAction(startGame);
		//hanbleButtonAction(quitGame);
	        
	        
		}
	        
	            

	    @Override
	    public void handleApplicationNotification(PreloaderNotification info) {
	        // Handle application notification in this point (see MyApplication#init).
	        if (info instanceof ProgressNotification) {
	            progress.setText((int)((ProgressNotification) info).getProgress() + "%");
	            pb.setProgress(((ProgressNotification) info).getProgress()/100);
	        }
	    }

	    @Override
	    public void handleStateChangeNotification(StateChangeNotification info) {
	        // Handle state change notifications.
	        StateChangeNotification.Type type = info.getType();
	        switch (type) {
	            case BEFORE_LOAD:
	                // Called after MyPreloader#start is called.
	                System.out.println(WordBuilderGame.STEP() + "BEFORE_LOAD");
	                break;
	            case BEFORE_INIT:
	                // Called before MyApplication#init is called.
	                System.out.println(WordBuilderGame.STEP() + "BEFORE_INIT");
	                break;
	            case BEFORE_START:
	                // Called after MyApplication#init and before MyApplication#start is called.
	                System.out.println(WordBuilderGame.STEP() + "BEFORE_START");

	                GamePreloadStage.hide();
	                break;
	        }
	    }
	    
	    public static VBox addVBox() {
		    VBox vbox = new VBox();
		    //vbox.setPadding(new Insets(15, 12, 15, 12));
		    vbox.setSpacing(70);
		    
		    Text gameName = new Text("Word Builder");
			
	        gameName.setFont(Font.font(null, FontWeight.BOLD, 72));
	        
	        
	        // DropShadow for gameName
	        DropShadow dropShadow = new DropShadow();
	        dropShadow.setOffsetX(4);
	        dropShadow.setOffsetY(4);
	        
	        gameName.setEffect(dropShadow);
	        
	        
	        Reflection r = new Reflection();
	        r.setFraction(0.9f);
	        gameName.setFill(Color.WHITE);
	        gameName.setEffect(r);
	        
	        // Adding ID's
	        //gameName.setId("gameStartLogo");
	        vbox.setAlignment(Pos.TOP_CENTER);
	        
		    vbox.getChildren().add(gameName);
		   

		    return vbox;
		}
	    
	   
			
			
		}
	   


