package com.giousa.imchart.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.adapter.EMMessageListenerAdapter;
import com.giousa.imchart.adapter.MessageListAdapter;
import com.giousa.imchart.adapter.TextWatcherAdapter;
import com.giousa.imchart.app.Constant;
import com.giousa.imchart.presenter.ChatPresenter;
import com.giousa.imchart.presenter.impl.ChatPresenterImpl;
import com.giousa.imchart.utils.ThreadUtils;
import com.giousa.imchart.view.ChatView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ChatActivity extends BaseActivity implements ChatView {

    public static final String TAG = "ChatActivity";

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.add)
    ImageView mAdd;
    @InjectView(R.id.edit)
    EditText mEdit;
    @InjectView(R.id.send)
    Button mSend;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ChatPresenter mChatPresenter;
    private String mUserName;
    private MessageListAdapter mMessageListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init() {
        super.init();
        mChatPresenter = new ChatPresenterImpl(this);
        mBack.setVisibility(View.VISIBLE);
        mUserName = getIntent().getStringExtra(Constant.Extra.USER_NAME);
        String title = String.format(getString(R.string.chat_with), mUserName);
        mTitle.setText(title);
        mEdit.setOnEditorActionListener(mOnEditorActionListener);
        mEdit.addTextChangedListener(mTextWatcher);

        initRecyclerView();

        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
        mChatPresenter.loadMessages(mUserName);
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageListAdapter = new MessageListAdapter(this, mChatPresenter.getMessages());
        mRecyclerView.setAdapter(mMessageListAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition == 0) {
                    mChatPresenter.loadMoreMessages(mUserName);
                }
            }
        }
    };
    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage();
                return true;
            }
            return false;
        }
    };

    private void sendMessage() {
        mChatPresenter.sendMessage(mUserName, mEdit.getText().toString().trim());
        hideKeyBoard();
        mEdit.getText().clear();
    }

    private TextWatcherAdapter mTextWatcher = new TextWatcherAdapter() {
        @Override
        public void afterTextChanged(Editable s) {
            mSend.setEnabled(s.length() != 0);
        }
    };

    private EMMessageListenerAdapter mEMMessageListener = new EMMessageListenerAdapter() {
        @Override
        public void onMessageReceived(final List<EMMessage> list) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final EMMessage emMessage = list.get(0);
                    if (emMessage.getUserName().equals(mUserName)) {
                        mChatPresenter.makeMessageRead(mUserName);
                        mMessageListAdapter.addNewMessage(emMessage);
                        smoothScrollToBottom();
                    }
                }
            });
        }
    };

    private void smoothScrollToBottom() {
        mRecyclerView.smoothScrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    @OnClick({R.id.back, R.id.send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send:
                sendMessage();
                break;
        }
    }

    @Override
    public void onStartSendMessage() {
        updateList();
    }

    private void updateList() {
        mMessageListAdapter.notifyDataSetChanged();
        smoothScrollToBottom();
    }

    @Override
    public void onSendMessageSuccess() {
        hideProgress();
        toast(getString(R.string.send_success));
        updateList();
    }

    @Override
    public void onSendMessageFailed() {
        hideProgress();
        toast(getString(R.string.send_failed));
    }

    @Override
    public void onMessagesLoaded() {
        toast(getString(R.string.load_data_success));
        mMessageListAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    private void scrollToBottom() {
        mRecyclerView.scrollToPosition(mMessageListAdapter.getItemCount() - 1);
    }

    @Override
    public void onMoreMessagesLoaded(int size) {
        toast(getString(R.string.load_more_data_success));
        mMessageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(size);
    }

    @Override
    public void onNoMoreData() {
        toast(getString(R.string.no_more_data));
    }
}
