package preetam.jecrc.kishaanbazaar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

class RVAdapter_Company_Products extends RecyclerView.Adapter<RVAdapter_Company_Products.PrtmHolder> {

    private LayoutInflater inflater;
    Context context;
    private int xpos;
    private List<Information_Products> list;

    RVAdapter_Company_Products(Context context, List<Information_Products> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public PrtmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrtmHolder(inflater.inflate(R.layout.custom_company_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final PrtmHolder holder, final int position) {
        holder.name.setText(list.get(position).name);
        Picasso.with(context).load("http://sggroupexport.com/ImageD/" + replaceString(list.get(position).name) + ".png").resize(150, 100).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPhoto(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {

        if (list == null) {
            return 0;
        }
        return list.size();
    }

    private void dialogPhoto(final int pos) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // ...Irrelevant code for customizing the buttons and title
        final View dialogView = inflater.inflate(R.layout.zoom_imageview, null);
        dialogBuilder.setView(dialogView);
        ImageView mImageView = (ImageView) dialogView.findViewById(R.id.company_product_img);
        TextView comp_name = (TextView) dialogView.findViewById(R.id.company_product_name);
        final TextView price = (TextView) dialogView.findViewById(R.id.price);
        final Spinner types = (Spinner) dialogView.findViewById(R.id.types);
        final Button addtocart = (Button) dialogView.findViewById(R.id.add_tocart);
        Button cancel = (Button) dialogView.findViewById(R.id.to_cancel);
        comp_name.setText(list.get(pos).name);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list.get(pos).types);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(dataAdapter);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);

        alertDialog.getWindow().setLayout(800, 800);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xpos = position;
                price.setText(String.format("Rs %s", list.get(pos).price.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                xpos = 0;
                types.setSelection(0);
                price.setText(String.format("Rs %s", list.get(pos).price.get(0)));
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NavigationDrawerActivity.cartsList != null) {
                    if (NavigationDrawerActivity.cartsList.size() < 15) {
                        if (addtocart.getText().toString().equalsIgnoreCase("Add to Cart")) {
                            addtocart.setText(R.string.gotocart);
                            Information_Carts l = new Information_Carts();
                            l.name = list.get(pos).name;
                            l.price = list.get(pos).price;
                            l.types = list.get(pos).types;
                            l.price_loc = getvalue();
                            l.totalamount = 1;
                            NavigationDrawerActivity.cartsList.add(l);
                        } else {
                            try {
                                Cart_Activity.img_On = NavigationDrawerActivity.cartsList == null || NavigationDrawerActivity.cartsList.isEmpty();

                            } catch (NullPointerException ignored) {

                            }

                            // add elements to al, including duplicates

                            Intent intent = new Intent(context, Cart_Activity.class);
                            context.startActivity(intent);
                            try {
                                Activity activity = (Activity) context;
                                activity.finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Cart is Full", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //Picasso.with(context).load("http://sggroupexport.com/ImageD/" + list.get(position).name + \".png").into(mImageView);
        Picasso.with(context).load("http://sggroupexport.com/ImageD/" + replaceString(list.get(pos).name) + ".png").into(mImageView);

        final ImageView finalMImageView = mImageView;
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomPhoto(finalMImageView);
            }
        });

        alertDialog.show();
    }

    private int getvalue() {
        return xpos;
    }

    private void zoomPhoto(ImageView mImageView) {

        // Set the Drawable displayed


        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        new PhotoViewAttacher(mImageView);
    }

    class PrtmHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;

        PrtmHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.company_product_name);
            img = (ImageView) itemView.findViewById(R.id.company_product_img);


        }
    }

    private String replaceString(String str) {
        String s = str.trim();
        return s.replaceAll(" ", "%20");
    }
}
