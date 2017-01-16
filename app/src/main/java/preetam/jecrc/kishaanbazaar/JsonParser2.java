package preetam.jecrc.kishaanbazaar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser2 {

    public static List<Download_Information2> parseFeed(String content) {

        try {
            JSONArray jarr = new JSONArray(content);
            List<Download_Information2> listf = new ArrayList<>();

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject obj = jarr.getJSONObject(i);
                Download_Information2 downloadInformation2 = new Download_Information2();
                downloadInformation2.company_name = obj.getString("Company_Name");
                downloadInformation2.price = obj.getString("Price");
                downloadInformation2.types = obj.getString("Types");
                downloadInformation2.products = obj.getString("Product");


                listf.add(downloadInformation2);

            }

            return listf;
        } catch (JSONException e) {
            return null;
        }
    }
}
