package com.giousa.imchart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.giousa.imchart.model.AddFriendItem;
import com.giousa.imchart.widget.AddFriendItemView;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class AddFriendListAdapter extends RecyclerView.Adapter<AddFriendListAdapter.AddFriendItemViewHolder> {

    private Context mContext;
    private List<AddFriendItem> mAddFriendItemList;

    public AddFriendListAdapter(Context context, List<AddFriendItem> list) {
        mContext = context;
        mAddFriendItemList = list;
    }

    @Override
    public AddFriendItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddFriendItemViewHolder(new AddFriendItemView(mContext));
    }

    @Override
    public void onBindViewHolder(AddFriendItemViewHolder holder, int position) {
        holder.mAddFriendItemView.bindView(mAddFriendItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddFriendItemList.size();
    }


    public class AddFriendItemViewHolder extends RecyclerView.ViewHolder {

        public AddFriendItemView mAddFriendItemView;

        public AddFriendItemViewHolder(AddFriendItemView itemView) {
            super(itemView);
            mAddFriendItemView = itemView;
        }
    }
}
