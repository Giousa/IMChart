package com.giousa.imchart.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.adapter.ContactListAdapter;
import com.giousa.imchart.adapter.EMContactListenerAdapter;
import com.giousa.imchart.app.Constant;
import com.giousa.imchart.model.ContactListItem;
import com.giousa.imchart.presenter.ContactPresenter;
import com.giousa.imchart.presenter.impl.ContactPresenterImpl;
import com.giousa.imchart.ui.activity.AddFriendActivity;
import com.giousa.imchart.ui.activity.ChatActivity;
import com.giousa.imchart.view.ContactView;
import com.giousa.imchart.view.SlideBar;
import com.hyphenate.chat.EMClient;

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
public class ContactFragment extends BaseFragment implements ContactView {

    public static final String TAG = "ContactFragment";
    private static final int POSITION_NOT_FOUND = -1;

    @InjectView(R.id.back)
    ImageView mBack;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.add)
    ImageView mAdd;
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.slide_bar)
    SlideBar mSlideBar;
    @InjectView(R.id.section)
    TextView mSection;

    private ContactListAdapter mContactListAdapter;

    private ContactPresenter mContactPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();
        mContactPresenter = new ContactPresenterImpl(this);
        initView();
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        mContactPresenter.getContactsFromServer();
    }

    private void initView() {
        mTitle.setText(getString(R.string.contacts));
        mAdd.setVisibility(View.VISIBLE);

        initRecyclerView();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.qq_red);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mSlideBar.setOnSlidingBarChangeListener(mOnSlideBarChangeListener);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mContactListAdapter = new ContactListAdapter(getContext(), mContactPresenter.getContactList());
        mContactListAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mContactListAdapter);
    }

    private ContactListAdapter.OnItemClickListener mOnItemClickListener = new ContactListAdapter.OnItemClickListener() {

        /**
         * 单击跳转到聊天界面
         * @param name 点击item的联系人名字
         */
        @Override
        public void onItemClick(String name) {
            startActivity(ChatActivity.class, Constant.Extra.USER_NAME, name);
        }

        /**
         * 长按删除好友
         * @param name 点击item的联系人名字
         */
        @Override
        public void onItemLongClick(final String name) {
            showDeleteFriendDialog(name);
        }
    };

    private void showDeleteFriendDialog(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String message = String.format(getString(R.string.delete_friend_message), name);
        builder.setTitle(getString(R.string.delete_friend))
                .setMessage(message)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showProgress(getString(R.string.deleting_friend));
                        mContactPresenter.deleteFriend(name);

                    }
                });
        builder.show();
    }

    private EMContactListenerAdapter mEMContactListener = new EMContactListenerAdapter() {

        @Override
        public void onContactAdded(String s) {
            mContactPresenter.refreshContactList();
        }

        @Override
        public void onContactDeleted(String s) {
            mContactPresenter.refreshContactList();
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContactPresenter.refreshContactList();
        }
    };

    private SlideBar.OnSlideBarChangeListener mOnSlideBarChangeListener = new SlideBar.OnSlideBarChangeListener() {
        @Override
        public void onSectionChange(int index, String section) {
            mSection.setVisibility(View.VISIBLE);
            mSection.setText(section);
            scrollToSection(section);
        }

        @Override
        public void onSlidingFinish() {
            mSection.setVisibility(View.GONE);
        }
    };

    /**
     * RecyclerView滚动直到界面出现对应section的联系人
     *
     * @param section 首字符
     */
    private void scrollToSection(String section) {
        int sectionPosition = getSectionPosition(section);
        if (sectionPosition != POSITION_NOT_FOUND) {
            mRecyclerView.smoothScrollToPosition(sectionPosition);
        }
    }

    /**
     *
     * @param section 首字符
     * @return 在联系人列表中首字符是section的第一个联系人在联系人列表中的位置
     */
    private int getSectionPosition(String section) {
        List<ContactListItem> contactListItems = mContactListAdapter.getContactListItems();
        for (int i = 0; i < contactListItems.size(); i++) {
            if (section.equals(contactListItems.get(i).getFirstLetterString())) {
                return i;
            }
        }
        return POSITION_NOT_FOUND;
    }

    @OnClick(R.id.add)
    public void onClick() {
        startActivity(AddFriendActivity.class, false);
    }

    @Override
    public void onGetContactListSuccess() {
        mContactListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetContactListFailed() {
        toast(getString(R.string.get_contacts_error));
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDeleteFriendSuccess() {
        hideProgress();
        toast(getString(R.string.delete_friend_success));
        mContactPresenter.refreshContactList();
    }

    @Override
    public void onDeleteFriendFailed() {
        hideProgress();
        toast(getString(R.string.delete_friend_failed));
    }
}
