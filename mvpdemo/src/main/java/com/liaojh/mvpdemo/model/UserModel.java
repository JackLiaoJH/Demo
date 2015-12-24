package com.liaojh.mvpdemo.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.liaojh.mvpdemo.bean.UserBean;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO 具体处理逻辑的地方
 */
public class UserModel implements IUserModel {
    SharedPreferences sharedPreferences;

    public UserModel(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    @Override
    public void setUser(UserBean user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", user.name).commit();
        editor.putString("sex", user.sex).commit();
    }

    @Override
    public UserBean loadUser() {
        UserBean userBean = new UserBean();
        userBean.name = sharedPreferences.getString("name", "");
        userBean.sex = sharedPreferences.getString("sex", "");
        return userBean;
    }
}
