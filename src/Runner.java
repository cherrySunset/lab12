import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        List<Result> results = new ArrayList<>();

        try {
            Object obj = parser.parse(new FileReader("src/results.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray resultsArray = (JSONArray) jsonObject.get("results");

            for (Object resultObj : resultsArray) {
                JSONObject resultJson = (JSONObject) resultObj;

                for (Object testObj : resultJson.values()) {
                    JSONArray testArray = (JSONArray) testObj;

                    for (Object entryObj : testArray) {
                        JSONObject entryJson = (JSONObject) entryObj;
                        String login = resultJson.keySet().iterator().next().toString();
                        String test = entryJson.keySet().iterator().next().toString();
                        String dateString = ((JSONObject) entryJson.get(test)).get("date").toString();
                        Date date = Date.valueOf(dateString);
                        double markDouble = (Double) ((JSONObject) entryJson.get(test)).get("mark");
                        int mark = (int) markDouble;
                        results.add(new Result(login, test, date, mark));
                    }
                }
            }

            for (Result result : results) {
                System.out.println(result.getLogin() + ";" +
                        result.getTest() + ";" +
                        result.getDate() + ";" +
                        result.getMark());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}