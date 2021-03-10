package sk.kosickaakademia.kristian.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MathController {
    @PostMapping(path = "/add")
    public ResponseEntity<String> addTwoNumbers(@RequestBody String input){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(input);
            int a = Integer.parseInt(String.valueOf(object.get("a")));
            int b = Integer.parseInt(String.valueOf(object.get("b")));
            int result = a + b;
            JSONObject resultObject = new JSONObject();
            resultObject.put("result", result);
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(resultObject.toJSONString());
        } catch (ParseException e) { e.printStackTrace();
        } catch (NumberFormatException e) {
            JSONObject object = new JSONObject();
            object.put("error", "Incorrect body");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString()); }
        return null;
    }

    @GetMapping("mul")
    public ResponseEntity<String> multiply(@RequestParam(value = "a") int value1, @RequestParam(value = "b") int value2){
        System.out.println(value1 * value2);
        JSONObject object = new JSONObject();
        int result = value1 * value2;
        object.put("result", result);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

}
