package com.giousa.imchart.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.adapter.ConversationAdapter;
import com.giousa.imchart.adapter.EMMessageListenerAdapter;
import com.giousa.imchart.presenter.ConversationPresenter;
import com.giousa.imchart.presenter.impl.ConversationPresenterImpl;
import com.giousa.imchart.utils.ThreadUtils;
import com.giousa.imchart.view.ConversationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ConversationFragment extends BaseFragment implements ConversationView {

    public static final String TAG = "ConversationFragment";

    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ConversationAdapter mConversationAdapter;

    private ConversationPresenter mConversationPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void init() {
        super.init();
        mConversationPresenter = new ConversationPresenterImpl(this);
        mTitle.setText(getString(R.string.messages));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationAdapter = new ConversationAdapter(getContext(), mConversationPresenter.getConversations());
        mRecyclerView.setAdapter(mConversationAdapter);

        mConversationPresenter.loadAllConversations();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);

    }

    @Override
    public void onAllConversationsLoaded() {
        toast(getString(R.string.load_conversations_success));
        mConversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mConversationAdapter.notifyDataSetChanged();
    }

    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        @Override
        public void onMessageReceived(List<EMMessage> list) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast(getString(R.string.receive_new_message));
                    mConversationPresenter.loadAllConversations();
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }
}
