// IOnAddNewUserListener.aidl
package com.liaojh.aidldemoservice;
import com.liaojh.aidldemoservice.User;

// Declare any non-default types here with import statements

interface IOnAddNewUserListener {
   void onAddNewUser(in User user);
}
