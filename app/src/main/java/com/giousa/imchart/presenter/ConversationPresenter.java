package com.giousa.imchart.presenter;

import com.hyphenate.chat.EMConversation;

import java.util.List;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public interface ConversationPresenter {

    void loadAllConversations();

    List<EMConversation> getConversations();
}
