
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import helper.gameclock.GameTimer;
import helper.gameclock.timerCallback;

public class testGameClock {
	
	static GameTimer timer;
	
	static final int NUMTESTS = 20;
	
	static int[] count1 = new int[NUMTESTS];
	static boolean[] isActive = new boolean[NUMTESTS];
	
	static long[] time1 = new long[NUMTESTS];
	static long[] diff = new long[NUMTESTS];
	
	static int interval = 1;
	static int maxJitter = 100;	// maximum acceptable offset (milliseconds)
	static boolean timeAnalysisFault = false;
	static int analysisFaultCount = 0;
	
	class handler1 implements timerCallback {

		@Override
		public void clockTick( int currentValue, int timeoutValue, int timerNumber) {
			count1[timerNumber] = currentValue;
			long time2 = System.currentTimeMillis();
			diff[timerNumber] = time2 - time1[timerNumber];
			time1[timerNumber] = time2;
			if( diff[timerNumber] > interval*1000+maxJitter || diff[timerNumber] < interval*1000-maxJitter ) {
				timeAnalysisFault = true;
				analysisFaultCount++;
			}	
//			System.out.println( "Clock " + timerNumber + 
//					"  c=" + currentValue + 
//					" diff=" + diff[timerNumber] +
//					" time=" + time1[timerNumber]);
		}

		@Override
		public void clockExpired( int currentValue, int timeoutValue, int timerNumber) {
			isActive[timerNumber] = false;
			count1[timerNumber] = currentValue;
			long time2 = System.currentTimeMillis();
			diff[timerNumber] = time2 - time1[timerNumber];
			time1[timerNumber] = time2;
			if( diff[timerNumber] > interval*1000+maxJitter || diff[timerNumber] < interval*1000-maxJitter ) {
					timeAnalysisFault = true;
					analysisFaultCount++;
			}	
//			System.out.println( "Clock end " + timerNumber + 
//					"  c=" + currentValue + 
//					" diff=" + diff[timerNumber] +
//					" time=" + time1[timerNumber]);
		}

		@Override
		public void clockStopped( int currentValue, int timeoutValue, int timerNumber) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clockPaused(int currentValue, int timeoutValue, int timerNumber) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void clockRestarted(int currentValue, int timeoutValue, int timerNumber) {
			// TODO Auto-generated method stub
			
		}
		
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		timer = new GameTimer();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
//	@Test
	public void test01() {
		
		isActive[0] = true;
		timeAnalysisFault = false;
		
		time1[0] = System.currentTimeMillis();
		timer.startTimer( 10, 1, new handler1());
		while ( count1[0] < 10) {
			try {
				Thread.sleep( 900);
			} catch (InterruptedException e) {
			}
		}
		if( !isActive[0]) {
			if( timeAnalysisFault) {
				fail( "Clock jitter is higher than limits");
			}
			else {
				assertTrue( true);
			}
		}
		else {
			fail( "Clock is not stopped");
		}
	}

	
	
//	@Test
	public void test02() {
		
		timeAnalysisFault = false;
		
		handler1 h = new handler1();
		for( int i=0; i<NUMTESTS; i++) {
			timer.startTimer( 10, 1, i, h);
			isActive[i] = true;
			time1[i] = System.currentTimeMillis();
		}
		
		while ( timer.getNumberOfActiveTimers() > 0 ) {
			try {
				Thread.sleep( 900);
			} catch (InterruptedException e) {
			}
		}
		if( timeAnalysisFault) {
			fail( "Clock jitter is higher than limits " + analysisFaultCount + " times");
		}
		else {
			assertTrue( true);
		}
	}
	
	
//	@Test
	public void test03() {

		handler1 h = new handler1();
		
		boolean result1 = timer.startTimer( 10, 1, h);
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		
		boolean result2 = timer.startTimer( 10, 1, h);
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		
		boolean result3 = timer.startTimer( 10, 1, 1, h);
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		
		handler1 h2 = new handler1();
		boolean result4 = timer.startTimer( 10, 1, h2);
		try { Thread.sleep(100); } catch (InterruptedException e) {}

		assertTrue(  result1);
		assertFalse( result2);
		assertTrue(  result3);
		assertTrue(  result4);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 3, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( 0, h);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 2, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( 0, h);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 2, timer.getNumberOfActiveTimers());
		
		timer.stopAllTimers();
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());
		
		timer.stopAllTimers();
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());
		
		timer.stopTimer( 0, h);
		
		try { Thread.sleep(100); } catch (InterruptedException e) {}
		assertEquals( 0, timer.getNumberOfActiveTimers());
	}
	
	
	@Test
	public void test04() {

		handler1 h = new handler1();

		boolean result1 = timer.startTimer( 10, 1, h);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 1, count1[0]);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 2, count1[0]);
		
		timer.pauseTimer( 0, h);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, count1[0]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 2, count1[0]);

		timer.restartTimer( 0, h);

		try { Thread.sleep(1500); } catch (InterruptedException e) {}
		assertEquals( 3, count1[0]);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 4, count1[0]);
		
		timer.pauseTimer( 0, h);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, count1[0]);

		try { Thread.sleep(2100); } catch (InterruptedException e) {}
		assertEquals( 4, count1[0]);

		timer.restartTimer( 0, h);

		try { Thread.sleep(1100); } catch (InterruptedException e) {}
		assertEquals( 5, count1[0]);

		try { Thread.sleep(5600); } catch (InterruptedException e) {}
		assertEquals( 10, count1[0]);
		
		result1 = timer.restartTimer( 0, h);
		assertFalse( result1);
		result1 = timer.pauseTimer( 0, h);
		assertFalse( result1);
		
		assertEquals( 0, timer.getNumberOfActiveTimers());
	}
	
	
}
