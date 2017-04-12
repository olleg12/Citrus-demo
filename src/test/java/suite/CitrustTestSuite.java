package suite;

import oleg.GreetingControllerTest;
import oleg.WeatherAPITest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by okunets on 12.04.2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GreetingControllerTest.class,
        WeatherAPITest.class
})
public class CitrustTestSuite {
}
