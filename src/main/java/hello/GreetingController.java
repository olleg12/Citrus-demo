package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public ResponseEntity<Greeting> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Greeting greeting=new Greeting(78,name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Operation","sayHello");
        return ResponseEntity.ok()
                .headers(headers)
                .body(greeting);
    }
}
