package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.score.MyScore;

import java.util.List;

/**
 * 积分全部适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class ScoreRecordAdapter extends RecyclerView.Adapter<ScoreRecordAdapter.ScoreRecordViewHolder> {

    private List<MyScore> mScoreList;

    private Context context;

    private View.OnClickListener mListener;

    public ScoreRecordAdapter(List<MyScore> mScoreList, Context context, View.OnClickListener listener) {
        this.mScoreList = mScoreList;
        this.context = context;
        mListener = listener;
    }

    @Override
    public ScoreRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_detail, parent, false);
        ScoreRecordViewHolder holder = new ScoreRecordViewHolder(view);
        view.setOnClickListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScoreRecordViewHolder holder, int position) {
        MyScore myScore = mScoreList.get(position);
        //积分类型1注册积分、2消费积分(下单消费)、3推荐积分、4分享积分、
        // 5收藏积分、6评论积分、7广告积分、8购物消费
        switch (myScore.getType()){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:

                break;
       }
        holder.mTvMakeMoneyNum.setTextColor(context.getResources().getColor(R.color.color_money_green));
        holder.mTvMakeMoneyNum.setText(String.valueOf(myScore.getScore()));
        holder.mTvMakeMoneyTime.setText(myScore.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return mScoreList == null ? 0 : mScoreList.size();
    }

    public class ScoreRecordViewHolder extends RecyclerView.ViewHolder {

        TextView mTvMakeMoneyTitle;
        TextView mTvMakeMoneyTime;
        TextView mTvMakeMoneyNum;

        public ScoreRecordViewHolder(View itemView) {
            super(itemView);
            mTvMakeMoneyNum = (TextView) itemView.findViewById(R.id.tv_make_money_num);
            mTvMakeMoneyTitle = (TextView) itemView.findViewById(R.id.tv_make_money_title);
            mTvMakeMoneyTime = (TextView) itemView.findViewById(R.id.tv_make_money_time);
        }
    }
}
