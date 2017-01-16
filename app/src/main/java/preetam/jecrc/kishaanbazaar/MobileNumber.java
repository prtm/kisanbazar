package preetam.jecrc.kishaanbazaar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MobileNumber extends Activity {
    private final String myName = "mobile";
    private EditText edittext;
    private boolean b = true;
    private String number;
    private ProgressBar progressBar;
    //private final String key_for_mobileN = "Y1zGPF3W9LMO9273PwUeNK1l85n0VcWz";
    private SharedPreferences mSecurePrefs;


    private SharedPreferences getSharedPreferences() {
        if (mSecurePrefs == null) {
            mSecurePrefs = getSharedPreferences("Myprefs", Context.MODE_PRIVATE);
        }
        return mSecurePrefs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSecurePrefs = getSharedPreferences();

        //SharedPreferences sharedpreferences = getSharedPreferences("DefaultKB", Context.MODE_PRIVATE);

        //sharedpreferences.getBoolean("key", false)

        String key_for_boolean = "vsvu6Saw5U4HafMtjKk7bK55p8f3hU6";
        String valueB = mSecurePrefs.getString(key_for_boolean, "eA==");
        //String valueN=mSecurePrefs.getString(key_for_mobileN,"eQ==");



        byte[] data1 = Base64.decode(valueB, Base64.DEFAULT);
        //byte[] data2 = Base64.decode(valueN, Base64.DEFAULT);
        //txtN = new String(data2,"UTF-8");
        //number=Integer.parseInt(txtN)/3;
        String txtB = null;
        try {
            txtB = new String(data1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        L.tlog(txtB);

        if (txtB != null && txtB.equals("def_yes")) {
            Intent intent = new Intent(MobileNumber.this, NavigationDrawerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        setContentView(R.layout.mobile_number_v);
        progressBar = (ProgressBar) findViewById(R.id.mobileProgress);
        Button button = (Button) findViewById(R.id.mobileN);
        edittext = (EditText) findViewById(R.id.editN);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                edittext.setEnabled(false);
                if (b) {

                    requestVolley("http://sggroupexport.com/androidRd/request_sms.php");
                }
                if (edittext.getText().toString().length() != 10) {
                    Toast.makeText(MobileNumber.this, "Enter 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    number = edittext.getText().toString();
                }
            }
        });
    }

    private void requestVolley(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        L.tlog(response);
                        progressBar.setVisibility(View.GONE);
                        edittext.setEnabled(true);
                        Intent intent = new Intent(MobileNumber.this, CheckingPhp.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mob_number", number);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                edittext.setEnabled(true);
                b = true;
                L.tlog(error.toString());
                L.tshort(getApplicationContext(), "Network error");

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(myName, number);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}