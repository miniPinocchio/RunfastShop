package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.address.AddressInfo;

import java.util.List;

/**
 * Created by 天上白玉京 on 2017/7/28.
 */

public class AddressSelectAdapter extends RecyclerView.Adapter<AddressSelectAdapter.AddressSelectViewHolder> implements View.OnClickListener {

    private List<AddressInfo> addresses;

    private Context context;

    private OnItemClickListener mOnItemClickListener = null;

    public AddressSelectAdapter(List<AddressInfo> addresses, Context context) {
        this.addresses = addresses;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public AddressSelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_user_info, parent, false);
        AddressSelectViewHolder viewHolder = new AddressSelectViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressSelectViewHolder holder, int position) {
        AddressInfo addressInfo = addresses.get(position);
//        if (address != null) {
//            if (address.status == 1){
//                holder.title.setTextColor(context.getResources().getColor(R.color.color_orange_select));
//                holder.title.setText("[当前位置]"+address.title);
//            }else {
//                holder.title.setText(address.title);
//                holder.title.setTextColor(context.getResources().getColor(R.color.color_address_black));
//            }
//            holder.name.setText(address.address);
        holder.title.setText(addressInfo.getUserAddress());
        holder.phone.setText(addressInfo.getPhone());
        holder.name.setText(addressInfo.getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return addresses == null ? 0 : addresses.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }



    public class AddressSelectViewHolder extends RecyclerView.ViewHolder {

        public TextView title, name, phone;

        public AddressSelectViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_user_title);
            name = (TextView) itemView.findViewById(R.id.tv_user_name);
            phone = (TextView) itemView.findViewById(R.id.tv_user_phone);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
