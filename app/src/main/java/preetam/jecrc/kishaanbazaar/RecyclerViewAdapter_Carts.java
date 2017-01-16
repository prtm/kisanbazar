package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RecyclerViewAdapter_Carts extends RecyclerView.Adapter<RecyclerViewAdapter_Carts.PrtmHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ImageSet is;
    private List<Integer> pric;
    private List<Information_Carts> list = Collections.emptyList();

    RecyclerViewAdapter_Carts(Context context, List<Information_Carts> l) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = l;
        pric = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            pric.add(0);
        }

        is = (ImageSet) context;
    }

    @Override
    public PrtmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrtmHolder(inflater.inflate(R.layout.cart_row, parent, false));
    }

    @Override
    public void onBindViewHolder(PrtmHolder holder, int position) {
        L.tlog("bindview " + position);
        holder.name.setText(list.get(position).name);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list.get(position).types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.edittocart.setText(String.valueOf(list.get(position).totalamount));
        holder.type.setAdapter(dataAdapter);
        holder.type.setSelection(list.get(position).price_loc);

        Picasso.with(context).load("http://sggroupexport.com/ImageD/" + list.get(position).name + ".png").into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    void send_to_me() {
        int total = 0;
        for (int i = 0; i < pric.size(); i++) {
            total += pric.get(i);
        }
        Cart_Activity.total = total;
    }

    class PrtmHolder extends RecyclerView.ViewHolder {
        private TextView name, price;
        private EditText edittocart;
        private Spinner type;
        private ImageView img;
        private Button button;
        private int spinner_pos = 0;

        PrtmHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.company_product_name);
            price = (TextView) itemView.findViewById(R.id.price);
            type = (Spinner) itemView.findViewById(R.id.types);
            img = (ImageView) itemView.findViewById(R.id.company_product_img);
            edittocart = (EditText) itemView.findViewById(R.id.edittocart);
            button = (Button) itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //((ViewGroup) v.getParent().getParent()).removeView((ViewGroup) v.getParent());
                    NavigationDrawerActivity.cartsList.remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                    if (NavigationDrawerActivity.cartsList.isEmpty()) {
                        Cart_Activity.img_On = true;
                        is.imagesettled();
                    } else {
                        is.number_money();
                    }

                }
            });


            edittocart.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().equals("")) {
                        NavigationDrawerActivity.cartsList.get(getLayoutPosition()).totalamount = Integer.parseInt(s.toString());
                        String st = list.get(getLayoutPosition()).price.get(spinner_pos);

                        int x = Integer.parseInt(s.toString()) * Integer.parseInt(st);
                        price.setText(String.format("Rs %d", x));
                        pric.set(getLayoutPosition(), x);

                    }
                }
            });
            edittocart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus && edittocart.getText().toString().equals("")) {
                        edittocart.setText("1");
                    }
                }
            });

            type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    NavigationDrawerActivity.cartsList.get(getLayoutPosition()).price_loc = position;
                    spinner_pos = position;
                    String s = list.get(getLayoutPosition()).price.get(position);
                    if (!edittocart.getText().toString().equals("")) {

                        int x = Integer.parseInt(s) * Integer.parseInt(edittocart.getText().toString());
                        price.setText(String.format("Rs %d", x));
                        pric.set(getLayoutPosition(), x);

                    } else {

                        price.setText(String.format("Rs %s", s));
                        pric.set(getLayoutPosition(), Integer.parseInt(s));

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    L.tlog("nothing");
                    String s = list.get(getLayoutPosition()).price.get(0);
                    if (!edittocart.getText().toString().equals("")) {
                        int x = Integer.parseInt(s) * Integer.parseInt(edittocart.getText().toString());
                        price.setText(String.format("Rs %d", x));
                        pric.set(getLayoutPosition(), x);

                    } else {

                        price.setText(String.format("Rs %s", s));
                        pric.set(getLayoutPosition(), Integer.parseInt(s));
                    }

                }
            });
        }
    }
}
