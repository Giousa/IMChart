package com.giousa.imchart.factory;

import com.giousa.imchart.R;
import com.giousa.imchart.ui.fragment.BaseFragment;
import com.giousa.imchart.ui.fragment.ContactFragment;
import com.giousa.imchart.ui.fragment.ConversationFragment;
import com.giousa.imchart.ui.fragment.DynamicFragment;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class FragmentFactory {

    public static final String TAG = FragmentFactory.class.getSimpleName();

    private static FragmentFactory sFragmentFactory;

    private BaseFragment mMessageFragment;
    private BaseFragment mContactFragment;
    private BaseFragment mDynamicFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragment(int id) {
        switch (id) {
            case R.id.conversations:
                return getConversationFragment();
            case R.id.contacts:
                return getContactFragment();
            case R.id.dynamic:
                return getDynamicFragment();
        }
        return null;
    }

    private BaseFragment getConversationFragment() {
        if (mMessageFragment == null) {
            mMessageFragment = new ConversationFragment();
        }
        return mMessageFragment;
    }

    private BaseFragment getDynamicFragment() {
        if (mDynamicFragment == null) {
            mDynamicFragment = new DynamicFragment();
        }
        return mDynamicFragment;
    }

    private BaseFragment getContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }

}
