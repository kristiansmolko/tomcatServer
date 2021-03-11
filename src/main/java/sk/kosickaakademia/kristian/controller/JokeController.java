package sk.kosickaakademia.kristian.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sk.kosickaakademia.kristian.database.Database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class JokeController {
    Database dat = new Database();
    String joke1 = "Why did the chicken commit suicide? To get to the other side";
    String joke2 = "A dyslexic man walks into a bra";
    String joke3 = "Two fish in a tank. One says: \"How do you drive this thing?\"";
    public final String getJokes = "SELECT * FROM jokeList";
    public final String addJoke = "INSERT INTO jokeList(joke) VALUES(?)";


    public List<String> getListOfJokes(){
        List<String> list = new ArrayList<>();
        try (Connection connection = dat.getConnection()){
            if (connection != null){
                PreparedStatement ps = connection.prepareStatement(getJokes);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    list.add(rs.getString("joke"));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @GetMapping("/jokes")
    public ResponseEntity<String> getJokes(){
        List<String> list = getListOfJokes();
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for (String s : list){
            array.add(s);
        }
        object.put("count", array.size());
        object.put("jokes", array);
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

    @GetMapping("/joke/{id}")
    public ResponseEntity<String> getJokeById(@PathVariable Integer id){
        List<String> list = getListOfJokes();
        if (id < 0 || id > list.size()){
            JSONObject error = new JSONObject();
            error.put("error", "Wrong index");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(error.toJSONString());
        }
        JSONObject object = new JSONObject();
        for (int i = 0; i < list.size(); i++){
            if (i == id-1)
                object.put(i, list.get(i));
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }

    @GetMapping("joke")
    public ResponseEntity<String> getRandomJoke(){
        List<String> list = getListOfJokes();
        Random rnd = new Random();
        int random = rnd.nextInt(list.size())+1;
        System.out.println(random);
        return getJokeById(random);
    }

    @GetMapping("add")
    public ResponseEntity<String> addJoke(@RequestBody String input){
        if (input.equals("") || input == null){
            JSONObject response = new JSONObject();
            response.put("error", "Wrong input");
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(response.toJSONString());
        }
        JSONObject object = null;
        JSONObject response = new JSONObject();
        try (Connection connection = dat.getConnection()){
            if (connection != null) {
                object = (JSONObject) new JSONParser().parse(input);
                String joke = String.valueOf(object.get("joke"));
                PreparedStatement ps = connection.prepareStatement(addJoke);
                ps.setString(1, joke);
                if (ps.executeUpdate() == 1)
                    response.put("info", "Added joke");
                else
                    response.put("error", "Error adding joke");
            }
        } catch (Exception e) { e.printStackTrace(); }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(response.toJSONString());



    }
}
