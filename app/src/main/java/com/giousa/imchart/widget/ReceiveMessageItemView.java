package com.giousa.imchart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ReceiveMessageItemView extends RelativeLayout {

    @InjectView(R.id.timestamp)
    TextView mTimestamp;
    @InjectView(R.id.receive_message)
    TextView mReceiveMessage;

    public ReceiveMessageItemView(Context context) {
        this(context, null);
    }

    public ReceiveMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_receive_message_item, this);
        ButterKnife.inject(this, this);
    }

    public void bindView(EMMessage emMessage, boolean showTimestamp) {
        updateTimestamp(emMessage, showTimestamp);
        updateMessageBody(emMessage);
    }

    private void updateTimestamp(EMMessage emMessage, boolean showTimestamp) {
        if (showTimestamp) {
            mTimestamp.setVisibility(VISIBLE);
            String time = DateUtils.getTimestampString(new Date(emMessage.getMsgTime()));
            mTimestamp.setText(time);
        } else {
            mTimestamp.setVisibility(GONE);
        }
    }

    private void updateMessageBody(EMMessage emMessage) {
        EMMessageBody body = emMessage.getBody();
        if (body instanceof EMTextMessageBody) {
            mReceiveMessage.setText(((EMTextMessageBody) body).getMessage());
        } else {
            mReceiveMessage.setText(getContext().getString(R.string.no_text_message));
        }
    }
}
