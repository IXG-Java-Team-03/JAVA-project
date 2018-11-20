package helper.gameclock;

public interface timerCallback {
	
	public void clockTick( int currentValue, int timeoutValue);
	
	public void clockExpired();
	
	public void clockStopped( int currentValue, int timeoutValue);
}
