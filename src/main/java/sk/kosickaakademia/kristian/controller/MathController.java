package sk.kosickaakademia.kristian.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {
    @PostMapping(path = "/add")
    public String addTwoNumbers(@RequestBody String input){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(input);
            int a = Integer.parseInt(String.valueOf(object.get("a")));
            int b = Integer.parseInt(String.valueOf(object.get("b")));
            System.out.println(a + b);
        } catch (ParseException e) { e.printStackTrace(); }
        return null;
    }

}
