package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.BankSelectInfo;
import com.example.runfastshop.bean.CashBankInfo;

import java.util.List;

/**
 * 银行卡列表选择适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class SelectBankAdapter extends RecyclerView.Adapter<SelectBankAdapter.SelectBankViewHolder>{

    private List<CashBankInfo> data;

    private Context context;

    private View.OnClickListener listener;

    public SelectBankAdapter(List<CashBankInfo> data, Context context,View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public SelectBankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank_select,parent,false);
        SelectBankViewHolder holder = new SelectBankViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SelectBankViewHolder holder, int position) {
        CashBankInfo bankSelectInfo = data.get(position);
        if (bankSelectInfo != null) {
            if (TextUtils.isEmpty(bankSelectInfo.getAccount())){
                holder.tvBankName.setText(bankSelectInfo.getBanktype());
            }else {
                String account = bankSelectInfo.getAccount();
                if (account.length() > 4){
                    account = account.substring(account.length()-4);
                }
                holder.tvBankName.setText(bankSelectInfo.getBanktype()+"("+account+")");
            }
            if (position != (data.size() -1)){
                if (bankSelectInfo.isSelect()){
                    holder.ivBankSelect.setVisibility(View.VISIBLE);
                }else {
                    holder.ivBankSelect.setVisibility(View.GONE);
                }
            }
            holder.layoutBankSelect.setTag(position);
            holder.layoutBankSelect.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class SelectBankViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout layoutBankSelect;
        public TextView tvBankName;
        public ImageView ivBankSelect;

        public SelectBankViewHolder(View itemView) {
            super(itemView);
            layoutBankSelect = (LinearLayout) itemView.findViewById(R.id.layout_bank_select);
            tvBankName = (TextView) itemView.findViewById(R.id.tv_bank_name);
            ivBankSelect = (ImageView) itemView.findViewById(R.id.iv_bank_select);
        }
    }
}
