package helper.gameclock;

import java.util.ArrayList;



public class GameTimer {
	
	private final ArrayList<GameTimerThread> timerList;
	
	public GameTimer() {
		timerList = new ArrayList<GameTimerThread>();
	}

	private class GameTimerThread extends Thread {
		
		private final int timeoutValue;
		private final int interval;
		private int counter;
		private boolean timerRunning = false;
		private final timerCallback callbackFunc;
		
		GameTimerThread( int timeout, int interval, timerCallback callbackFunc) {
			counter = 0;
			timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
			this.interval = Math.max( 1, interval);			// one is the minimum value
			this.callbackFunc = callbackFunc;
		}
		
		public void run() {
			timerRunning = true;
			
			while( timerRunning) {
				try {					
					Thread.sleep( interval * 1000 );         // seconds to milliseconds
					checkClock();
					callbackFunc.clockTick(counter, timeoutValue);
				} catch (InterruptedException e) {
					timerRunning = false;
				}
			}
			callbackFunc.clockExpired();
			handleTimerExpiry( this);
		}
		
		
		public void StopTimer() {
			timerRunning = false;
			callbackFunc.clockStopped(counter, timeoutValue);
		}
		
		
		private void checkClock() throws InterruptedException {
			counter++;
			if( counter == timeoutValue) {
				throw new InterruptedException();
			}
		}
		
	}
	
	
	
	
	private void handleTimerExpiry( GameTimerThread timer) {
		timerList.remove(timer);
	}

	
	
	public void startTimer( int timeout, int interval, timerCallback callbackFunc) {
		GameTimerThread timer = new GameTimerThread( timeout, interval, callbackFunc);
		timerList.add( timer);
		timer.start();
	}
	
	
	
	public void stopAllTimers() {
		for( GameTimerThread timer : timerList) {
			timer.StopTimer();
		}
	}
	
}
