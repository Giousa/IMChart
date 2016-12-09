package com.giousa.imchart.view;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public interface ChatView {

    void onStartSendMessage();

    void onSendMessageSuccess();

    void onSendMessageFailed();

    void onMessagesLoaded();

    void onMoreMessagesLoaded(int size);

    void onNoMoreData();
}
