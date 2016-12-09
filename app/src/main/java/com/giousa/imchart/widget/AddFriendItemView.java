package com.giousa.imchart.widget;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.event.AddFriendEvent;
import com.giousa.imchart.model.AddFriendItem;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddFriendItemView extends RelativeLayout {
    public static final String TAG = "AddFriendItemView";
    @InjectView(R.id.user_name)
    TextView mUserName;
    @InjectView(R.id.timestamp)
    TextView mTimestamp;
    @InjectView(R.id.add)
    Button mAdd;


    public AddFriendItemView(Context context) {
        this(context, null);
    }

    public AddFriendItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_add_friend_item, this);
        ButterKnife.inject(this, this);
    }

    public void bindView(AddFriendItem addFriendItem) {
        mUserName.setText(addFriendItem.userName);
        mTimestamp.setText(addFriendItem.timestamp);
        if (addFriendItem.isAdded) {
            mAdd.setText(getContext().getString(R.string.added));
            mAdd.setEnabled(false);
        } else {
            mAdd.setText(getContext().getString(R.string.add));
            mAdd.setEnabled(true);
        }
    }

    @OnClick(R.id.add)
    public void onClick() {
        String friendName = mUserName.getText().toString().trim();
        String addFriendReason = getContext().getString(R.string.add_friend_reason);
        AddFriendEvent event = new AddFriendEvent(friendName, addFriendReason);
        EventBus.getDefault().post(event);
    }

}
