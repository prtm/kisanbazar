package preetam.jecrc.kishaanbazaar;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class DownloadService extends IntentService {
    public static String url = "http://sggroupexport.com/sg146543575.php";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("DownloadService", "Service Started!");
        try {

            requestVolley(url);

        } catch (Exception e) {
            L.tlong(getApplicationContext(), e.toString());
        }

        Log.d("DownloadService", "Service Stopping!");
        this.stopSelf();
    }

    private void requestVolley(String url) {
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                L.tlog(error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
