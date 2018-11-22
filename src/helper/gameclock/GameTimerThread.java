package helper.gameclock;

class GameTimerThread extends Thread {
	
	private boolean timerRunning = false;
	private boolean timerPaused = false;
	private boolean recalculate = false;

	private final int timeoutValue;
	private final int interval;
	private final int timerNumber;
	private final timerCallback callerReference;
	private final GameTimer parent;
	
	private int counter;
	private int intervalCounter;
	private long startTime=0;
	private long remainder=0;

	private final static int HEARTBEAT = 70;
	
	
	GameTimerThread( int timeout, int interval, int timerNumber, timerCallback callerReference, GameTimer parent) {
		this.timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
		this.interval = Math.max( 1, interval);			// one is the minimum value
		this.callerReference = callerReference;
		this.timerNumber = timerNumber;
		this.parent = parent;
	}
	
	
	
	
	/**
	 * Thread run method
	 */
	public void run() {
		
		timerRunning = true;
		counter 		= 0;
		intervalCounter = 1;
		startTime          		= System.currentTimeMillis();
		long CalculatedTimeout  = startTime + timeoutValue*interval*1000;
		long CalculatedInterval = startTime + interval*1000;
		
		callerReference.clockTick( counter, timeoutValue, timerNumber);
		
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
		parent.removeFromList( this);
	}
	
	
	

	/**
	 * Check if this thread is running.
	 * @param timerNumber
	 * @param callbackClass
	 * @return true if the clock is running
	 */
	boolean isActiveThread( int timerNumber, timerCallback callbackClass) {
		return  timerNumber == this.timerNumber && 
				callbackClass == this.callerReference &&
				timerRunning;
	}
	
	

	
	/**
	 * Stop timer for this thread
	 */
	void StopTimerThread() {
		timerRunning = false;
		callerReference.clockStopped( counter, timeoutValue, timerNumber);
	}
	
	
	
	
	
	/**
	 * Pause the active timer
	 */
	void PauseTimerThread() {
		timerPaused = true;
		recalculate = false;
		long diff = System.currentTimeMillis() - startTime;
		remainder = diff%(interval*1000);
		callerReference.clockPaused( counter, timeoutValue, timerNumber);
	}
	
	
	
	
	/**
	 * Pause the active timer
	 */
	void RestartTimerThread() {
		timerPaused = false;
		recalculate = true;
	}
	
}

