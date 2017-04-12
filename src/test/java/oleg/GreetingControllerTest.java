package oleg;

import com.consol.citrus.annotations.CitrusEndpoint;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.design.AbstractTestBehavior;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.config.annotation.HttpClientConfig;
import com.consol.citrus.validation.json.JsonMappingValidationCallback;
import hello.Greeting;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by okunets on 12.04.2017.
 */
public class GreetingControllerTest extends JUnit4CitrusTestDesigner {
    @CitrusEndpoint
    @HttpClientConfig(requestUrl = "http://localhost:8080/")
    private HttpClient greetingClient;

    @Test
    @CitrusTest
    public void  getGreetingNameTest(){
        applyBehavior(new SendGreetingQueryBehaviour());

        http()
                .client(greetingClient)
                .receive()
                .response(HttpStatus.OK)
                .validationCallback(new JsonMappingValidationCallback<Greeting>(Greeting.class) {
                    @Override
                    public void validate(Greeting greeting, Map<String, Object> map, TestContext testContext) {
                        assertEquals(greeting.getContent(),"Oleg");
                    }
                });


    }

    @Test
    @CitrusTest
    public void  getGreetingIdTest(){
        applyBehavior(new SendGreetingQueryBehaviour());

        http()
                .client(greetingClient)
                .receive()
                .response(HttpStatus.OK)
                .validationCallback(new JsonMappingValidationCallback<Greeting>(Greeting.class) {
                    @Override
                    public void validate(Greeting greeting, Map<String, Object> map, TestContext testContext) {
                        assertEquals(greeting.getId(),78);
                    }
                });


    }

    public class SendGreetingQueryBehaviour extends AbstractTestBehavior{

        @Override
        public void apply() {
            http()
                    .client(greetingClient)
                    .send()
                    .get("/greeting?name=Oleg");
        }
    }


}
