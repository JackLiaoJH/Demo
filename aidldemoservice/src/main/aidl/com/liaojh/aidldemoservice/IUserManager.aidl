package com.liaojh.aidldemoservice;

import com.liaojh.aidldemoservice.User;
import com.liaojh.aidldemoservice.IOnAddNewUserListener;
/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
interface IUserManager {
    void addUser(in User user);

    List<User> getUsers();

    void registerListener(IOnAddNewUserListener listener);

    void unregisterListener(IOnAddNewUserListener listener);
}
