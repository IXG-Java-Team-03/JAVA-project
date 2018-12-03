package helper.gameclock;

public class GameTimerThread extends Thread {
	
	private boolean timerRunning = false;
	private boolean timerPaused = false;
	private boolean recalculate = false;

	private final int timeoutValue;
	private final int interval;
	private final int timerNumber;
	private final timerCallback callerReference;
	private final GameTimer parent;
	
	private int counter;
	private long startTime=0;
	private long remainder=0;

	private final static int HEARTBEAT = 70;
	
	/**
	 * Constructor for the timer thread
	 * @param timeout The number of repetitions that will count for this counter
	 * @param interval The interval in milliseconds
	 * @param timerNumber The timer number 
	 * @param callerReference The reference to the calling procedure
	 * @param parent The timer pool object
	 */
	public GameTimerThread( int timeout, int interval, int timerNumber, timerCallback callerReference, GameTimer parent) {
		this.timeoutValue = Math.max( 0, timeout);           // zero is the minimum value
		this.interval = Math.max( 500, interval);			 // 500 ms is the minimum value
		this.callerReference = callerReference;
		this.timerNumber = timerNumber;
		this.parent = parent;
	}
	
	
	
	
	/**
	 * Thread run method
	 */
	@Override
	public void run() {
		
		timerRunning = true;
		counter 		= 0;
		startTime          		= System.currentTimeMillis();
		long CalculatedTimeout  = startTime + interval * timeoutValue;
		long CalculatedInterval = startTime + interval;
		
		callerReference.clockTick( counter, timeoutValue, timerNumber, interval);
		
		while( timerRunning) {
			
			try {
				Thread.sleep( HEARTBEAT );
			} 
			catch (InterruptedException e) {}
			
			if( timerPaused) {
				continue;
			}
			
			long time = System.currentTimeMillis();

			if( recalculate) {
				recalculate			= false;
				startTime			= time - remainder;		// recalculate the start time
				remainder			= 0;
				CalculatedTimeout	= startTime + interval * timeoutValue;		// recalculate the timeout value
				CalculatedInterval	= startTime + interval * (counter+1);		// get the next clock tick
				callerReference.clockRestarted( counter, timeoutValue, timerNumber, interval);
			}
			
			if( timerRunning && time >= CalculatedTimeout) {
				timerRunning = false;
				callerReference.clockExpired( timeoutValue, timeoutValue, timerNumber, interval);
				break;
			}
			
			if( timerRunning && time >= CalculatedInterval) {
				counter++;
				CalculatedInterval = startTime + interval * (counter+1);
				callerReference.clockTick( counter, timeoutValue, timerNumber, interval);
			}
		}
		parent.removeFromList( this);
	}
	
	
	

	/**
	 * Check if this thread is running.
	 * @param timerNumber1 The timer number
	 * @param callerReference1 The reference to the calling procedure
	 * @return true if the clock is running
	 */
	boolean isActiveThread( int timerNumber1, timerCallback callerReference1) {
		return  timerRunning && 
				this.timerNumber == timerNumber1 && 
				this.callerReference.equals(callerReference1);
	}
	
	

	
	/**
	 * Stop timer for this thread
	 */
	void StopTimerThread() {
		timerRunning = false;
		callerReference.clockStopped( counter, timeoutValue, timerNumber, interval);
	}
	
	
	
	
	
	/**
	 * Pause the active timer
	 */
	void PauseTimerThread() {
		timerPaused = true;
		recalculate = false;
		remainder = System.currentTimeMillis() - startTime;
		callerReference.clockPaused( counter, timeoutValue, timerNumber, interval);
	}
	
	
	
	
	/**
	 * Pause the active timer
	 */
	void RestartTimerThread() {
		timerPaused = false;
		recalculate = true;
	}
	
}

