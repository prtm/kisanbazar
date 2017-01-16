package preetam.jecrc.kishaanbazaar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Company_Products extends AppCompatActivity {

    RecyclerView rv;
    RVAdapter_Company_Products rvap;
    String mSelectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_p);

        rv = (RecyclerView) findViewById(R.id.rv_nvg);

        Bundle bundle = getIntent().getExtras();
        mSelectName = bundle.getString("nvg_click");

        rv.setVisibility(View.VISIBLE);
        rvap = new RVAdapter_Company_Products(Company_Products.this, getData(mSelectName));
        rvap.setHasStableIds(true);
        rv.setAdapter(rvap);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(Company_Products.this, 4));
    }


    private List<Information_Products> getData(String mSelectName) {
        List<Information_Products> list = new ArrayList<>();
        List<Download_Information2> productsList = NavigationDrawerActivity.informations2;
        Information_Products l;
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).company_name.equals(mSelectName)) {
                l = new Information_Products();
                l.name = productsList.get(i).products;
                /////////////////////
                l.price = Arrays.asList(productsList.get(i).price.split(","));
                ////////////////////////
                l.types = Arrays.asList(productsList.get(i).types.split(","));

                /////////////////////
                list.add(l);
            }
        }


        return list;
    }
}
