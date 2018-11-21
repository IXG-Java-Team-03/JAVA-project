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
	private final GameTimer selfReference;
	
	public GameTimer() {
		timerList = new ArrayList<GameTimerThread>();
		selfReference = this;
	}

	
	
	
	private class GameTimerThread extends Thread {
		
		private final int timeoutValue;
		private final int interval;
		private int counter;
		private boolean timerRunning = false;
		private final timerCallback callbackClass;
		private final int timerNumber;
		
		private final static int HEARTBEAT = 70;
		
		private long CalculatedTimeout;
		private long CalculatedInterval;
		
		
		GameTimerThread( int timeout, int interval, int timerNumber, timerCallback callbackClass) {
			counter = 0;
			timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
			this.interval = Math.max( 1, interval);			// one is the minimum value
			this.callbackClass = callbackClass;
			this.timerNumber = timerNumber;
		}
		
		
		/**
		 * Thread run method
		 */
		public void run() {
			
			timerRunning = true;
			long startTime     = System.currentTimeMillis();
			CalculatedTimeout  = startTime + timeoutValue*interval*1000;
			CalculatedInterval = startTime + interval*1000;

			while( timerRunning) {
				
				try {
					Thread.sleep( HEARTBEAT );
				} catch (InterruptedException e) {}
				
				boolean flag1 = false;
				boolean flag2 = false;
				
				synchronized( selfReference) {
					long time = System.currentTimeMillis();	
					if( time >= CalculatedInterval) {
						counter++;
						flag1 = true;
						CalculatedInterval = startTime + (counter+1)*interval*1000;
					}
					if( time >= CalculatedTimeout) {
						timerRunning = false;
						flag2 = true;
						flag1 = false;
					}
				}
				if( flag1) {
					callbackClass.clockTick( counter, timeoutValue, timerNumber);
				}
				if( flag2) {
					callbackClass.clockExpired( timeoutValue, timeoutValue, timerNumber);
				}
			}
			removeFromList( this);
		}
		
		
		
		/**
		 * Stop timer for this thread
		 */
		private void StopTimerThread() {
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
			return  timerNumber == this.timerNumber && 
					callbackClass == this.callbackClass &&
					timerRunning;
		}
	}
	
	
	

	/**
	 * Notify the manager that the clock is stopped
	 * @param timer
	 */
	private void removeFromList( GameTimerThread timer) {
		synchronized( selfReference) {
			timerList.remove(timer);
		}
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
		synchronized( selfReference) {
			GameTimerThread timer = new GameTimerThread( timeout, interval, timerNumber, callbackClass);
			timerList.add( timer);
			timer.setName( "Clock-" + timeout + "s-"+ timerNumber + "-" + callbackClass.hashCode());
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
				timer.StopTimerThread();
			}
		}
	}
	
	
	
	
	/**
	 * Stop all running timers
	 */
	public void stopAllTimers() {
		for( GameTimerThread timer : timerList) {
			timer.StopTimerThread();
		}
	}
	
	
	
	/**
	 * Gets the number of active timers
	 * @return
	 */
	public int getNumberOfActiveTimers() {
		return timerList.size();
	}
	
}
