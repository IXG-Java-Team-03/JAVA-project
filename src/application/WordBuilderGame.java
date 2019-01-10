package application;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;

import com.sun.javafx.application.LauncherImpl;

import helper.InvalidWordException;
import helper.gameclock.GameTimer;
import helper.gameclock.timerCallback;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
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






/**
 * hdfghdfghdfgh
 * @author soco
 *
 */
public class WordBuilderGame extends Application implements timerCallback {

	private final static String className = MethodHandles.lookup().lookupClass().getSimpleName();
	private final static appLogger logger = new appLogger( className, null);

	private final static Button startGame = new Button("Start Playing");
	private final static Button quitGame = new Button("Quit");


	private final static String language ="GR";

	private final static int MINLETTERS = 2;
	private final static int MAXLETTERS = 5;


	private final static int MAXROWS = 15;
	private final static int MAXCOLS = 15;
	
	private final static int NOFDEFAULTWORDSCURLEVEL = 4;
	
	private Label scoreLabel;


	private String pickedWord;
	
	/**
	 * Words that can be constructed, using letters of pickedWord
	 */
	
	private ArrayList<String> foundWords;
	
	
	/**
	 * Instance of a timer object
	 */
	private final GameTimer timer = new GameTimer();
	private Label timerLabel = null;
	
	

	/**
	 *  Just a counter to create some delay while showing preloader.
	 */
	private static final int COUNT_LIMIT = 5000;

	private BorderPane root;

	public static int CurrentLevel;

	private Scene scene;

	private static int Score;

	private Button chckword, resetword, shuffleword,nextlevel;

	private GridPane gamePane;

	private ArrayList<Button> availableLetters, availablePositions;
	
	public static WordBuilderGame selfReference;
	
	/**
	 * Instance of FillTrans class that will be used when a word is invalid
	 */
	private FillTrans ft;
	

//	/**
//	 * organized words in arraylist of different sizes
//	 */
//	private HashMap<Integer, ArrayList<String>> wdb;



	/**
	 * Container for the upper row
	 */
	public CharContainer charArrayUpper = new CharContainer();
	/**
	 * Container for the lower row
	 */
	public CharContainer charArrayLower = new CharContainer();
	/**
	 * Char indicating an empty position
	 */
	public static final char EmptyLabel = '_';

	public String initialLetters;

	private Stage applicationStage;
	public ListView<String> wordsList;


	
	
	
	
	
	/*********************************************************************
	 * 
	 * @return
	 */
	private HBox createTimer() {

		Properties	params			= GameMethods.getLevelParameters(CurrentLevel);
		int			TimerValue		= GameMethods.getIntegerProperty(params, "Timer", 60);
		int			TimerInterval	= GameMethods.getIntegerProperty(params, "TimerInterval", 1);
		String		TimerUnit		= GameMethods.getStringProperty(params, "TimerUnit", "sec");
		
		if (TimerValue != 0) {
			Label timeSlogan = new Label("Time");
			timeSlogan.setTextFill(Color.WHITE);
			timeSlogan.setStyle("-fx-font-size:28px;");


			timerLabel = new Label();
			timerLabel.setTextFill(Color.BLUE);
			timerLabel.setStyle("-fx-font-size:28px;");
			timerLabel.setText( String.valueOf(TimerValue));
			
			if( TimerUnit.equals( "sec")) {
				timer.startTimer( TimerValue, TimerInterval, this);
			}
			else {
				timer.startTimerDS( TimerValue, TimerInterval, this);
			}

			HBox hb = new HBox(20);
			hb.setAlignment(Pos.BASELINE_LEFT);
			hb.getChildren().addAll(timeSlogan, timerLabel);
//			hb.setLayoutY(20);

			return hb;

		}

		return null;

	}

	
	
	
//	/*********************************************************************
//	 * 
//	 * @param value
//	 */
//	public static void setStartTime(int value) {
//
//		STARTTIME = value;
//
//	}
//	
//



	/*********************************************************************
	 * 
	 * We will normally need to pick-up a word that will give us the chance to create more words
	 * of different sizes, with max size the size of the word initially picked up
	 * 
	 */
	private void pickUpWord() {

		logger.entering( className, "pickUpWord");
	
		/**
		 * creates a new wset with greek words
		 */
		WordSet wset = new WordSet(language, MINLETTERS, MAXLETTERS, "words");

		// get the whole set of words
		HashMap<Integer, ArrayList<String>> wdb = wset.getWordsDB();

		ArrayList<String> listofMaxletters = wdb.get(MAXLETTERS);

		Random rand = new Random();

		/**
		 * random position inside ArrayList
		 */
		int randomidx = rand.nextInt(listofMaxletters.size() + 1);

		/**
		 *  shuffle word letters
		 */
		pickedWord = shuffleWord(listofMaxletters.get(randomidx));
		
		
		//extract all words that can be constructed with "pickedWord" letters
		foundWords = wset.AssembleWordGameSet(pickedWord);

		logger.exiting( className, "pickUpWord");

	}



