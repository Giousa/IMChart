package com.giousa.imchart.presenter;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public interface ChatPresenter {

    void sendMessage(String userName, String message);

    List<EMMessage> getMessages();

    void loadMessages(String userName);

    void loadMoreMessages(String userName);

    void makeMessageRead(String userName);
}
