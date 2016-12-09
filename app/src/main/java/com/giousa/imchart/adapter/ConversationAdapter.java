package com.giousa.imchart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.giousa.imchart.widget.ConversationItemView;
import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationItemViewHolder> {
    public static final String TAG = "ConversationAdapter";

    public Context mContext;
    public List<EMConversation> mEMConversations;

    public ConversationAdapter(Context context, List<EMConversation> conversations) {
        mContext = context;
        mEMConversations = conversations;
    }

    @Override
    public ConversationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConversationItemViewHolder(new ConversationItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ConversationItemViewHolder holder, int position) {
        holder.mConversationItemView.bindView(mEMConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return mEMConversations.size();
    }


    public class ConversationItemViewHolder extends RecyclerView.ViewHolder{

        public ConversationItemView mConversationItemView;

        public ConversationItemViewHolder(ConversationItemView itemView) {
            super(itemView);
            mConversationItemView = itemView;
        }
    }
}
