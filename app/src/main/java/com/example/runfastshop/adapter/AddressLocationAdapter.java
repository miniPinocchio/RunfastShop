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

public class AddressLocationAdapter extends RecyclerView.Adapter<AddressLocationAdapter.AddressManagerViewHolder>{

    private List<Address> addresses;

    private Context context;

    private View.OnClickListener listener;

    public AddressLocationAdapter(List<Address> addresses, Context context, View.OnClickListener listener) {
        this.addresses = addresses;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public AddressManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_name,parent,false);
        AddressManagerViewHolder viewHolder = new AddressManagerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressManagerViewHolder holder, int position) {
        Address address = addresses.get(position);
        if (address != null) {
            holder.title.setText(address.title);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(listener);
        }

    }

    @Override
    public int getItemCount() {
        return addresses == null?0:addresses.size();
    }

    public class AddressManagerViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public AddressManagerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_address_title);
        }
    }
}
