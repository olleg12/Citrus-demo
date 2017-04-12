package oleg;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.design.AbstractTestBehavior;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.config.annotation.HttpClientConfig;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;


/**
 * @author Oleg
 * @since 2017-04-11
 */
public class WeatherAPITest extends JUnit4CitrusTestDesigner {
    /*
     * HttpClient can be declared either in the test
     * or in xml and initialized via beans injection.
     * Tried to configure beans via java beans configuration,
     * but it didn't work out.
     * see citrus-context.xml for HttpClient bean
     */
    @CitrusEndpoint
    @HttpClientConfig(requestUrl = "http://api.openweathermap.org/")
    private HttpClient weatherClient;

    @Test
    @Ignore
    @CitrusTest
    /*
        This one is ignored due to fast weather change.
        But you showld look inside KyivWeatherJson to see some
        Citrus assertions
     */
    public void checkAllKyivWeatherJsonWithParam() {
        applyBehavior(new SendKyivWeatherQueryBehaviour());
        http()
                .client(weatherClient)
                //An xml-configured HttpClient
//                .client("weatherClient")
                .receive()
                .response(HttpStatus.OK)
                .payload(new ClassPathResource("KyivWeather.json"));
    }

    @Test
    @CitrusTest
    public void fieldsTest() {
        applyBehavior(new SendKyivWeatherQueryBehaviour());
        http()
                .client(weatherClient)
                .receive()
                .response(HttpStatus.OK)
                .validate("$.keySet()", "[city, cnt, cod, message, list]");
    }

    @Test
    @CitrusTest
    public void daylyForecastListSizeTest() {
        applyBehavior(new SendKyivWeatherQueryBehaviour());
        http()
                .client(weatherClient)
                .receive()
                .response(HttpStatus.OK)
                .validate("$.list.size()", "@greaterThan(${minLength})@")
                .validate("$.list.size()", "@lowerThan(${maxLength})@");
    }

    @Test
    @CitrusTest
    public void cityNameTest() {
        applyBehavior(new SendKyivWeatherQueryBehaviour());
        http()
                .client(weatherClient)
                .receive()
                .response(HttpStatus.OK)
                .validate("$.city.name", "@equalsIgnoreCase(Pushcha-Voditsa)@");

    }

    public class SendKyivWeatherQueryBehaviour extends AbstractTestBehavior {
        public void apply() {
            http()
                    .client(weatherClient)
                    .send()
                    .get("/data/2.5/forecast?q=Kyiv&mode=json&appid=ff040ba777566af18d15758740014695");
            variable("maxLength", 55);
            variable("minLength", 40);
        }
    }
}
