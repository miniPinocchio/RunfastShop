package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.Address;

import java.util.List;

/**
 *
 * Created by 天上白玉京 on 2017/7/28.
 */

public class AddressManagerAdapter extends RecyclerView.Adapter<AddressManagerAdapter.AddressManagerViewHolder>{

    private List<Address> addresses;

    private Context context;

    private View.OnClickListener listener;

    public AddressManagerAdapter(List<Address> addresses, Context context, View.OnClickListener listener) {
        this.addresses = addresses;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AddressManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_info,parent,false);
        AddressManagerViewHolder viewHolder = new AddressManagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressManagerViewHolder holder, int position) {
        Address address = addresses.get(position);
        if (address != null) {
            if (address.status == 1){
                holder.title.setTextColor(context.getResources().getColor(R.color.color_orange_select));
                holder.title.setText("[当前位置]"+address.title);
            }else {
                holder.title.setText(address.title);
                holder.title.setTextColor(context.getResources().getColor(R.color.color_address_black));
            }
            holder.name.setText(address.address);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(listener);
        }

    }

    @Override
    public int getItemCount() {
        return addresses == null?0:addresses.size();
    }

    public class AddressManagerViewHolder extends RecyclerView.ViewHolder{

        public TextView title,name;

        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_address_title);
            name = (TextView) itemView.findViewById(R.id.tv_address_name);
        }
    }
}
