package com.sgb.gank.net.receiver;

import com.sgb.gank.util.NetworkUtils;

/**
 * Created by panda on 2017/2/15 下午1:25.
 */
public class NetworkReceiverEvent {

    private int state;

    public NetworkReceiverEvent(@NetworkUtils.NetType int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
