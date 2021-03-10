package sk.kosickaakademia.kristian.controller;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getRoot() { return "I am groot"; }

    @RequestMapping(path = "/data", method = RequestMethod.POST)
    public String getData(@RequestBody String name) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(name);

        return "Hello there! " + object.get("username");
    }

//    @RequestMapping ("/user/id/{id}")
//    public String getUserById(@PathVariable String id) {
//
//    }

}