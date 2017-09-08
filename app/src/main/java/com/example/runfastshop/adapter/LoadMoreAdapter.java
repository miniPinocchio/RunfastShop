package com.example.runfastshop.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.fragment.EvaluateFragment;


/**
 * 加载更多的适配器
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/24]
 */
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mInflater;

    private RecyclerView.Adapter adapter;

    private int loadMoreState;//加载状态

    private LoadMoreApi mLoadMoreListener; //加载更多的监听器

    private static final int LOAD_IN_STATE = 1; //记载中

    private static final int LOAD_COMPLETED_STATE = 2; //加载完成

    private static final int LOAD_FAILED_STATE = 3; //加载失败

    private static final int LOADED_ALL_STATE = 4; //所有数据完成加载

    private LoadMoreViewHolder mLoadMoreViewHolder;



    /**
     * 加载更多的接口
     */
    public interface LoadMoreApi {
        void loadMore();
    }

    public LoadMoreAdapter(Context context, RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        this.loadMoreState = 2;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Integer.MAX_VALUE) {
            View itemView = mInflater.inflate(R.layout.item_load_more, parent, false);
            mLoadMoreViewHolder = new LoadMoreViewHolder(itemView);
            return mLoadMoreViewHolder;
        }
        if (viewType == Integer.MAX_VALUE - 1) {
            View itemView = mInflater.inflate(R.layout.layout_empty, parent, false);
            return new EmptyViewHolder(itemView);
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
            holder.itemView.setOnClickListener(new LoadMoreViewClick(viewHolder));
            viewHolder.ivLoading.setVisibility(loadMoreState == LOAD_IN_STATE ? View.VISIBLE : View.GONE);
            viewHolder.tvMessage.setText(getMessageByState());
            return;
        }
        if (holder instanceof EmptyViewHolder) {

            return;
        }
        adapter.onBindViewHolder(holder, position);
    }

    /**
     * 根据状态获取要显示的信息
     *
     * @return
     */
    private int getMessageByState() {

        switch (loadMoreState) {
            case LOAD_COMPLETED_STATE:
            case LOAD_IN_STATE:
                return R.string.please_waite_to_refresh;
            case LOAD_FAILED_STATE:
                return R.string.refresh_failed;
            case LOADED_ALL_STATE:
                return R.string.data_loaded;
        }

        return R.string.please_waite_to_refresh;

    }


    @Override
    public int getItemCount() {
        return adapter.getItemCount() == 0 ? 1 : adapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (adapter.getItemCount() == 0) {
            return Integer.MAX_VALUE - 1;
        }
        if (position == adapter.getItemCount()) {
            return Integer.MAX_VALUE;
        }
        return adapter.getItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (!(holder instanceof LoadMoreViewHolder)) {
            return;
        }
        LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
        Log.i("LoadMoreViewHolder", "onViewAttachedToWindow");
        if (viewHolder.loadingDrawable != null) {
            viewHolder.loadingDrawable.start();
        }
        if (loadMoreState != LOAD_COMPLETED_STATE) {
            return;
        }
        loadMoreState = LOAD_IN_STATE;
        viewHolder.ivLoading.setVisibility(View.VISIBLE);
        viewHolder.tvMessage.setText(R.string.please_waite_to_refresh);
        if (mLoadMoreListener != null) {
            mLoadMoreListener.loadMore();
        }
        super.onViewAttachedToWindow(holder);
    }

    /**
     * 刷新完成
     */
    public void loadCompleted() {
        loadMoreState = LOAD_COMPLETED_STATE;
        notifyDataSetChanged();
    }

    /***
     * 刷新失败
     */
    public void loadFailed() {
        loadMoreState = LOAD_FAILED_STATE;
        if (mLoadMoreViewHolder == null) {
            return;
        }
        mLoadMoreViewHolder.tvMessage.setText(R.string.refresh_failed);
        mLoadMoreViewHolder.ivLoading.setVisibility(View.GONE);
    }

    /**
     * 数据已全部加载
     */
    public void loadAllDataCompleted() {
        loadMoreState = LOADED_ALL_STATE;
        notifyDataSetChanged();
    }

    /**
     * 重置加载状态，主要在下拉刷新的时候使用
     */
    public void resetLoadState() {
        loadMoreState = LOAD_COMPLETED_STATE;
    }

    /**
     * 设置监听事件
     *
     * @param mLoadMoreListener
     */
    public void setLoadMoreListener(LoadMoreApi mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (!(holder instanceof LoadMoreViewHolder)) {
            return;
        }
        LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
        if (viewHolder.loadingDrawable != null) {
            viewHolder.loadingDrawable.stop();
        }
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * 加载更多的ViewHolder
     */
    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivLoading;

        public TextView tvMessage;

        public AnimationDrawable loadingDrawable;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            ivLoading = (ImageView) itemView.findViewById(R.id.iv_loading);
            loadingDrawable = (AnimationDrawable) ivLoading.getDrawable();
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }

    /***
     * 加载更多的点击事件
     */
    public class LoadMoreViewClick implements View.OnClickListener {

        private LoadMoreViewHolder loadMoreViewHolder;

        public LoadMoreViewClick(LoadMoreViewHolder loadMoreViewHolder) {
            this.loadMoreViewHolder = loadMoreViewHolder;
        }

        @Override
        public void onClick(View v) {
            if (loadMoreState != LOAD_FAILED_STATE) {
                return;
            }
            loadMoreState = LOAD_IN_STATE;
            loadMoreViewHolder.ivLoading.setVisibility(View.VISIBLE);
            loadMoreViewHolder.tvMessage.setText(R.string.please_waite_to_refresh);
            if (mLoadMoreListener != null) {
                mLoadMoreListener.loadMore();
            }
        }
    }

    /**
     * 空白界面的ViewHolder
     */
    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEmptyMessage;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            tvEmptyMessage = (TextView) itemView.findViewById(R.id.tv_empty_message);
        }
    }


}
