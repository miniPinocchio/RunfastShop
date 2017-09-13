package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.spend.AccountRecord;

import java.util.List;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class CashRecordAdapter extends RecyclerView.Adapter<CashRecordAdapter.CashRecordViewHolder> {

    private List<AccountRecord> mScoreList;

    private Context context;

    private View.OnClickListener mListener;

    public CashRecordAdapter(List<AccountRecord> mScoreList, Context context, View.OnClickListener listener) {
        this.mScoreList = mScoreList;
        this.context = context;
        mListener = listener;
    }

    @Override
    public CashRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_detail, parent, false);
        CashRecordViewHolder holder = new CashRecordViewHolder(view);
        view.setOnClickListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CashRecordViewHolder holder, int position) {
        AccountRecord accountRecord = mScoreList.get(position);
        holder.mTvMakeMoneyTitle.setText(accountRecord.getName());
        holder.mTvMakeMoneyNum.setTextColor(context.getResources().getColor(R.color.color_money_green));
        holder.mTvMakeMoneyNum.setText(String.valueOf(accountRecord.getMonetary()));
        holder.mTvMakeMoneyTime.setText(accountRecord.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return mScoreList == null ? 0 : mScoreList.size();
    }

    public class CashRecordViewHolder extends RecyclerView.ViewHolder {

        TextView mTvMakeMoneyTitle;
        TextView mTvMakeMoneyTime;
        TextView mTvMakeMoneyNum;

        public CashRecordViewHolder(View itemView) {
            super(itemView);
            mTvMakeMoneyNum = (TextView) itemView.findViewById(R.id.tv_make_money_num);
            mTvMakeMoneyTitle = (TextView) itemView.findViewById(R.id.tv_make_money_title);
            mTvMakeMoneyTime = (TextView) itemView.findViewById(R.id.tv_make_money_time);
        }
    }
}
