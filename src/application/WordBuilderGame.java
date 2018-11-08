package application;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.application.LauncherImpl;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * hdfghdfghdfgh
 * @author soco
 *
 */
public class WordBuilderGame extends Application {

	private final static Button startGame = new Button("Start Playing");
	private final static Button quitGame = new Button("Quit");

	private final static int MAXROWS = 15;
	private final static int MAXCOLS = 15;

	/**
	 *  Just a counter to create some delay while showing preloader.
	 */
	private static final int COUNT_LIMIT = 5000;

	private static int stepCount = 1;

	private BorderPane root;

	private static int Level;

	private Scene scene;

	private static int Score;

	private Button chckword, resetword, shuffleword,nextlevel;

	private GridPane gamePane;

	private ArrayList<Button> availableLetters, availablePositions;

	/**
	 * Container for the upper row
	 */
	private CharContainer charArrayUpper = new CharContainer();
	/**
	 * Container for the lower row
	 */
	private CharContainer charArrayLower = new CharContainer();
	/**
	 * Char indicating an empty position
	 */
	public static final char EmptyLabel = '_';
    
	private String initialLetters;

	/**
	 *  variable for the timer value
	 */
	private static Integer STARTTIME = 60;
	

	private HBox createTimer() {

		if (STARTTIME != 0) {
			
			AnimationTimer timer;
			
			

			Label timeSlogan = new Label("Time");
			timeSlogan.setTextFill(Color.WHITE);
			timeSlogan.setStyle("-fx-font-size:28px;");
			// timeSlogan.setStyle("-fx-font-weight:bold");

			Timeline timeline = null;
			Label timerLabel = new Label();
			IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME * 100);

			timerLabel.textProperty().bind(timeSeconds.divide(100).asString());
			timerLabel.setTextFill(Color.GREEN);
			timerLabel.setStyle("-fx-font-size:28px;");



			timeSeconds.set((STARTTIME + 1) * 100);
			timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
			
			
			
			
			
			
			//You can add a specific action when each frame is started.
	        timer = new AnimationTimer() {
	        	
	        	long starttime = 0L;
	        	long currentabstime=0;
	        	
	            @Override
	            public void handle(long l) {
	            	
	            	if(starttime==0)
	            		starttime=l;
	            	else
	            	{
	            		currentabstime=l-starttime;		
	            	}
	            	
	                
	            	if(TimeUnit.SECONDS.convert(currentabstime, TimeUnit.NANOSECONDS)==(STARTTIME-(STARTTIME/2)))
	            		timerLabel.setTextFill(Color.YELLOW);
	            	else if(STARTTIME > 10 && (TimeUnit.SECONDS.convert(currentabstime, TimeUnit.NANOSECONDS)==STARTTIME-5))
	            		timerLabel.setTextFill(Color.RED);
	                
	            }
	        };
			
		
	        //this part of code will perform some actions when 
			//the timer expires
            timeline.setOnFinished(event -> {
				timer.stop(); //stop Animation timer
			
				
				
			});
			

			timeline.playFromStart();
			timer.start();
			


			HBox hb = new HBox(20);
			hb.setAlignment(Pos.BASELINE_LEFT);
			hb.getChildren().addAll(timeSlogan, timerLabel);
			//hb.setLayoutY(20);

			return hb;

		}

