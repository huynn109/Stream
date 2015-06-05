package dev.hw.app.streaming.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.app.AppController;
import dev.hw.app.streaming.helper.SessionManager;
import dev.hw.app.streaming.model.SubscriberResponse;

/**
 * Class login activity
 *
 * @author huyuit
 * @since 11-05-2015
 */
public class LoginActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnLogin;
    private CircularProgressView circularProgressView;
    private SessionManager session;
    private EditText etPhone;
    private EditText etPass;
    private LinearLayout llFormLogin;
    private ImageView icLogo;
    private TextView tvErrorConnect;
    private Button btnTouchRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getAllField();
        setActionField();

    }

    /**
     * Get all field form
     */
    private void getAllField() {
        btnTouchRefresh = (Button) findViewById(R.id.btn_touch_refresh);
        tvErrorConnect = (TextView) findViewById(R.id.tv_error_connection);
        icLogo = (ImageView) findViewById(R.id.logo);
        llFormLogin = (LinearLayout) findViewById(R.id.ll_form_login);
        etPhone = (EditText) findViewById(R.id.phone);
        etPass = (EditText) findViewById(R.id.pass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        circularProgressView = (CircularProgressView) findViewById(R.id.circular_progress_view);
        this.checkSessionLoggedIn();
    }

    /**
     * Set action for field
     */
    private void setActionField() {
        btnLogin.setOnClickListener(new setOnClickForm());
        btnTouchRefresh.setOnClickListener(new setOnClickForm());
    }

    /**
     * Check session login
     * If User is already logged in. Take him to main activity
     */
    private void checkSessionLoggedIn() {
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            checkLogin(session.getUserDetail().get(0), session.getUserDetail().get(1));
        }
    }

    /**
     * Handle event Onclick form
     */
    private class setOnClickForm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btnLogin) {
                hideSoftKeyboard(LoginActivity.this);
                String phone = etPhone.getText().toString();
                String pass = etPass.getText().toString();

                // Check for empty data in the form
                if (phone.trim().length() > 0 && pass.trim().length() > 0) {
                    // login user
                    checkLogin(phone, pass);
                } else {
                    // Error in login. Get the error message
                    String requiredMsg = getResources().getString(R.string.required_msg);
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            requiredMsg, Toast.LENGTH_LONG)
                            .show();
                }
            } else if(v == btnTouchRefresh){
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }

    }

    private void checkLogin(final String phone, final String pass) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        showDialog();
        hideForm();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean status = jObj.getBoolean("status");
                    // Check for error node in json
                    if (status) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);
                        // Launch main activity
                        session.setUserDetail(phone, pass);
                        SubscriberResponse subscriberGson = new Gson().fromJson(response, SubscriberResponse.class);
                        Log.d("Test user detail", phone + "--" + pass);
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        showForm();
                        String errorMsg = getResources().getString(R.string.error_msg);
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showForm();
                Log.e(TAG, "Login Error: " + error.getMessage());
                btnTouchRefresh.setText(getResources().getString(R.string.btn_touch_refresh));
                btnTouchRefresh.setVisibility(View.VISIBLE);
                tvErrorConnect.setText(getResources().getString(R.string.error_connect));
                tvErrorConnect.setVisibility(View.VISIBLE);
                hideForm();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("pass", pass);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Hide form login
     */
    private void hideForm() {
        llFormLogin.setVisibility(View.GONE);
//        icLogo.setVisibility(View.GONE);
    }

    /**
     * Show form login
     */
    private void showForm() {
        llFormLogin.setVisibility(View.VISIBLE);
//        icLogo.setVisibility(View.GONE);
    }

    /**
     * Show circular progress dialog
     */
    private void showDialog() {
        circularProgressView.setIndeterminate(true);
        circularProgressView.startAnimation();
    }

    /**
     * Hide circular progress dialog
     */
    private void hideDialog() {
        circularProgressView.setIndeterminate(false);
        circularProgressView.resetAnimation();
    }

    /**
     * Hide Keyboard when click Login button
     *
     * @param activity
     */
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
