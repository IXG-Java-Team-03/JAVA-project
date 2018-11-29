package application;


import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class FillTrans {
	
	public static enum Color {GREEN, RED};
	
	
	private HashMap<Button,Timeline> timelinesforButtons;
	
	public FillTrans(Scene scene, String style) {
		
		
		scene.getStylesheets().add(getClass().getResource(style).toExternalForm());
		
		
		timelinesforButtons = new HashMap<Button,Timeline>();
		
	}
	
	
	public void flashOn(Button btn,Color color) {
		

		if(checkColor(color))
		{
		
		
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.millis(0), e -> btn.setId("Button-"+color.toString())),
	                new KeyFrame(Duration.millis(1000), e -> btn.setId(".button"))
	        );
			
			//perform animation forever until stop is issued
			timeline.setCycleCount(2);//Timeline.INDEFINITE);
			
			timeline.setAutoReverse(true);
			
			timeline.play();
			
			timelinesforButtons.put(btn, timeline);
		}
		
	}
	
	
	public void flashOn(ArrayList<Button> btns,Color color) {
		
		for(Button btn:btns)
			flashOn(btn,color);
	}
	
	
	
	public void flashOff(Button btn) {
		
		if(!timelinesforButtons.isEmpty())
		{
		
			Timeline timeline = timelinesforButtons.get(btn);
			
			//retrieve previous CSS property of Button 
			btn.setId(".button");
			
			
			timeline.stop();
		}
		
	}
	
	
	public void flashOff(ArrayList<Button> btns) {
		
		for(Button btn:btns)
			flashOff(btn);
	}
	
	
	private boolean checkColor(Color color) {
		
		for (Color c : Color.values()) {
	        if (c.equals(color)) {
	            return true;
	        }
	    }
		
		return false;
	}
	

}
