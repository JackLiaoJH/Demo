package com.liaojh.mvpdemo.model;

import com.liaojh.mvpdemo.bean.UserBean;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public interface IUserModel {
    void setUser(UserBean user);

    UserBean loadUser();
}
