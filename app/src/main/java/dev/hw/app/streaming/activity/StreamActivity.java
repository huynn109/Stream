package dev.hw.app.streaming.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.adapter.MessageListAdapter;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.helper.SessionManager;
import dev.hw.app.streaming.model.JoinRoom;
import dev.hw.app.streaming.model.Message;
import dev.hw.app.streaming.model.MessageResponse;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class StreamActivity extends AppCompatActivity implements
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnInfoListener {

    private static final String TAG = "StreamFragment";
    private VideoView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer mMediaPlayer;
    private String path = AppConfig.URL_RTMP_PATH_HOME;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    private int mVideoWidth;
    private int mVideoHeight;
    private TextView mSubtitleView;
    private int mVideoLayout = 0;
    private String subtitle_path = "";
    private long mPosition = 0;
    private ImageView iconFullScreen;
    private FloatingActionButton iconActionChat;
    private String dataJson;

    private String strJson = "{\"type\":\"usermsg\",\"group\":1,\"name\":\"Huy\",\"message\":null,\"color\":\"registergroup\"}";

    // Adapter
    private ListView lvMessage;
    private List<Message> listMessages;
    private MessageListAdapter messageListAdapter;

    private MessageResponse strMessageJson;
    private Message message;
    private MessageResponse messageResponse;
    private ImageView btnSend;
    private TextView etMessage;
    private ActionBar aBar;
    private ImageView iconArrowBack;
    private CircularProgressView myProgressBar;
    private View rootView;
    private String randColor;
    private LinearLayout llBottom;
    private FrameLayout flVideoContainer;
    private ImageView iconExitFullScreen;
    private View decorView;
    private TextView tv_name;
    private TextView tv_room;
    private TextView tv_count_view;
    private String link;
    private String name;
    private int countView;
    private int room;
    private Socket mSocket;
    private String groupId;
    private String userName;
    private Gson gson;
    private JSONObject objSend = null;

    {
        try {
            mSocket = IO.socket("http://thegioinguoidep.vn:3001");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor
     */
    public StreamActivity() {
        super();
    }


    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            surfaceView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0);
            Log.e("On Config Change", "LANDSCAPE");

        } else {
            surfaceView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
            Log.e("On Config Change", "PORTRAIT");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView().findViewById(R.id.fl_video_container);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_stream);

        // Get bundle from home fragment
        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        name = getBundle.getString("name");
        countView = getBundle.getInt("count_view");
        room = getBundle.getInt("room");
        groupId = getBundle.getString("group_id");
        link = getBundle.getString("link");
        if (!link.isEmpty()) {
            path = link;
        }
        // Get field name
        this.getNameFieldOnForm();
        // Event
        this.setEventFieldOnForm();
        // Setting play video stream
        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(
                    this,
                    "Please edit VideoBuffer Activity, and set path"
                            + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
        } else {
      /*
       * Alternatively,for streaming media you can use
       * mVideoView.setVideoURI(Uri.parse(URLstring));
       */
            surfaceView.setVideoURI(Uri.parse(path));
            surfaceView.requestFocus();
            surfaceView.setOnInfoListener(this);
            surfaceView.setOnBufferingUpdateListener(this);
            surfaceView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }


        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        if (!mSocket.connected()) {
            // Connect to server socket io
            mSocket.connect();
        }
        gson = new Gson();
        userName = this.getNameFromSession();
        JoinRoom joinRoom = new JoinRoom();
        joinRoom.setGroupid(groupId);
        joinRoom.setUname(userName);
        joinRoom.setColor(randomColor());
        try {
            objSend = new JSONObject(gson.toJson(joinRoom));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send  group Id to Socket server
        mSocket.emit("jointroom", objSend);
        mSocket.on("jointroom", onJoinRoom);
        mSocket.on("sendchat", onSendChat);
        mSocket.on("leaveroom", onLeaveRoom);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSocket.connected()) {
            mSocket.disconnect();
        }

        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.off("jointroom", onJoinRoom);
        mSocket.off("sendchat", onSendChat);
        mSocket.off("leaveroom", onLeaveRoom);
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onLeaveRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String groupid;
                    String content;
                    String uName;
                    String color;
                    try {
                        groupid = data.getString("groupid");
                        content = data.getString("content");
                        uName = data.getString("uname");
                        color = data.getString("color");
                    } catch (JSONException e) {
                        return;
                    }
                    Log.d("Content message", content);
                    // Add message
                    addMessage(uName, content, null);
                }
            });
        }
    };
    private Emitter.Listener onJoinRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String groupid;
                    String content;
                    String uName;
                    String color;
                    try {
                        groupid = data.getString("groupid");
                        content = data.getString("content");
                        uName = data.getString("uname");
                        color = data.getString("color");
                    } catch (JSONException e) {
                        return;
                    }
                    Log.d("Content message", content);
                    // Add message
                    addMessage(null, content, null);
                }
            });
        }
    };

    private Emitter.Listener onSendChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String groupid;
                    String content;
                    String uName;
                    String color;
                    try {
                        groupid = data.getString("groupid");
                        content = data.getString("content");
                        uName = data.getString("uname");
                        color = data.getString("color");
                    } catch (JSONException e) {
                        return;
                    }
                    Log.d("Content message", content);
                    // Add message
                    addMessage(uName, content, color);
                }
            });
        }
    };

    private void scrollToBottom() {
        lvMessage.setSelection(messageListAdapter.getCount() - 1);
    }

    private void addMessage(String name, String content, String color) {
        Message message = new Message(name, content, color);
        listMessages.add(message);
        messageListAdapter.notifyDataSetChanged();
        scrollToBottom();
    }

    @Override
    public void onPause() {
        super.onPause();
//        aBar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "On resume called");
//        aBar.hide();
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (surfaceView.isPlaying()) {
                    surfaceView.pause();
                    showProgress();
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                surfaceView.start();
                hideProgress();
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    /**
     * Get all field name
     */
    private void getNameFieldOnForm() {
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        flVideoContainer = (FrameLayout) findViewById(R.id.fl_video_container);
        myProgressBar = (CircularProgressView) findViewById(R.id.circular_progress_view_video);
        etMessage = (TextView) findViewById(R.id.et_message);
        btnSend = (ImageView) findViewById(R.id.btn_send);
        surfaceView = (VideoView) findViewById(R.id.surface_view);
        iconFullScreen = (ImageView) findViewById(R.id.icon_full_screen);
        iconExitFullScreen = (ImageView) findViewById(R.id.icon_exit_full_screen);
        iconArrowBack = (ImageView) findViewById(R.id.icon_arrow_back);
        lvMessage = (ListView) findViewById(R.id.list_view_messages);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_room = (TextView) findViewById(R.id.tv_room);
        tv_count_view = (TextView) findViewById(R.id.tv_count_view);
    }

    /**
     * Event for field form
     */
    private void setEventFieldOnForm() {
        // Listview
        listMessages = new ArrayList<Message>();
        messageListAdapter = new MessageListAdapter(this, listMessages);
        lvMessage.setAdapter(messageListAdapter);
        // Event
        iconArrowBack.setOnClickListener(onClickListenerFragment);
        iconFullScreen.setOnClickListener(onClickListenerFragment);
        iconExitFullScreen.setOnClickListener(onClickListenerFragment);
        btnSend.setOnClickListener(onClickListenerFragment);
        surfaceView.setOnTouchListener(onTouchListenerFragment);
        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
        setViewForm();

//        lvMessage.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                hideSoftKeyboard(getActivity());
//                return true;
//            }
//        });
    }

    /**
     * Send message
     */
    private void attemptSend() {
        if (!mSocket.connected()) return;

        String message = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            etMessage.requestFocus();
            return;
        }

        gson = new Gson();
        etMessage.setText("");

        gson = new Gson();
        userName = this.getNameFromSession();
        JoinRoom sendMessage = new JoinRoom();
        sendMessage.setGroupid(groupId);
        sendMessage.setContent(message);
        sendMessage.setUname(userName);
        sendMessage.setColor(randomColor());
        try {
            objSend = new JSONObject(gson.toJson(sendMessage));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send  group Id to Socket server
        mSocket.emit("sendchat", objSend);
    }

    /**
     * Set view for name, room, view count
     */
    private void setViewForm() {
        tv_name.setText(name);
        tv_room.setText(String.valueOf(room));
        tv_count_view.setText(String.valueOf(countView));
    }


    /**
     * Setting video stream
     */
    private void playVideo() {

    }


    /**
     * Function hadle event OnClick
     */
    private View.OnClickListener onClickListenerFragment = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btn_send:
                    attemptSend();
                    break;
                case R.id.icon_full_screen:
                    changeToLandcase();
                    break;
                case R.id.icon_exit_full_screen:
                    changeToPortrait();
                    break;
                case R.id.icon_arrow_back:
                    finish();
                    break;
            }

        }
    };

    /**
     * Fragment full screen streaming
     */

    private void replaceFragmentFullScreen() {

    }

    private void changeToLandcase() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        llBottom.setVisibility(View.GONE);
        android.view.ViewGroup.LayoutParams layoutParams = flVideoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        flVideoContainer.setLayoutParams(layoutParams);
        iconExitFullScreen.setVisibility(View.VISIBLE);
        iconFullScreen.setVisibility(View.GONE);
    }

    private void changeToPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        llBottom.setVisibility(View.VISIBLE);
        android.view.ViewGroup.LayoutParams layoutParams = flVideoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = 0;
        flVideoContainer.setLayoutParams(layoutParams);
        iconExitFullScreen.setVisibility(View.GONE);
        iconFullScreen.setVisibility(View.VISIBLE);
    }


    /**
     * Hide Keyboard when click send button
     *
     * @param activity
     */
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Function handle event OnTouch
     */
    private View.OnTouchListener onTouchListenerFragment = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.surface_view:
                    showHideIconInVideo();
                    break;
            }
            return false;
        }
    };

    /**
     * Function show hide icon fullscreen
     */

    private void showHideIconInVideo() {
//        if (surfaceView.isPlaying()) {
//            surfaceView.pause();
//        } else {
//            surfaceView.start();
//        }
        if (iconFullScreen.getVisibility() == View.VISIBLE || iconExitFullScreen.getVisibility() == View.VISIBLE) {
            iconFullScreen.setVisibility(View.GONE);
            iconArrowBack.setVisibility(View.GONE);
            iconExitFullScreen.setVisibility(View.GONE);
            // hideSystemUI();

        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                iconExitFullScreen.setVisibility(View.VISIBLE);
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                iconFullScreen.setVisibility(View.VISIBLE);
            }

            iconArrowBack.setVisibility(View.VISIBLE);

            // showSystemUI();
        }
    }

    /**
     * Get name from session
     */

    private String getNameFromSession() {
        SessionManager session = new SessionManager(getApplicationContext());
        return (session.isLoggedIn()) ? (session.getUserDetail().get(0)) : null;
    }

    /**
     * Random color
     *
     * @return int color
     */
    private String randomColor() {
        int idx = new Random().nextInt(AppConfig.ARR_COLOR.length);
        return ((AppConfig.ARR_COLOR[idx]));
    }

    /**
     * Show circular progress dialog
     */
    private void showProgress() {
        myProgressBar.setIndeterminate(true);
        myProgressBar.startAnimation();
    }

    /**
     * Hide circular progress dialog
     */
    private void hideProgress() {
        myProgressBar.setIndeterminate(false);
        myProgressBar.resetAnimation();
    }
}
