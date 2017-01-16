package preetam.jecrc.kishaanbazaar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<String> parseFeed(String content) {

        try {
            JSONArray jarr = new JSONArray(content);
            ArrayList<String> listf = new ArrayList<>();

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject obj = jarr.getJSONObject(i);
                listf.add(obj.getString("Company_Name"));

            }

            return listf;
        } catch (JSONException e) {
            return null;
        }
    }
}
