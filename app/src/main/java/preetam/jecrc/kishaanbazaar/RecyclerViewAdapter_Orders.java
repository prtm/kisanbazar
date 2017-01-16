package preetam.jecrc.kishaanbazaar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

class RecyclerViewAdapter_Orders extends RecyclerView.Adapter<RecyclerViewAdapter_Orders.PrtmHolder> {

    private LayoutInflater inflater;
    private List<Information_my_order> list = Collections.emptyList();

    RecyclerViewAdapter_Orders(Context context, List<Information_my_order> l) {
        inflater = LayoutInflater.from(context);
        this.list = l;

    }

    @Override
    public PrtmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PrtmHolder(inflater.inflate(R.layout.order_custom_rwo, parent, false));
    }

    @Override
    public void onBindViewHolder(PrtmHolder holder, int position) {
        holder.order_name.setText(String.format("Name: %s", list.get(position).name));
        holder.order_id.setText(String.format("OrderId :%s", list.get(position).order_id));
        holder.order_number.setText(String.format("Number :%s", list.get(position).number));

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    class PrtmHolder extends RecyclerView.ViewHolder {
        TextView order_name, order_id, order_number;

        PrtmHolder(View itemView) {
            super(itemView);
            order_name = (TextView) itemView.findViewById(R.id.order_name);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_number = (TextView) itemView.findViewById(R.id.order_number);

        }
    }
}
