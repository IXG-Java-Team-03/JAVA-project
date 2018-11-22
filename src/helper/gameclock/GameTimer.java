package helper.gameclock;

import java.util.ArrayList;


/**
 * This class handles timers for the game application.
 * Each class can start several timers - marked by a timer number.
 * @author nkot
 *
 */
public class GameTimer {
	
	private final ArrayList<GameTimerThread> timerList = new ArrayList<GameTimerThread>();
	
	
	
	//=================================================================================
	
	
	private class GameTimerThread extends Thread {
		
		private final int timeoutValue;
		private final int interval;
		private boolean timerRunning = false;
		private boolean timerPaused = false;
		private boolean recalculate = false;
		private long startTime=0;
		private long remainder=0;
		private int intervalCounter;
		private final timerCallback callerReference;
		private final int timerNumber;
		private int counter = 0;
		
		private final static int HEARTBEAT = 70;
		
		
		GameTimerThread( int timeout, int interval, int timerNumber, timerCallback callerReference) {
			this.timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
			this.interval = Math.max( 1, interval);			// one is the minimum value
			this.callerReference = callerReference;
			this.timerNumber = timerNumber;
		}
		
		
		
		
		/**
		 * Thread run method
		 */
		public void run() {
			
			timerRunning = true;
			intervalCounter = 1;
			startTime          		= System.currentTimeMillis();
			long CalculatedTimeout  = startTime + timeoutValue*interval*1000;
			long CalculatedInterval = startTime + interval*1000;
			
			while( timerRunning) {
				
				try {
					Thread.sleep( HEARTBEAT );
				} catch (InterruptedException e) {}
				
				
				if( timerPaused) {
					continue;
				}
				
				if( recalculate) {
					recalculate			= false;
					intervalCounter		= 1;
					startTime			= System.currentTimeMillis()-remainder;
					remainder			= 0;
					CalculatedTimeout	= startTime + (timeoutValue-counter)*interval*1000;
					CalculatedInterval	= startTime + interval*1000;
					callerReference.clockRestarted( counter, timeoutValue, timerNumber);
				}
				
				long time = System.currentTimeMillis();	
				if( timerRunning && time >= CalculatedTimeout) {
					timerRunning = false;
					callerReference.clockExpired( timeoutValue, timeoutValue, timerNumber);
					break;
				}
				if( timerRunning && time >= CalculatedInterval) {
					counter++;
					intervalCounter++;
					CalculatedInterval = startTime + intervalCounter*interval*1000;
					callerReference.clockTick( counter, timeoutValue, timerNumber);
				}
			}
			callerReference.clockStopped( counter, timeoutValue, timerNumber);
			removeFromList( this);
		}
		
		
		
		/**
		 * Stop timer for this thread
		 */
		private void StopTimerThread() {
			timerRunning = false;
		}
		
		

		/**
		 * Check if this thread is running.
		 * @param timerNumber
		 * @param callbackClass
		 * @return true if the clock is running
		 */
		private boolean isActiveThread( int timerNumber, timerCallback callbackClass) {
			return  timerNumber == this.timerNumber && 
					callbackClass == this.callerReference &&
					timerRunning;
		}
		
		
		
		
		
		/**
		 * Pause the active timer
		 */
		public void PauseTimerThread() {
			timerPaused = true;
			recalculate = false;
			long diff = System.currentTimeMillis() - startTime;
			remainder = diff%(interval*1000);
			callerReference.clockPaused( counter, timeoutValue, timerNumber);
		}
		
		
		
		
		
		/**
		 * Pause the active timer
		 */
		public void RestartTimerThread() {
			timerPaused = false;
			recalculate = true;
		}
		
		
		
	}
	
	
	
	
	//=================================================================================
	

	
	
	/**
	 * Notify the manager that the clock is stopped
	 * @param timer
	 */
	private void removeFromList( GameTimerThread timer) {
		synchronized( this) {
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
			if( timer.isActiveThread( timerNumber, callbackClass)) {
				return true;	// timer is active
			}
		}
		return false;		// timer is not active
	}


	
	
	
	/**
	 * Initiate a new clock if there is none already running
	 * @param timeout The duration of the timer
	 * @param interval The clock tick interval
	 * @param callbackClass The class reference that will receive the callback notifications
	 * @return true if the timer is started - false if not
	 */
	public boolean startTimer( int timeout, int interval, timerCallback callbackClass) {
		return startTimer( timeout, interval, 0, callbackClass);
	}
	
	
	
	
	
	/**
	 * Initiate a new clock if there is none already running
	 * @param timeout The duration of the timer
	 * @param interval The clock tick interval
	 * @param timerNumber The timer number distinguishing between independent timers 
	 * @param callbackClass The class reference that will receive the callback notifications
	 * @return true if the timer is started - false if not
	 */
	public boolean startTimer( int timeout, int interval, int timerNumber, timerCallback callbackClass) {
		synchronized( this) {
			if( isTimerRunning( timerNumber, callbackClass)) {
				return false;	// timer already active
			}
		}
		GameTimerThread timer = new GameTimerThread( timeout, interval, timerNumber, callbackClass);
		synchronized( this) {
			timerList.add( timer);
		}
		timer.setName( "Clock-" + timeout + "s-"+ timerNumber + "-" + callbackClass.hashCode());
		timer.start();
		return true;		// timer started
	}

	
	
	
	/**
	 * Stop the indicated clock 
	 * @param timerNumber
	 * @param callbackClass
	 */
	public boolean stopTimer( int timerNumber, timerCallback callbackClass) {
		for( GameTimerThread timer : timerList) {
			if( timer.isActiveThread( timerNumber, callbackClass)) {
				timer.StopTimerThread();
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	/**
	 * Stop the indicated clock 
	 * @param timerNumber
	 * @param callbackClass
	 */
	public boolean pauseTimer( int timerNumber, timerCallback callbackClass) {
		for( GameTimerThread timer : timerList) {
			if( timer.isActiveThread( timerNumber, callbackClass)) {
				timer.PauseTimerThread();
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	/**
	 * Stop the indicated clock 
	 * @param timerNumber
	 * @param callbackClass
	 */
	public boolean restartTimer( int timerNumber, timerCallback callbackClass) {
		for( GameTimerThread timer : timerList) {
			if( timer.isActiveThread( timerNumber, callbackClass)) {
				timer.RestartTimerThread();
				return true;
			}
		}
		return false;
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
		synchronized( this) {
			return timerList.size();  
		}
	}
	
	
	
}
