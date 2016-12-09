package com.giousa.imchart.event;

/**
 * Description:
 * Author:Giousa
 * Date:2016/12/9
 * Email:65489469@qq.com
 */
public class AddFriendEvent {
    public static final String TAG = "AddFriendEvent";

    private String mFriendName;

    private String mReason;

    public AddFriendEvent(String friendName, String reason) {
        this.mFriendName = friendName;
        this.mReason = reason;
    }


    public String getFriendName() {
        return mFriendName;
    }

    public void setFriendName(String friendName) {
        this.mFriendName = friendName;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        this.mReason = reason;
    }
}
