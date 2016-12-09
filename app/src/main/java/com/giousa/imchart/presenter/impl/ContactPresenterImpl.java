package com.giousa.imchart.presenter.impl;

import com.giousa.imchart.model.ContactListItem;
import com.giousa.imchart.presenter.ContactPresenter;
import com.giousa.imchart.view.ContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class ContactPresenterImpl implements ContactPresenter {

    private static final String TAG = "ContactPresenterImpl";

    private ContactView mContactView;

    private List<ContactListItem> mContactListItems;

    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
        mContactListItems = new ArrayList<ContactListItem>();
    }
    @Override
    public void getContactsFromServer() {
    }

    @Override
    public List<ContactListItem> getContactList() {
        return mContactListItems;
    }

    @Override
    public void refreshContactList() {
        mContactListItems.clear();
        getContactsFromServer();
    }

    @Override
    public void deleteFriend(String name) {

    }
}
