package com.example.bassant.finderapp;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by hp on 7/1/2017.
 */

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("userName", user.getUserName());
        userLocalDatabaseEditor.putString("password", user.getPassword());
        userLocalDatabaseEditor.putString("email", user.getEmail());
        userLocalDatabaseEditor.putString("mobileNumber", user.getMobile());
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public User getLoggedInUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String userName = userLocalDatabase.getString("userName", "");
        String password = userLocalDatabase.getString("password", "");
        String email = userLocalDatabase.getString("email", "");
        String mobileNumber = userLocalDatabase.getString("mobileNumber","");

        User user = new User(userName, password, email, mobileNumber);
        return user;
    }



}
