package sk.kosickaakademia.kristian;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class Controller {
    @RequestMapping("/hello")
    public String getHello(){
        return "Hello there";
    }

    @RequestMapping("/time")
    public String getTime() { return new Date().toString(); }

    @RequestMapping("/response")
    public String getResponse() { return "General Kenobi"; }

    @RequestMapping("/hi/{username}")
    public String getHi(@PathVariable String username) { return "Hello there " + username; }

}