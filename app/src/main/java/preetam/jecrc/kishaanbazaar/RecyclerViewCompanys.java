package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by preetam on 3/26/16.
 */
class RecyclerViewCompanys extends RecyclerView.Adapter<RecyclerViewCompanys.Myholder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> ls;
    private RecyclerViewClickListener recyclerViewClickListener;

    RecyclerViewCompanys(Context context, ArrayList<String> ls) {
        this.ls = ls;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        recyclerViewClickListener = (RecyclerViewClickListener) context;
        return new Myholder(inflater.inflate(R.layout.cardview, parent, false));

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.tv.setText(ls.get(position));


        Picasso.with(context).load("http://sggroupexport.com/ImageD/" + replaceString(ls.get(position)) + ".png").resize(150, 100).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return ls == null ? 0 : ls.size();
    }


    class Myholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv;

        Myholder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.company_photo);
            tv = (TextView) itemView.findViewById(R.id.company_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickListener.recyclerViewListClicked(v, tv.getText().toString());

                }
            });
        }
    }

    private String replaceString(String str) {
        String s = str.trim();
        return s.replaceAll(" ", "%20");
    }
}
