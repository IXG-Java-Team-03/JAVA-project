package helper.gameclock;

import java.util.ArrayList;


/**
 * This class handles timers for the game application.
 * Each class can start several timers - marked by a timer number.
 * @author nkot
 *
 */
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
		private final timerCallback callbackClass;
		private final int timerNumber;
		
		GameTimerThread( int timeout, int interval, int timerNumber, timerCallback callbackClass) {
			counter = 0;
			timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
			this.interval = Math.max( 1, interval);			// one is the minimum value
			this.callbackClass = callbackClass;
			this.timerNumber = timerNumber;
		}
		
		public void run() {
			timerRunning = true;
			
			while( timerRunning) {
				try {					
					Thread.sleep( interval * 1000 );        // seconds to milliseconds
					counter++;								// increase timer
					if( counter >= timeoutValue) {
						timerRunning = false;
						callbackClass.clockExpired( timerNumber);
						break;
					}
					callbackClass.clockTick(counter, timeoutValue, timerNumber);
				} catch (InterruptedException e) {
				}
			}
			handleTimerExpiry( this);
		}
		
		
		
		/**
		 * Stop timer for this thread
		 */
		private void StopTimer() {
			timerRunning = false;
			callbackClass.clockStopped(counter, timeoutValue, timerNumber);
		}
		

		

		/**
		 * Check if this thread is running.
		 * @param timerNumber
		 * @param callbackClass
		 * @return true if the clock is running
		 */
		private boolean isActive( int timerNumber, timerCallback callbackClass) {
			return timerNumber == this.timerNumber && callbackClass == this.callbackClass;
		}
	}
	
	
	

	/**
	 * Notify the manager that the clock is stopped
	 * @param timer
	 */
	private void handleTimerExpiry( GameTimerThread timer) {
		timerList.remove(timer);
	}
	
	
	
	
	/**
	 * Check if the clock is running
	 * @param timerNumber
	 * @param callbackClass
	 * @return true if the clock is running
	 */
	private boolean isTimerRunning( int timerNumber, timerCallback callbackClass) {
		for( GameTimerThread timer : timerList) {
			if( timer.isActive( timerNumber, callbackClass)) {
				return true;	// timer is active
			}
		}
		return false;		// timer is not active
	}


	
	
	/**
	 * Initiate a new clock if there is none already running
	 * @param timeout
	 * @param interval
	 * @param callbackClass
	 * @return true if the timer is started - false if not
	 */
	public boolean startTimer( int timeout, int interval, timerCallback callbackClass) {
		return startTimer( timeout, interval, 0, callbackClass);
	}
	
	
	
	
	
	/**
	 * Initiate a new clock if there is none already running
	 * @param timeout
	 * @param interval
	 * @param timerNumber
	 * @param callbackClass
	 * @return true if the timer is started - false if not
	 */
	public boolean startTimer( int timeout, int interval, int timerNumber, timerCallback callbackClass) {
		if( isTimerRunning( timerNumber, callbackClass)) {
			return false;	// timer already active
		}
		synchronized(this) {
			GameTimerThread timer = new GameTimerThread( timeout, interval, timerNumber, callbackClass);
			timerList.add( timer);
			timer.start();
		}
		return true;		// timer started
	}

	
	
	
	/**
	 * Stop the indicated clock 
	 * @param timerNumber
	 * @param callbackClass
	 */
	public void stopTimer( int timerNumber, timerCallback callbackClass) {
		for( GameTimerThread timer : timerList) {
			if( timer.isActive( timerNumber, callbackClass)) {
				synchronized(this) {
					timer.StopTimer();
				}
			}
		}
	}
	
	
	
	
	/**
	 * Stop all running timers
	 */
	public void stopAllTimers() {
		synchronized(this) {
			for( GameTimerThread timer : timerList) {
				timer.StopTimer();
			}
		}
	}
	
}
