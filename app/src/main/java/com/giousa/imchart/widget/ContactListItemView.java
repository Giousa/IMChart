package com.giousa.imchart.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giousa.imchart.R;
import com.giousa.imchart.model.ContactListItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ContactListItemView extends RelativeLayout {
    public static final String TAG = "ContactItemView";

    @InjectView(R.id.section)
    TextView mSection;
    @InjectView(R.id.user_name)
    TextView mUserName;

    public ContactListItemView(Context context) {
        this(context, null);
    }

    public ContactListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_contact_item, this);
        ButterKnife.inject(this, this);

    }

    public void bindView(ContactListItem contactListItem) {
        mUserName.setText(contactListItem.userName);
        if (contactListItem.showFirstLetter) {
            mSection.setVisibility(VISIBLE);
            mSection.setText(contactListItem.getFirstLetterString());
        } else {
            mSection.setVisibility(GONE);
        }
    }
}

