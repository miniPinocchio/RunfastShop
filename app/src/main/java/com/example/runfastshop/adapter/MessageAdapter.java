package com.example.runfastshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.DiscountActivity;
import com.example.runfastshop.bean.message.MessageInfo;

import java.util.List;

/**
 * Created by huiliu on 2017/9/5.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context mContext;
    private List<MessageInfo> mMessageInfos;

    public MessageAdapter(Context context, List<MessageInfo> messageInfos) {
        mContext = context;
        mMessageInfos = messageInfos;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_message_list, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DiscountActivity.class));
            }
        });
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        MessageInfo messageInfo = mMessageInfos.get(position);
        //TODO 消息图片在哪里获取
        //1系统消息2商家消息3骑手消息4用户消息5确认订单提示消息
        switch (messageInfo.getType()) {
            case 1:
                holder.iv_message_notice.setImageResource(R.drawable.icon_promotion);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                holder.iv_message_notice.setImageResource(R.drawable.icon_message_notice);
                break;
        }
        holder.tv_message_name.setText(messageInfo.getTitle());
        holder.tv_message_date.setText(messageInfo.getCreateTime());
        holder.tv_message_content.setText(messageInfo.getContent());
    }

    @Override
    public int getItemCount() {
        return mMessageInfos.size() <= 0 ? 0 : mMessageInfos.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_message_notice;
        TextView tv_message_name, tv_message_content, tv_message_date;

        public MessageViewHolder(View itemView) {
            super(itemView);
            iv_message_notice = (ImageView) itemView.findViewById(R.id.iv_message_notice);
            tv_message_name = (TextView) itemView.findViewById(R.id.tv_message_name);
            tv_message_content = (TextView) itemView.findViewById(R.id.tv_message_content);
            tv_message_date = (TextView) itemView.findViewById(R.id.tv_message_date);
        }
    }
}
