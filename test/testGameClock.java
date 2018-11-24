
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import helper.gameclock.GameTimer;
import helper.gameclock.GameTimerThread;
import helper.gameclock.timerCallback;


public class testGameClock {
	
	static GameTimer timer;
	
	static final int NUM_OF_TIMERS	   = 30;
	static final int HEARTBEAT_OFFSET  = 20; // ms
	static int MAX_JITTER;

	static boolean timeAnalysisFault = false;
	static int analysisFaultCount = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		timer = new GameTimer();

		try {
			GameTimerThread obj1 = new GameTimerThread( 4, 1, 0, null, null);
			Field val = obj1.getClass().getDeclaredField("HEARTBEAT");
			val.setAccessible(true);
		
			MAX_JITTER = val.getInt(obj1) + HEARTBEAT_OFFSET;		// HEARTBEAT + OFFSET
			obj1 = null;											// deallocate object
		} 
		catch ( NoSuchFieldException | SecurityException | 
				IllegalArgumentException | IllegalAccessException e) 
		{
			MAX_JITTER = 100;
		}
	}
	
	@Before
	public void setUp() throws Exception 
	{
		timeAnalysisFault = false;
		analysisFaultCount = 0;
	}
	
	
	class handler1 implements timerCallback {

		public int[] count1 = new int[NUM_OF_TIMERS];
		public boolean[] isActive = new boolean[NUM_OF_TIMERS];
		
		public long[] time1 = new long[NUM_OF_TIMERS];
		public long[] diff  = new long[NUM_OF_TIMERS];
		
		
		handler1() {
			for( int i=0; i<NUM_OF_TIMERS; i++ ) {
				count1[i] 	= 0;
				isActive[i] = false;
				time1[i]	= 0;
				diff[i]		= 0;
			}
		}

		@Override
		public void clockTick( int currentValue, int timeoutValue, int timerNumber, long interval) {
			synchronized( this) {
				count1[timerNumber] = currentValue;
				long time2 = System.currentTimeMillis();
				if( currentValue>0) {
					diff[timerNumber] = time2 - time1[timerNumber];
				}
				time1[timerNumber] = time2;
				if( currentValue>0 && (diff[timerNumber] > interval+MAX_JITTER || diff[timerNumber] < interval-MAX_JITTER) ) {
					timeAnalysisFault = true;
					analysisFaultCount++;
				}	
			}
//			System.out.format( "%-13s %-2d  h=%-12d  f=%-3d  counter=%-3d  diff=%-8d  int=%-4d  time=%d%n",
//					"Clock", timerNumber, this.hashCode(), analysisFaultCount, currentValue, 
//					diff[timerNumber], interval, time1[timerNumber] );
		}

		@Override
		public void clockExpired( int currentValue, int timeoutValue, int timerNumber, long interval) {
			synchronized( this) {
				isActive[timerNumber] = false;
				count1[timerNumber] = currentValue;
				long time2 = System.currentTimeMillis();
				diff[timerNumber] = time2 - time1[timerNumber];
				time1[timerNumber] = time2;
				if( currentValue>0 && (diff[timerNumber] > interval+MAX_JITTER || diff[timerNumber] < interval-MAX_JITTER) ) {
						timeAnalysisFault = true;
						analysisFaultCount++;
				}
			}
//			System.out.format( "%-13s %-2d  h=%-12d  f=%-3d  counter=%-3d  diff=%-8d  int=%-4d  time=%d%n",
//					"Clock end", timerNumber, this.hashCode(), analysisFaultCount, currentValue, 
//					diff[timerNumber], interval, time1[timerNumber] );
		}

		@Override
		public void clockStopped( int currentValue, int timeoutValue, int timerNumber, long interval) {
			synchronized( this) {
				long time2 = System.currentTimeMillis();
				diff[timerNumber] = time2 - time1[timerNumber];
				time1[timerNumber] = time2;
			}
//			System.out.format( "%-13s %-2d  h=%-12d  f=%-3d  counter=%-3d  diff=%-8d  int=%-4d  time=%d%n",
//					"Clock stop", timerNumber, this.hashCode(), analysisFaultCount, currentValue, 
//					diff[timerNumber], interval, time1[timerNumber] );
		}

		@Override
		public void clockPaused(int currentValue, int timeoutValue, int timerNumber, long interval) {
			synchronized( this) {
				long time2 = System.currentTimeMillis();
				diff[timerNumber] = time2 - time1[timerNumber];
				time1[timerNumber] = time2;
			}
//			System.out.format( "%-13s %-2d  h=%-12d  f=%-3d  counter=%-3d  diff=%-8d  int=%-4d  time=%d%n",
//					"Clock pause", timerNumber, this.hashCode(), analysisFaultCount, currentValue, 
//					diff[timerNumber], interval, time1[timerNumber] );
		}

		@Override
		public void clockRestarted(int currentValue, int timeoutValue, int timerNumber, long interval) {
			synchronized( this) {
				long time2 = System.currentTimeMillis();
				diff[timerNumber] = time2 - time1[timerNumber];
				time1[timerNumber] = time2;
			}
//			System.out.format( "%-13s %-2d  h=%-12d  f=%-3d  counter=%-3d  diff=%-8d  int=%-4d  time=%d%n",
//					"Clock restart", timerNumber, this.hashCode(), analysisFaultCount, currentValue, 
//					diff[timerNumber], interval, time1[timerNumber] );
		}
		
	}


	
	@Test
	public void test01_one_timer() {
		
		int pointer = 0;
		handler1 h1 = new handler1();
		
		h1.isActive[pointer] = true;		
		h1.time1[pointer] = System.currentTimeMillis();
		
		timer.startTimer( 10, h1);
		
		while ( h1.count1[pointer] < 10) {
			try {
				Thread.sleep( 900);
			} catch (InterruptedException e) {}
		}
		
		try { Thread.sleep( 300); } catch (InterruptedException e) {}
		
		if( !h1.isActive[pointer]) {
			if( timeAnalysisFault) {
				fail( "Clock jitter is higher than limits " + analysisFaultCount + " times");
			}
		}
		else {
			fail( "Clock is not stopped");
		}
	}

	
	
	
	
	
	@Test
	public void test02_one_timer_ds() {
		
		int pointer = 0;
		handler1 h1 = new handler1();
		
		h1.isActive[pointer] = true;		
		h1.time1[pointer] = System.currentTimeMillis();
		
		timer.startTimerDS( 20, 4, h1);
		
		while ( h1.count1[pointer] < 20) {
			try {
				Thread.sleep( 900);
			} catch (InterruptedException e) {}
		}
		
		try { Thread.sleep( 300); } catch (InterruptedException e) {}
		
		if( !h1.isActive[pointer]) {
			if( timeAnalysisFault) {
				fail( "Clock jitter is higher than limits " + analysisFaultCount + " times");
			}
		}
		else {
			fail( "Clock is not stopped");
		}
	}

	
	
	@Test
	public void test03_many_timers() {
		
		handler1 h1 = new handler1();
		handler1 h2 = new handler1();
		handler1 h3 = new handler1();
		handler1 h4 = new handler1();

		for( int i=0; i < NUM_OF_TIMERS; i++) {

			timer.startTimer( 10, 1, i, h1);
			synchronized( h1) {
				h1.isActive[i] = true;
				h1.time1[i] = System.currentTimeMillis();
			}
			try { Thread.sleep( 20 ); } catch (InterruptedException e) {}

			timer.startTimerDS( 20, 5, i, h2);
			synchronized( h2) {
				h2.isActive[i] = true;
				h2.time1[i] = System.currentTimeMillis();
			}
			try { Thread.sleep( 20 ); } catch (InterruptedException e) {}

			timer.startTimerDS( 10, 12, i, h3);
			synchronized( h3) {
				h3.isActive[i] = true;
				h3.time1[i] = System.currentTimeMillis();
			}
			try { Thread.sleep( 20 ); } catch (InterruptedException e) {}

			timer.startTimer( 4, 2, i, h4);
			synchronized( h4) {
				h4.isActive[i] = true;
				h4.time1[i] = System.currentTimeMillis();
			}
			try { Thread.sleep( 20 ); } catch (InterruptedException e) {}
		}
		
		while ( timer.getNumberOfActiveTimers() > 0 ) {
			try {
				Thread.sleep( 900);
			} catch (InterruptedException e) {
			}
		}
		
		try { Thread.sleep( 300); } catch (InterruptedException e) {}
		
		if( timeAnalysisFault) {
			fail( "Clock jitter is higher than limits " + analysisFaultCount + " times");
		}

	}
	
	
	
	
	@Test
	public void test04_simultaneous() {

		int pointer = 0;
		handler1 h1 = new handler1();
		
		boolean result1 = timer.startTimer( 10, 1, h1);
		try { Thread.sleep(100); } catch (InterruptedException e) {}

		boolean result2 = timer.startTimer( 10, 1, h1);
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		
		boolean result3 = timer.startTimer( 10, 1, 1, h1);
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		
		handler1 h2 = new handler1();
		boolean result4 = timer.startTimer( 10, 1, h2);
		try { Thread.sleep(100); } catch (InterruptedException e) {}

		assertTrue(  result1);		// first timer is active
		assertFalse( result2);		// second timer could not be started (same timer number)
		assertTrue(  result3);		// third timer is active
		assertTrue(  result4);		// fourth timer is active
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 3, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( pointer, h1);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 2, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( pointer, h1);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 2, timer.getNumberOfActiveTimers());
		
		timer.stopAllTimers();
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());
		
		timer.stopAllTimers();
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( pointer, h1);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());

		if( timeAnalysisFault) {
			fail( "Clock jitter is higher than limits " + analysisFaultCount + " times");
		}

	}
	

	
	@Test
	public void test05_pause_and_restart() {

		int pointer = 0;
		handler1 h1 = new handler1();

		boolean result1 = timer.startTimer( 10, 1, h1);

		timer.startTimerDS( 8, 8, 1, h1);
		timer.startTimer  ( 4, 2, 2, h1);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 1, h1.count1[pointer]);
		
		assertEquals( 3, timer.getNumberOfActiveTimers());

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);
		
		timer.pauseTimer( pointer, h1);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);

		timer.restartTimer( pointer, h1);

		try { Thread.sleep(1500); } catch (InterruptedException e) {}
		assertEquals( 3, h1.count1[pointer]);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);
		
		timer.pauseTimer( pointer, h1);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);

		timer.restartTimer( pointer, h1);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 5, h1.count1[pointer]);

		try { Thread.sleep(5600); } catch (InterruptedException e) {}
		assertEquals( 10, h1.count1[pointer]);
		
		result1 = timer.restartTimer( pointer, h1);
		assertFalse( result1);
		result1 = timer.pauseTimer( pointer, h1);
		assertFalse( result1);
		
		assertEquals( 0, timer.getNumberOfActiveTimers());

	}
	
	
	@Test
	public void test06_pause_and_restart_ds() {

		int pointer = 0;
		handler1 h1 = new handler1();

		boolean result1 = timer.startTimerDS( 10, 6, h1);
		
		timer.startTimerDS( 8, 8, 1, h1);
		timer.startTimer  ( 4, 2, 2, h1);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 1, h1.count1[pointer]);
		
		assertEquals( 3, timer.getNumberOfActiveTimers());

		try { Thread.sleep(610); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);
		
		timer.pauseTimer( pointer, h1);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, h1.count1[pointer]);

		timer.restartTimer( pointer, h1);

		try { Thread.sleep(650); } catch (InterruptedException e) {}
		assertEquals( 3, h1.count1[pointer]);

		try { Thread.sleep(610); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);
		
		timer.pauseTimer( pointer, h1);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, h1.count1[pointer]);

		timer.restartTimer( pointer, h1);

		try { Thread.sleep(610); } catch (InterruptedException e) {}
		assertEquals( 5, h1.count1[pointer]);

		try { Thread.sleep(4000); } catch (InterruptedException e) {}
		assertEquals( 10, h1.count1[pointer]);
		
		result1 = timer.restartTimer( pointer, h1);
		assertFalse( result1);
		result1 = timer.pauseTimer( pointer, h1);
		assertFalse( result1);
		
		assertEquals( 0, timer.getNumberOfActiveTimers());

	}
	

}
