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
	private int intervalCounter;
	private long startTime=0;
	private long remainder=0;

	private final static int HEARTBEAT = 70;
	
	/**
	 * Constructor for the timer thread
	 * @param timeout The number of repetitions that will count for this counter
	 * @param interval The interval in milliseconds
	 * @param timerNumber The timer number 
	 * @param callerReference The reference to the caller
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
	public void run() {
		
		timerRunning = true;
		counter 		= 0;
		intervalCounter = 1;
		startTime          		= System.currentTimeMillis();
		long CalculatedTimeout  = startTime + timeoutValue * interval;
		long CalculatedInterval = startTime + interval;
		
		callerReference.clockTick( counter, timeoutValue, timerNumber, interval);
		
		while( timerRunning) {
			
			try {
				Thread.sleep( HEARTBEAT );
			} catch (InterruptedException e) {}
			
			
			if( timerPaused) {
				continue;
			}
			
			long time = System.currentTimeMillis();

			if( recalculate) {
				recalculate			= false;
				intervalCounter		= 1;					// reset the interval counter to 1
				startTime			= time - remainder;		// this is the start of the paused interval
				remainder			= 0;
				CalculatedTimeout	= startTime + (timeoutValue - counter) * interval;
				CalculatedInterval	= startTime + interval;
				callerReference.clockRestarted( counter, timeoutValue, timerNumber, interval);
			}
			
			if( timerRunning && time >= CalculatedTimeout) {
				timerRunning = false;
				callerReference.clockExpired( timeoutValue, timeoutValue, timerNumber, interval);
				break;
			}
			
			if( timerRunning && time >= CalculatedInterval) {
				counter++;
				intervalCounter++;
				CalculatedInterval = startTime + intervalCounter * interval;
				callerReference.clockTick( counter, timeoutValue, timerNumber, interval);
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
		callerReference.clockStopped( counter, timeoutValue, timerNumber, interval);
	}
	
	
	
	
	
	/**
	 * Pause the active timer
	 */
	void PauseTimerThread() {
		timerPaused = true;
		recalculate = false;
		long diff = System.currentTimeMillis() - startTime;
		remainder = diff % interval;
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

