package helper.gameclock;

public interface timerCallback {
	
	public void clockTick( int currentValue, int timeoutValue, int timerNumber);
	
	public void clockExpired( int currentValue, int timeoutValue, int timerNumber);
	
	public void clockStopped( int currentValue, int timeoutValue, int timerNumber);
}
