package com.giousa.imchart.presenter;

import com.giousa.imchart.model.AddFriendItem;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public interface AddFriendPresenter {

    void searchFriend(String keyword);

    void onDestroy();

    List<AddFriendItem> getAddFriendList();
}