	/*********************************************************************
	 * 
	 * @param word
	 * @return
	 */
	private String shuffleWord(String word) {

		logger.entering( className, "shuffleWord");

		char[] wordofChars = word.toCharArray();

		Random rand = new Random();

		for (int i = wordofChars.length - 1; i > 0; i--) {
			int r = rand.nextInt(i + 1);
			char tmp = wordofChars[i];
			wordofChars[i] = wordofChars[r];
			wordofChars[r] = tmp;
		}

		logger.exiting( className, "shuffleWord");

		return String.valueOf(wordofChars);
	}






	/*********************************************************************
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// thes ena thread gia na kaleis afto
		LauncherImpl.launchApplication(WordBuilderGame.class, GamePreloader.class, args);
	}





	/*********************************************************************
	 * 
	 */
	public WordBuilderGame() {
		
		logger.entering( className, "WordBuilderGame constructor");
		
		// Constructor is called after BEFORE_LOAD.
		logger.info( "MyApplication constructor called");
		// Create down BorderPane
		root = new BorderPane();
		availableLetters = new ArrayList<Button>();
		availablePositions = new ArrayList<Button>();
		CurrentLevel = 0;
		
		Score = 0;

		selfReference = this;
		
		
		
		
		startGame.setId("default-button");
		quitGame.setId("default-button");

		startGame.setPrefWidth(200.0);
		quitGame.setPrefWidth(200.0);
		
		//This is the Score
		scoreLabel = new Label();

		logger.exiting( className, "WordBuilderGame constructor");
	}
	
	

	
	/*********************************************************************
	 * 
	 * 
	 */
	@Override
	public void init() throws Exception {
		
		logger.entering( className, "init");
		logger.info( "MyApplication#init (doing some heavy lifting)");
		

		pickUpWord();

		// Perform some heavy lifting (i.e. database start, check for application
		// updates, etc. )
		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (100 * i) / COUNT_LIMIT;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));

		}
		logger.exiting( className, "init");
	}

	
	
	
	
	/*********************************************************************
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		logger.entering( className, "start");
		logger.info( "MyApplication#start (initialize and show primary application stage)");

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
			
			ft = new FillTrans(scene,"application.css");
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		handleButtonAction(startGame);
		handleButtonAction(quitGame);

		primaryStage.setOnCloseRequest(event -> {
			timer.stopAllTimers();
		});

		logger.exiting( className, "start");
	}

	
	
	
	/*********************************************************************
	 * 
	 * @param btn
	 */
	public void handleButtonAction(Button btn) {

		logger.entering( className, "handleButtonAction");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				switch (btn.getText()) {

				case "Start Playing":
					
					createLevel(NOFDEFAULTWORDSCURLEVEL);
					
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
		logger.exiting( className, "handleButtonAction");
	}

	
	
	
	/*********************************************************************
	 * 
	 * @param numofWordstoFound
	 * @return
	 */
	private String[] maxWordsForCurrentLevel(int numofWordstoFound) {
		
		int maxsize = 0;
		
		if( foundWords.size() < numofWordstoFound)
			maxsize = foundWords.size();
		else
			maxsize = numofWordstoFound;
		
		return Arrays.copyOfRange(foundWords.toArray(new String[0]),0,maxsize);
	}
	
	
	
	
	
	
	/*********************************************************************
	 * Replaces all letters with underscores in all words in a String array
	 * @author xigvaag
	 * @param wordsToShow String array with all the letters
	 * @return String array with all letters replaced by underscore
	 */
	private String[] dashReplace(String[] wordsToShow) {

		String[] dashWords = new String[wordsToShow.length];

		for (int i = 0; i < wordsToShow.length; i++) {
			dashWords[i] = wordsToShow[i].replaceAll( ".", "_");
		}

		return dashWords;
	}
	
	
	
	
	
	
	/*********************************************************************
	 * 
	 * @param numofWordstoFound
	 */
	private void createLevel(int numofWordstoFound) {
		
		
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
		SetScore();

		// on the left we will need a list of words to be found
		wordsList = new ListView<String>();

		// ***********************This is just an example**************
		// here we will call a function that will return the found words
		// with a dash replacing each word letter
		//String[] wordsToShowUp = dashReplace(maxWordsForCurrentLevel(numofWordstoFound));
		

		//wordsList.setItems(FXCollections.observableArrayList(wordsToShowUp));
		// wordsList.setBackground(value);

		wordsList.setMouseTransparent(true);
		wordsList.setFocusTraversable(false);

		gamePane.add(wordsList, 0, 2, 3, MAXROWS - 2); // spans 1 column, 4 rows

		// create and add the timer
		HBox hbtimer = createTimer();
		//hbtimer.setPadding(new Insets(10));

		gamePane.add(hbtimer, MAXCOLS - 4, 2, 2, 1); // spans 2 columns and 2 rows (the last two elements)

		SetScoreLabelData();

		createLetterSeqBut(pickedWord);

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
		
		
	}
	
	
	
	/*********************************************************************
	 * 
	 */
	private void SetGameLevel() {

		logger.entering( className, "SetGameLevel");

		++CurrentLevel;

		Text gameLevel = new Text("Level " + CurrentLevel);

		gameLevel.setId("gamelevel");

		// gameLevel.gameLevel.setTextFill(Color.WHITE);

		gameLevel.setStyle("-fx-font-size: 48px;");

		// gameLevel.setTextAlignment(Pos.CENTER_LEFT);

		GridPane.setHalignment(gameLevel, HPos.CENTER);

		gamePane.add(gameLevel, (MAXCOLS / 2)+1, 0, 1, 1);

		logger.exiting( className, "SetGameLevel");
	}

	
	
	/*********************************************************************
	 * 
	 * @author Aris
	 * @param value
	 */
	public static void addScore( int value) {
		Score += value;
		selfReference.SetScore();
	}
	
	
	
	
	/*********************************************************************
	 * 
	 * @author Aris
	 */
	public void SetScore() {
		
		scoreLabel.setText(Integer.toString(Score));
	}
	
	
	
	
	/*********************************************************************
	 * 
	 */
	private void SetScoreLabelData( ) {

		logger.entering( className, "SetScore");

		Label scoreSlogan = new Label("Score");
		scoreSlogan.setTextFill(Color.WHITE);
		scoreSlogan.setStyle("-fx-font-size:28px;");

		//Label scoreLabel = new Label();
		
		scoreLabel.setTextFill(Color.BLUE);
		scoreLabel.setStyle("-fx-font-size:28px;");

		HBox hb = new HBox(20);
		hb.setAlignment(Pos.BASELINE_LEFT);
		hb.getChildren().addAll(scoreSlogan, scoreLabel);
		//hb.setLayoutY(20);

		gamePane.add(hb, MAXCOLS - 4, 1, 2, 1);

		logger.exiting( className, "SetScore");
	}

	
	
	/*********************************************************************
	 * 
	 * @author Nikos
	 */
	void updateButtons() {

		logger.entering( className, "updateButtons");

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
		
		//it is a good place to call flashOff since even there is no flash
		//of available positions, this method guarantees the call safety
		ft.flashOff(availablePositions);
		
		logger.exiting( className, "updateButtons");
	}




	/*********************************************************************
	 * 
	 * @author Soco + others
	 */
	final EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(final ActionEvent event) {

			logger.entering( className, "buttons event handler");

			Button x = (Button) event.getSource();

			for (int i = 0; i < availableLetters.size(); i++) {
				if (x == availableLetters.get(i)) {
					logger.log( Level.INFO, "Lower row letter pushed {0}", event.getSource().toString());
					char c = charArrayLower.popLetter(i);
					if (c != CharContainer.EMPTY_CHAR) {
						charArrayUpper.pushLetter(c);
					}
					updateButtons();
					logger.exiting( className, "buttons event handler");
					return;
				} else if (x == availablePositions.get(i)) {
					logger.log( Level.INFO, "Upper row letter pushed {0}", event.getSource().toString());
					char c = charArrayUpper.popLetter(i);
					if (c != CharContainer.EMPTY_CHAR) {
						charArrayLower.pushLetter(c);
					}
					updateButtons();
					logger.exiting( className, "buttons event handler");
					return;
				}

			}

			if (x == chckword) {
				//validate word - aris
				String word = charArrayUpper.toString();

				String wordForSearch;
				try {
					wordForSearch = validateWords.isValidWord(word);
					
					validateWords.searchInArrayList(foundWords,wordForSearch);
					
				}
				catch (InvalidWordException ex) {
					wordForSearch = "";
					logger.throwing( className, "buttons event handler", ex);

					////////////////////////////////////////////////////////////////////////////
					// TODO: EDW MPOREI NA FLASH KOKKINO STA BUTTONS
					////////////////////////////////////////////////////////////////////////////
					
					
					ft.flashOn(availablePositions,FillTrans.Color.RED);
					
				}

				logger.log( Level.INFO, "check1 {0}", event.getSource().toString());

			} else if (x == resetword) {
				// TODO : IMPLEMENT RESET BUTTON
				
				charArrayLower.InitLetters(initialLetters);
				charArrayUpper.InitLetters("");
				updateButtons();
				ft.flashOff(availablePositions);
				
				logger.log( Level.INFO, "Reset word {0}", event.getSource().toString());

			} else if (x == shuffleword) {
				charArrayLower.InitLetters(initialLetters);
				charArrayLower.ShuffleContainer();
				charArrayUpper.InitLetters("");
				updateButtons();
				
				logger.log( Level.INFO, "Shuffle letters pressed {0}", event.getSource().toString());
			}

			logger.exiting( className, "buttons event handler");
		}
	};




	/*********************************************************************
	 * 
	 * @author soco
	 * @param gpane
	 * @param rows
	 * @param cols
	 */
	private void setGridPaneRowsCols(GridPane gpane, int rows, int cols) {

		logger.entering( className, "setGridPaneRowsCols");

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

		logger.exiting( className, "setGridPaneRowsCols");
	}




	/*********************************************************************
	 * 
	 * @author soco
	 * @param letters
	 */
	private void createLetterSeqBut(String letters) {

		logger.entering( className, "createLetterSeqBut");

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

		logger.exiting( className, "createLetterSeqBut");
	}

	
	
	/*********************************************************************
	 * 
	 * @author soco
	 * @param slogan
	 * @return
	 */
	private Button createNewButton(String slogan) {

		Button btn = new Button(slogan);
		btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


		GridPane.setHalignment(btn, HPos.CENTER);
		btn.setOnAction(myHandler);


		return btn;
	}

	
	
	/*********************************************************************
	 * 
	 * @author soco
	 * @param button
	 * @param color
	 */
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



	/*********************************************************************
	 * 
	 * @author soco
	 * @param filepath
	 * @param tooltipslogan
	 * @param color
	 * @return
	 */
	private Button createControlButton(String filepath,String tooltipslogan,Color color) {

		Button btn = null;

		if(!filepath.isEmpty() && !tooltipslogan.isEmpty())
		{

			//FIXME : Overlapping of glow areas around buttons 
			
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

	/*********************************************************************
	 * 
	 */
	@Override
	public void clockTick( int currentValue, int timeoutValue, int timerNumber, long interval) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	timerLabel.setText( String.valueOf( timeoutValue-currentValue));
		    }
		});
	}

	/*********************************************************************
	 * 
	 */
	@Override
	public void clockExpired( int currentValue, int timeoutValue, int timerNumber, long interval) {
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	timerLabel.setText( String.valueOf( timeoutValue-currentValue));
		    }
		});
		
		// TODO : Add actions for timer expiry
		
