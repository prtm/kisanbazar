package preetam.jecrc.kishaanbazaar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class My_Orders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__orders);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_orderid);
        RecyclerViewAdapter_Orders adapter = new RecyclerViewAdapter_Orders(this, getData());
        assert recyclerView != null;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private List<Information_my_order> getData() {
        OrderId_Base orderId_base = new OrderId_Base(this);
        List<Information_my_order> list;

        orderId_base.open();
        list = orderId_base.getAll();

        orderId_base.close();

        return list;
    }
}
