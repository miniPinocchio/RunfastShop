package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.CashBankInfo;
import com.example.runfastshop.bean.spend.AccountRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class CashUserNameAdapter extends RecyclerView.Adapter<CashUserNameAdapter.CashUserNameViewHolder> {

    private List<CashBankInfo> mScoreList;

    private Context context;

    private View.OnClickListener mListener;

    public CashUserNameAdapter(List<CashBankInfo> mScoreList, Context context, View.OnClickListener listener) {
        this.mScoreList = mScoreList;
        this.context = context;
        mListener = listener;
    }

    @Override
    public CashUserNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank_info, parent, false);
        CashUserNameViewHolder holder = new CashUserNameViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CashUserNameViewHolder holder, int position) {
        CashBankInfo bankInfo = mScoreList.get(position);
        if (bankInfo != null) {
            holder.tv_bank_name.setText(bankInfo.getBanktype());
            holder.tv_user_name.setText(bankInfo.getName());
            String account = bankInfo.getAccount();
            if (account.length() > 4){
                account = account.substring(account.length()-4);
            }
            holder.tv_bank_code.setText("**** **** **** "+account);
            holder.tv_bank_time.setText("添加日期 "+bankInfo.getCreateTime());

            holder.iv_delete_bank.setTag(position);
            holder.iv_delete_bank.setOnClickListener(mListener);

        }
    }

    @Override
    public int getItemCount() {
        return mScoreList == null ? 0 : mScoreList.size();
    }

    public class CashUserNameViewHolder extends RecyclerView.ViewHolder {

        TextView tv_bank_name,tv_user_name,tv_bank_code,tv_bank_time;
        ImageView iv_delete_bank;

        public CashUserNameViewHolder(View itemView) {
            super(itemView);
            tv_bank_name = (TextView) itemView.findViewById(R.id.tv_bank_name);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_bank_code = (TextView) itemView.findViewById(R.id.tv_bank_code);
            tv_bank_time = (TextView) itemView.findViewById(R.id.tv_bank_time);
            iv_delete_bank = (ImageView) itemView.findViewById(R.id.iv_delete_bank);
        }
    }
}
