package com.liaojh.mvpdemo.presenter;

import android.content.Context;

import com.liaojh.mvpdemo.bean.UserBean;
import com.liaojh.mvpdemo.model.IUserModel;
import com.liaojh.mvpdemo.model.UserModel;
import com.liaojh.mvpdemo.ui.views.IUserView;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public class UserPresenter {
    private IUserModel mIUserModel;
    private IUserView mIUserView;

    public UserPresenter(Context context, IUserView userView) {
        mIUserModel = new UserModel(context);
        mIUserView = userView;
    }

    public void saveUser() {
        UserBean userBean = new UserBean();
        userBean.name = mIUserView.getName();
        userBean.sex = mIUserView.getSex();
        mIUserModel.setUser(userBean);
        mIUserView.clear();
    }

    public void getUser() {
        UserBean userBean = mIUserModel.loadUser();
        mIUserView.setName(userBean.name);
        mIUserView.setSex(userBean.sex);
    }
}