//		Properties params = GameMethods.getLevelParameters( CurrentLevel);
//		int PointsForNextLevel = getIntegerProperty( params, "NextLevel", 100);

	}

	/*********************************************************************
	 * 
	 */
	@Override
	public void clockStopped( int currentValue, int timeoutValue, int timerNumber, long interval) {
		
	}

	/*********************************************************************
	 * 
	 */
	@Override
	public void clockPaused(int currentValue, int timeoutValue, int timerNumber, long interval) {
		
	}

	/*********************************************************************
	 * 
	 */
	@Override
	public void clockRestarted(int currentValue, int timeoutValue, int timerNumber, long interval) {
		
	}



	/*********************************************************************
	 * This method is used by Jubula in order to get the selected word and the randomized letters
	 * @author Nikos
	 * @param index The word index in the array
	 * @return The concatentation of the selected word and the randomized letters separated by one space
	 */
	public static String getSelectedWords(int index) {
		if( selfReference.foundWords.size() == 0 ) {
			return "";
		}
		index = Math.min( index, selfReference.foundWords.size()-1);
		return selfReference.foundWords.get(index) + CharContainer.WORD_SEPARATOR
				+ selfReference.charArrayLower.toString();
	}

	/*********************************************************************
	 * This method is used by Jubula in order to find out which button to press for the test case 
	 * @author Nikos
	 * @param word The selected word and the randomized letters, separated by one space
	 * @param index The letter index in the original word 
	 * @return The letter index in the randomized word for the specific original letter index
	 */
	public static String getLetterIndex( String word, int index) {
		return String.valueOf( CharContainer.getLetterIndex(word, index));
	}


}
