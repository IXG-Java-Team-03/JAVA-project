

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)

@SuiteClasses({ 
	CharContainerTest01.class,
	CharContainerTest02.class,
	WordSetTest.class,
	testValidateWords.class,
	testGameClock.class
})

public class AllTests {

}
