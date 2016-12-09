package com.giousa.imchart.presenter;

import com.giousa.imchart.model.ContactListItem;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public interface ContactPresenter {

    void getContactsFromServer();

    List<ContactListItem> getContactList();

    void refreshContactList();

    void deleteFriend(String name);
}
