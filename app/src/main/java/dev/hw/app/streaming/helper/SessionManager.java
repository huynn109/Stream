package dev.hw.app.streaming.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Session manager
 * <p/>
 * Created by huyuit on 5/12/2015.
 */
public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;

    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "UserLoggedIn";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_DETAIL = "userDetail";
    private static final String KEY_USER_MOBILE = "mobile";
    private static final String KEY_USER_PASS = "pass";
    private String mobile;
    private String pass;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setLogout() {
        editor.clear();
        editor.commit();
        Log.d(TAG, "User login session removed!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setUserDetail(String mobile, String pass) {
        editor.putString(KEY_USER_MOBILE, mobile);
        editor.putString(KEY_USER_PASS, pass);
        editor.commit();
        Log.d(TAG, "User detail login session");
    }

    public List<String> getUserDetail() {
        List<String> userDetailList = new ArrayList<>();
        mobile = pref.getString(KEY_USER_MOBILE, null);
        pass = pref.getString(KEY_USER_PASS, null);
        userDetailList.add(mobile);
        userDetailList.add(pass);
        return userDetailList;
    }
}
