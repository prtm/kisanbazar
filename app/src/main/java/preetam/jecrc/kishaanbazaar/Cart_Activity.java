package preetam.jecrc.kishaanbazaar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by preetam on 3/24/16.
 */
public class Cart_Activity extends AppCompatActivity implements ImageSet {
    static boolean img_On;
    private RecyclerView rv;
    private LinearLayout lv;
    private View view;
    private ImageView emptycart;
    private TextView number;
    static int total = 0;
    private Button proceed_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayActionbar();
        intialize();
        if (img_On) {
            emptycart.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);

        } else {
            emptycart.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            number.setText("Total(" + NavigationDrawerActivity.cartsList.size() + "):");

        }
        final RecyclerViewAdapter_Carts adapter_carts = new RecyclerViewAdapter_Carts(Cart_Activity.this, NavigationDrawerActivity.cartsList);
        rv.setAdapter(adapter_carts);
        rv.setLayoutManager(new LinearLayoutManager(Cart_Activity.this));

        proceed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter_carts.send_to_me();
                AlertDialog.Builder ab = new AlertDialog.Builder(Cart_Activity.this);
                ab.setTitle("Product pay: Rs " + total);
                ab.setMessage("Delivery Charges depend upon Shipping Service");
                ab.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Cart_Activity.this, AddressClass.class);
                        startActivity(intent);
                    }
                });
                ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ab.show();
            }
        });


    }

    private void intialize() {
        rv = (RecyclerView) findViewById(R.id.rv_cart);
        emptycart = (ImageView) findViewById(R.id.emptycart);
        lv = (LinearLayout) findViewById(R.id.proceed);
        view = findViewById(R.id.extraview);
        number = (TextView) findViewById(R.id.proceed_number);
        proceed_button = (Button) findViewById(R.id.proceed_button);


    }

    private void displayActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void imagesettled() {
        emptycart.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        lv.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }

    @Override
    public void number_money() {

        number.setText("Total(" + NavigationDrawerActivity.cartsList.size() + "):");
    }
}

