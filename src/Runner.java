import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<Result> results;

        try (InputStream is = new FileInputStream("src/results.json")) {
            results = new ArrayList<>();
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(is));
            JSONArray arrJson = (JSONArray) jsonObject.get("results");
            Iterator<JSONObject> it = arrJson.iterator();

            while (it.hasNext()) {
                JSONObject json = it.next();
                String login = (String) json.keySet().iterator().next();
                JSONArray testArray = (JSONArray) json.get(login);
                for (Object testObject : testArray) {
                    JSONObject testJson = (JSONObject) testObject;
                    String test = (String) testJson.keySet().iterator().next();
                    JSONObject resultJson = (JSONObject) testJson.get(test);
                    String dateString = (String) resultJson.get("date");
                    java.sql.Date date = java.sql.Date.valueOf(dateString);
                    double mark = (double) resultJson.get("mark");
                    results.add(new Result(login, test, date, mark));
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println(results);
    }
}