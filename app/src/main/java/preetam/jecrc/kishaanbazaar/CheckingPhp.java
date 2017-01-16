package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckingPhp extends AppCompatActivity {
    private final String otp = "otp";
    private TextView textView;
    private String getOtp;
    private EditText editText;
    private String mob_number;
    private ProgressBar progressBar;
    private final String key_for_boolean = "vsvu6Saw5U4HafMtjKk7bK55p8f3hU6";
    private final String key_for_mobileN = "Y1zGPF3W9LMO9273PwUeNK1l85n0VcWz";
    private final String value_for_boolean = "def_yes";
    private SharedPreferences sharedpreferences;

    private SharedPreferences getSharedPreferences() {
        if (sharedpreferences == null) {
            sharedpreferences = getSharedPreferences("Myprefs", Context.MODE_PRIVATE);
        }
        return sharedpreferences;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkingphp);
        Bundle bundle = getIntent().getExtras();
        mob_number = bundle.getString("mob_number");
        sharedpreferences = getSharedPreferences();
        //sharedpreferences = getSharedPreferences("DefaultKB", Context.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.verifyProgress);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.response_msg);
        Button button = (Button) findViewById(R.id.verify);
        assert progressBar != null;
        progressBar.setProgress(20);
//        new java.util.Timer().schedule(
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        editText.setEnabled(true);
//                    }
//                },
//                10000
//        );
//        long start = System.currentTimeMillis();
//        long end = start + 10 * 1000; // 10 seconds * 1000 ms/sec
//        while (System.currentTimeMillis() < end) {
//            long tDelta = end - start;
//            double elapsedSeconds = tDelta / 1000.0;
//            progressBar.setProgress((int) (10 * elapsedSeconds));
//            textView.setVisibility(View.VISIBLE);
//            textView.setText(String.valueOf(elapsedSeconds));
//            b = true;
//        }
        textView.setVisibility(View.GONE);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() == 6) {
                    progressBar.setProgress(View.VISIBLE);
                    getOtp = editText.getText().toString();
                    textView.setVisibility(View.GONE);
                    verifyOtp("http://sggroupexport.com/androidRd/verify_otp.php");
                }
            }
        });
        progressBar.setProgress(100);

    }


    private void verifyOtp(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        L.tlog(response);

                        try {

                            JSONObject responseObj = new JSONObject(response);

                            // Parsing json object response
                            // response will be a json object
                            boolean error = responseObj.getBoolean("error");
                            L.tlog(String.valueOf(error));
                            //String message = responseObj.getString("message");

                            if (!error) {
                                L.tlog("no error");


                                byte[] data1 = value_for_boolean.getBytes("UTF-8");
                                byte[] data2 = mob_number.getBytes("UTF-8");
                                String txtB = Base64.encodeToString(data1, Base64.DEFAULT);
                                String txtN = Base64.encodeToString(data2, Base64.DEFAULT);

                                L.tlog("Working ");
                                L.tlog(txtB + "," + txtN);


                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(key_for_boolean, txtB);
                                editor.putString(key_for_mobileN, txtN);
                                editor.apply();
                                L.tlog("putting successfully");
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(CheckingPhp.this, NavigationDrawerActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                // parsing the user profile information
                                //JSONObject profileObj = responseObj.getJSONObject("profile");

                                //String mobile = profileObj.getString("mobile");

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        //if (true) {


                        //}
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(error.toString());


            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(otp, getOtp);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
