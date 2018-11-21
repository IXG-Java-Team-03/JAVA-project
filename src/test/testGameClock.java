package test;

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
	
	static final int NUMTESTS = 10;
	
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

	@Test
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

	@Test
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

}