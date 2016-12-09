package com.giousa.imchart.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.adapter.AddFriendListAdapter;
import com.giousa.imchart.presenter.AddFriendPresenter;
import com.giousa.imchart.presenter.impl.AddFriendPresenterImpl;
import com.giousa.imchart.view.AddFriendView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class AddFriendActivity extends BaseActivity implements AddFriendView {

    public static final String TAG = "AddFriendActivity";

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.add)
    ImageView mAdd;
    @InjectView(R.id.user_name)
    EditText mUserName;
    @InjectView(R.id.search)
    ImageView mSearch;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.friend_not_found)
    TextView mFriendNotFound;

    private AddFriendPresenter mAddFriendPresenter;

    private AddFriendListAdapter mAddFriendListAdapter;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        super.init();
        mAddFriendPresenter = new AddFriendPresenterImpl(this);

        mTitle.setText(getString(R.string.add_friend));
        mUserName.setOnEditorActionListener(mOnEditorActionListener);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddFriendListAdapter = new AddFriendListAdapter(this, mAddFriendPresenter.getAddFriendList());
        mRecyclerView.setAdapter(mAddFriendListAdapter);
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFriend();
                return true;
            }
            return false;
        }
    };

    private void searchFriend() {
        hideKeyBoard();
        String keyword = mUserName.getText().toString().trim();
        mAddFriendPresenter.searchFriend(keyword);
    }

    @OnClick(R.id.search)
    public void onClick() {
        searchFriend();
    }

    @Override
    public void onStartSearch() {
        showProgress(getString(R.string.searching));
    }

    @Override
    public void onSearchSuccess() {
        hideProgress();
        mFriendNotFound.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAddFriendListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchFailed() {
        hideProgress();
        mFriendNotFound.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onAddFriendSuccess() {
        toast(getString(R.string.add_friend_success));
    }

    @Override
    public void onAddFriendFailed() {
        toast(getString(R.string.add_friend_failed));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddFriendPresenter.onDestroy();
    }
}
