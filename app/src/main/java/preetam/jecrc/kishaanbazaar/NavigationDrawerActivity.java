package preetam.jecrc.kishaanbazaar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by preetam on 3/20/16.
 */
public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NetworkErrorInterface, RecyclerViewClickListener {

    static List<Download_Information2> informations2;
    static List<Information_Carts> cartsList = new ArrayList<>();
    private static int mNotifCount = 0;
    private final String first_time = "firsttime";
    private boolean doubleBackToExitPressedOnce = false;
    private ArrayList<String> informations;
    private NavigationView mDrawer;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle actionbardt;
    private CustomDialogX cdx;
    private boolean b = true;
    private Product_Dialog cdx2;
    private String company_name;
    private boolean b1 = true, b2 = true;

    private void nvg_menu(ArrayList<String> d) {
        Menu m = mDrawer.getMenu();
        try {
            if (d != null) {
                for (int i = 0; i < d.size(); i++) {
                    m.add(d.get(i));

                }
            }
            MenuItem mi = m.getItem(m.size() - 1);
            mi.setTitle(mi.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNotifCount(cartsList == null ? 0 : cartsList.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_nvgdrawer);
        Toolbar mActionbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ImageView programtitle = (ImageView) findViewById(R.id.program_title);
        setSupportActionBar(mActionbar);

        displayActionbar();
        actionbardt = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.addDrawerListener(actionbardt);


        mDrawer.setNavigationItemSelectedListener(this);

        mDrawer.setItemIconTintList(null);
        informations = new ArrayList<>();
        informations2 = new ArrayList<>();
        /*
        programtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SubMenu topChannelMenu = m.addSubMenu("Top Channels");
        topChannelMenu.add("Foo");
        topChannelMenu.add("Bar");
        topChannelMenu.add("Baz");


         */

        if (!diduserSeenDrawer()) {
            showDrawer();
            markDrawerSeen();
        }
        cdx = new CustomDialogX(NavigationDrawerActivity.this, R.style.DialogTheme);
        cdx.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        cdx.show();
        requestVolley("http://sggroupexport.com/androidRd/sg146543575.php");


    }

    private void displayActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        }
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.edit().putBoolean(first_time, true).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.global, menu);

        /*
        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.layout_textoverimage);
        notifCount = (TextView) MenuItemCompat.getActionView(item);
         */

        RelativeLayout count = (RelativeLayout) menu.findItem(R.id.cart).getActionView();
        TextView notifCount = (TextView) count.findViewById(R.id.actionbar_notifcation_textview);
        if (mNotifCount > 0) {
            notifCount.setVisibility(View.VISIBLE);
        } else {
            notifCount.setVisibility(View.GONE);
        }
        final ImageView img = (ImageView) count.findViewById(R.id.cart_img);
        notifCount.setText(String.valueOf(mNotifCount));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setNotifCount(++mNotifCount);
                try {
                    Cart_Activity.img_On = NavigationDrawerActivity.cartsList == null || NavigationDrawerActivity.cartsList.isEmpty();

                } catch (NullPointerException ignored) {

                }

                Intent i = new Intent(NavigationDrawerActivity.this, Cart_Activity.class);
                startActivity(i);

            }
        });


        return true;

    }


    private void setNotifCount(int count) {
        mNotifCount = count;
        invalidateOptionsMenu();
    }

    private boolean diduserSeenDrawer() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPref.getBoolean(first_time, false);
    }

    private void showDrawer() {
        mDrawerlayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        mDrawerlayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionbardt.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(NavigationDrawerActivity.this, SellerDetails.class));
        }
        if (item.getItemId() == R.id.action_my_orders) {
            startActivity(new Intent(NavigationDrawerActivity.this, My_Orders.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionbardt.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionbardt.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        item.setChecked(true);
        //String mSelectName = item.getTitle().toString();
        mDrawerlayout.closeDrawer(GravityCompat.START);
        if (b) {
            cdx2 = new Product_Dialog(NavigationDrawerActivity.this, R.style.DialogTheme);
            cdx2.show();
            requestVolley2("http://sggroupexport.com/androidRd/sg645063343.php");
        } else {
            Intent i = new Intent(NavigationDrawerActivity.this, Company_Products.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            if (informations != null) {
                bundle.putString("nvg_click", item + "");
                i.putExtras(bundle);
                startActivity(i);
            }


        }

        return false;
    }

    private void fragm(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ll, fragment, "HELLO");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        if (!mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        return cm.getActiveNetworkInfo() != null;
//    }


    private void requestVolley(String url) {
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        informations = JsonParser.parseFeed(response);
                        nvg_menu(informations);

                        cdx.dismiss();
                        Fragment m = new Main_Selling();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("list", informations);
                        m.setArguments(bundle);
                        fragm(m);
                        b1 = true;

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                L.tlog(error.toString());

                cdx.dismiss();
                NetworkError n = new NetworkError();
                Bundle b = new Bundle();
                b.putString("url", "url1");
                n.setArguments(b);
                fragm(n);
                b1 = true;

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void networkRetry() {

        if (b1) {
            b1 = false;
            requestVolley("http://sggroupexport.com/androidRd/sg146543575.php");
        }
    }

    @Override
    public void networkRetry2() {
        if (b2) {
            b2 = false;
            requestVolley2("http://sggroupexport.com/androidRd/sg645063343.php");
        }
    }

    public void seller(View v) {

    }

    public void dev(View v) {

    }

    private void requestVolley2(String url) {

        final StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        NavigationDrawerActivity.informations2 = JsonParser2.parseFeed(response);
                        b = false;
                        Intent i = new Intent(NavigationDrawerActivity.this, Company_Products.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putString("nvg_click", company_name);
                        i.putExtras(bundle);
                        cdx2.dismiss();
                        startActivity(i);
                        b2 = true;

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                L.tlog(error.toString());
                NetworkError n = new NetworkError();
                Bundle b = new Bundle();
                b.putString("url", "url2");
                n.setArguments(b);
                cdx2.dismiss();
                fragm(n);
                b2 = true;

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    @Override
    public void recyclerViewListClicked(View v, String company_name) {
        this.company_name = company_name;
        if (b) {
            cdx2 = new Product_Dialog(NavigationDrawerActivity.this, R.style.DialogTheme);
            cdx2.show();
            requestVolley2("http://sggroupexport.com/androidRd/sg645063343.php");
        } else {
            Intent i = new Intent(NavigationDrawerActivity.this, Company_Products.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("nvg_click", company_name);
            i.putExtras(bundle);
            startActivity(i);
        }
    }


}