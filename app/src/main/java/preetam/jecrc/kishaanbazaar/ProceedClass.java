package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProceedClass extends AppCompatActivity implements View.OnClickListener {
    private OrderId_Base orderId_base;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_class);
        progressBar = (ProgressBar) findViewById(R.id.proceedProgress);
        orderId_base = new OrderId_Base(ProceedClass.this);
        TextView total_pay = (TextView) findViewById(R.id.total_pay);

        assert total_pay != null;
        total_pay.setText(String.format("%s%d", getString(R.string.pay_rs), Cart_Activity.total));

        Button paytmCall = (Button) findViewById(R.id.paytmCall);
        Button cashOnCall = (Button) findViewById(R.id.cashOnCall);
        assert paytmCall != null;
        paytmCall.setOnClickListener(this);
        assert cashOnCall != null;
        cashOnCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.paytmCall == v.getId()) {
            Toast.makeText(getApplicationContext(), "Soon Available", Toast.LENGTH_SHORT).show();
        } else if (R.id.cashOnCall == v.getId()) {
            progressBar.setVisibility(View.VISIBLE);
            requestVolley("http://sggroupexport.com/androidRd/sg454687017.php");

        }
    }

    private void requestVolley(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        L.tlog(response);

                        DataBasehelper dataBasehelper = new DataBasehelper(ProceedClass.this);
                        dataBasehelper.open();
                        String ne = dataBasehelper.getName();
                        String mn = dataBasehelper.getmobileN();
                        dataBasehelper.close();
                        orderId_base.open();
                        orderId_base.createEntry(ne, response, mn);
                        orderId_base.close();
                        final Intent intent = new Intent(ProceedClass.this, NavigationDrawerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProceedClass.this);
                        builder.setTitle("Order Placed Successfully");
                        builder.setMessage(response);
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                NavigationDrawerActivity.cartsList.clear();
                                startActivity(intent);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.setCancelable(false);
                        dialog.show();


                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                L.tlog(error.toString());
                L.tlong(getApplicationContext(), error.toString());

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedpreferences = getSharedPreferences("Myprefs", Context.MODE_PRIVATE);
                String s = sharedpreferences.getString("Y1zGPF3W9LMO9273PwUeNK1l85n0VcWz", "error");
                byte[] decoded = Base64.decode(s, Base64.DEFAULT);
                String decodefinal = new String(decoded);
                DataBasehelper dataBasehelper = new DataBasehelper(ProceedClass.this);
                dataBasehelper.open();
                params.put("Address", dataBasehelper.getAll() + "V.N. :" + decodefinal);
                dataBasehelper.close();
                String orderPatchup = "";
                List<Information_Carts> ls = NavigationDrawerActivity.cartsList;

                for (int i = 0; i < ls.size(); i++) {
                    orderPatchup += "Product: " + ls.get(i).name + " -- " + ls.get(i)
                            .types.get(ls.get(i).price_loc) + ", Quantitiy: " + ls.get(i).totalamount + "\n";
                }
                orderPatchup += "Total:" + Cart_Activity.total;
                params.put("OrderPro", orderPatchup);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


}
