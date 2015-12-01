package com.liaojh.recyclerviewdemo.view;

interface BaseRefreshHeader {
    /**
     * 移动回调
     *
     * @param delta
     */
    void onMove(float delta);

    /**
     * 释放回调
     *
     * @return
     */
    boolean releaseAction();

    /**
     * 刷新完成回调
     */
    void refreshComplete();

    /**
     * 正常状态
     */
    int STATE_NORMAL = 0;
    /**
     * 释放到刷新状态
     */
    int STATE_RELEASE_TO_REFRESH = 1;
    /**
     * 正在刷新状态
     */
    int STATE_REFRESHING = 2;
    /**
     * 刷新完成状态
     */
    int STATE_DONE = 3;
}