		return null;

	}

	public static void setStartTime(int value) {

		STARTTIME = value;

	}

	// Used to demonstrate step couns.
	public static String STEP() {
		return stepCount++ + ". ";
	}

	private Stage applicationStage;

	public static void main(String[] args) {
		// thes ena thread gia na kaleis afto
		LauncherImpl.launchApplication(WordBuilderGame.class, GamePreloader.class, args);
	}

	public WordBuilderGame() {
		// Constructor is called after BEFORE_LOAD.
		System.out.println(WordBuilderGame.STEP() + "MyApplication constructor called, thread: "
				+ Thread.currentThread().getName());
		// Create down BorderPane
		root = new BorderPane();
		availableLetters = new ArrayList<Button>();
		availablePositions = new ArrayList<Button>();
		Level = 0;
		Score = 0;
		
		startGame.setId("default-button");
		quitGame.setId("default-button");

		startGame.setPrefWidth(200.0);
		quitGame.setPrefWidth(200.0);

	}

	@Override
	public void init() throws Exception {
		System.out.println(WordBuilderGame.STEP() + "MyApplication#init (doing some heavy lifting), thread: "
				+ Thread.currentThread().getName());

		// Perform some heavy lifting (i.e. database start, check for application
		// updates, etc. )
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (100 * i) / COUNT_LIMIT;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));

		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(
				WordBuilderGame.STEP() + "MyApplication#start (initialize and show primary application stage), thread: "
						+ Thread.currentThread().getName());

		// keep the stage since we will change the scene when "start playing" will
		// be pushed
		applicationStage = primaryStage;

		try {

			// this is for the logo
			VBox box = GamePreloader.addVBox();

			// set the log at the center of the center
			root.setCenter(box);
			
			

			BorderPane border = new BorderPane();

			// Set the Buttons to their Location
			BorderPane.setAlignment(startGame, Pos.BOTTOM_LEFT);
			BorderPane.setAlignment(quitGame, Pos.BOTTOM_RIGHT);

			border.setLeft(startGame);
			border.setRight(quitGame);

			root.setBottom(border);

			scene = new Scene(root, GamePreloader.WIDTH, GamePreloader.HEIGHT);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		handleButtonAction(startGame);
		handleButtonAction(quitGame);

	}

	public void handleButtonAction(Button btn) {

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				switch (btn.getText()) {

				case "Start Playing":
					// fill in with code to generate the new
					// Stage, pre-processing word data
					// and fill in progress bar
					// ((Node)(e.getSource())).getScene().getWindow().hide();

					gamePane = new GridPane();

					setGridPaneRowsCols(gamePane, MAXROWS, MAXCOLS);

					// it shows the grid of cols and rows
					//gamePane.setGridLinesVisible(true);

					gamePane.setPadding(new Insets(10, 10, 10, 10));

					Label WlistLabel = new Label("Words");
					WlistLabel.setTextFill(Color.WHITE);
					WlistLabel.setStyle("-fx-font-size: 34px;");
					WlistLabel.setAlignment(Pos.CENTER_LEFT);

					GridPane.setHalignment(WlistLabel, HPos.CENTER);

					gamePane.add(WlistLabel, 0, 1, 2, 1);

					SetGameLevel();

					// on the left we will need a list of words to be found
					ListView<String> wordsList = new ListView<String>();

					// ***********************This is just an example**************
					// here we will call a function that will return the found words
					// with a dash replacing each word letter
					String[] exampleWords = { "- - - - - - -", "- - -", "- - -", "- - - - -", "- - - - -", "- -", "- -",
							"- - -", "- - -","- - - - - - -" };

					wordsList.setItems(FXCollections.observableArrayList(exampleWords));
					// wordsList.setBackground(value);

					wordsList.setMouseTransparent(true);
					wordsList.setFocusTraversable(false);

					gamePane.add(wordsList, 0, 2, 3, MAXROWS - 2); // spans 1 column, 4 rows

					// create and add the timer
					HBox hbtimer = createTimer();
					//hbtimer.setPadding(new Insets(10));

					gamePane.add(hbtimer, MAXCOLS - 4, 2, 2, 1); // spans 2 columns and 2 rows (the last two elements)

					SetScore();

					createLetterSeqBut("KOSSSTA");

                    //create the 4 control buttons: check word, shuffle, reset, next level
					chckword = createControlButton("/res/ok.png","Checks if word is in the list!",Color.GREENYELLOW);
					resetword = createControlButton("/res/reset.png","Resets all actions done so far!",Color.GREENYELLOW);
					shuffleword = createControlButton("/res/shuffle.png","Shuffle letters!",Color.GREENYELLOW);
					nextlevel = createControlButton("/res/level.png","Go to next level!",Color.AQUA);
					
					HBox hbox = new HBox(chckword, resetword, shuffleword,nextlevel);

					gamePane.add(hbox, MAXCOLS - 5, MAXROWS - 1, 1, 1);
					

					scene = new Scene(gamePane, GamePreloader.WIDTH, GamePreloader.HEIGHT);

					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

					applicationStage.setTitle("World Builder");
					applicationStage.setScene(scene);
					applicationStage.show();

					break;

				case "Quit":
					((Node) (e.getSource())).getScene().getWindow().hide();
					Platform.exit();
					break;

				default:
					break;

				}

			}
		});
	}

	private void SetGameLevel() {

		++Level;

		Text gameLevel = new Text("Level " + Level);

		gameLevel.setId("gamelevel");

		// gameLevel.gameLevel.setTextFill(Color.WHITE);

		gameLevel.setStyle("-fx-font-size: 48px;");

		// gameLevel.setTextAlignment(Pos.CENTER_LEFT);

		GridPane.setHalignment(gameLevel, HPos.CENTER);

		gamePane.add(gameLevel, (MAXCOLS / 2)+1, 0, 1, 1);

	}

	private void SetScore() {

		Label scoreSlogan = new Label("Score");
		scoreSlogan.setTextFill(Color.WHITE);
		scoreSlogan.setStyle("-fx-font-size:28px;");

		Label scoreLabel = new Label();
		scoreLabel.setText(Integer.toString(Score));
		scoreLabel.setTextFill(Color.BLUE);
		scoreLabel.setStyle("-fx-font-size:28px;");

		HBox hb = new HBox(20);
		hb.setAlignment(Pos.BASELINE_LEFT);
		hb.getChildren().addAll(scoreSlogan, scoreLabel);
		//hb.setLayoutY(20);

		gamePane.add(hb, MAXCOLS - 4, 1, 2, 1);

	}

	void updateButtons() {

		char[] letters1 = charArrayLower.toString().toCharArray();
		char[] letters2 = charArrayUpper.toString().toCharArray();

		for (int i = 0; i < availableLetters.size(); i++) {
			Button b = (Button) availableLetters.get(i);
			if (i < letters1.length) {
				char c = letters1[i];
				if (c == CharContainer.EMPTY_CHAR) {
					c = EmptyLabel;
				}
				b.setText(String.valueOf(c).toUpperCase());
			} else {
				b.setText(String.valueOf(EmptyLabel));
			}
		}
		for (int i = 0; i < availablePositions.size(); i++) {
			Button b = (Button) availablePositions.get(i);
			if (i < letters2.length) {
				char c = letters2[i];
				if (c == CharContainer.EMPTY_CHAR) {
					c = EmptyLabel;
				}
				b.setText(String.valueOf(c).toUpperCase());
			} else {
				b.setText(String.valueOf(EmptyLabel));
			}
		}
	}

	final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {
			Button x = (Button) event.getSource();

			for (int i = 0; i < availableLetters.size(); i++) {
				if (x == availableLetters.get(i)) {
					System.out.println("mphka1:" + event.getSource().toString());
					char c = charArrayLower.popLetter(i);
					if (c != CharContainer.EMPTY_CHAR) {
						charArrayUpper.pushLetter(c);
					}
					updateButtons();
					return;
				} else if (x == availablePositions.get(i)) {
					System.out.println("mphka2:" + event.getSource().toString());
					char c = charArrayUpper.popLetter(i);
					if (c != CharContainer.EMPTY_CHAR) {
						charArrayLower.pushLetter(c);
					}
					updateButtons();
					return;
				}

			}

			if (x == chckword) {
				System.out.println("check1" + event.getSource().toString());
			} else if (x == resetword) {
				System.out.println("check2" + event.getSource().toString());
			} else if (x == shuffleword) {
				charArrayLower.InitLetters(initialLetters);
				charArrayLower.ShuffleContainer();
				charArrayUpper.InitLetters("");
				updateButtons();
				System.out.println("check3" + event.getSource().toString());
			}

		}
	};

	private void setGridPaneRowsCols(GridPane gpane, int rows, int cols) {

		final int numCols = cols;
		final int numRows = rows;

		// create the buttons with the letters
		for (int i = 0; i < numCols; i++) {
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setHgrow(Priority.ALWAYS); // allow column to grow
			colConst.setFillWidth(true); //
			// colConst.setPercentWidth(100.0 / numCols);
			gpane.getColumnConstraints().add(colConst);
		}

		for (int i = 0; i < numRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setVgrow(Priority.ALWAYS); // allow row to grow
			rowConst.setFillHeight(true); // ask nodes to fill height for row
			// rowConst.setPercentHeight(100.0 / numRows);
			gpane.getRowConstraints().add(rowConst);
		}

	}

	private void createLetterSeqBut(String letters) {

		if (letters != null) {

			HBox availlettersbox = new HBox();
			HBox availposlettersbox = new HBox();

			charArrayLower = new CharContainer(letters);
			charArrayUpper = new CharContainer();
			initialLetters = letters;

			for (int i = 0; i < letters.length(); i++) {
	
				Button btn = createNewButton(letters.substring(i, i + 1));
				
				availableLetters.add(btn);

				availlettersbox.getChildren().add(btn);

				btn = createNewButton(String.valueOf(EmptyLabel));
				availablePositions.add(btn);

				availposlettersbox.getChildren().add(btn);
				

			}

			gamePane.add(availlettersbox, 4, (MAXROWS / 3) + 2, 7, 1);
			gamePane.add(availposlettersbox, 4, (MAXROWS / 3), 7, 1);

		}

	}

	private Button createNewButton(String slogan) {

		Button btn = new Button(slogan);
		btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


		GridPane.setHalignment(btn, HPos.CENTER);
		btn.setOnAction(myHandler);

		
		return btn;
	}

	private void setEffectShadow(Button button,Color color) {

		DropShadow shadow = new DropShadow();

		shadow.setColor(color);
		shadow.setRadius(50.0f);

		button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(shadow);
			}
		});

		button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				button.setEffect(null);
			}
		});

	}
	
	
	
	private Button createControlButton(String filepath,String tooltipslogan,Color color) {
		
		Button btn = null;
		
		if(!filepath.isEmpty() && !tooltipslogan.isEmpty())
		{
		
	      	Image img = new Image(getClass().getResourceAsStream(filepath));

	      	btn = new Button("", new ImageView(img));

	      	Tooltip tooltip = new Tooltip(tooltipslogan);

	      	tooltip.setFont(new Font("Arial", 16));

	      	btn.setTooltip(tooltip);

	      	btn.setId("check-button");

	      	btn.setOnAction(myHandler);

	      	setEffectShadow(btn,color);
		}
		
		
		return btn;
		
		
	}
	
	
	
	

}
