package dev.hw.app.streaming.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.adapter.MessageStrangerAdapter;
import dev.hw.app.streaming.helper.SessionManager;
import dev.hw.app.streaming.model.MessageStranger;
import dev.hw.app.streaming.model.Stranger;

/**
 * Fragment for stranger chat
 *
 * @author huyuit
 * @version 0.1
 * @since 05052015
 */
public class StrangerChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static String TAG = "StrangerChatFragment";
    private static final int TYPING_TIMER_LENGTH = 600;
    private List<MessageStranger> listMessages = new ArrayList<MessageStranger>();
    private RecyclerView.Adapter messageStrangerAdapter;
    private Socket mSocket;
    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private String mUsername;
    private boolean mTyping = false;
    private Gson gson;
    private Handler mTypingHandler = new Handler();
    private String partnerId = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            mSocket.emit("stop typing");
        }
    };
    private View rootView;


    {
        try {
            mSocket = IO.socket("http://thegioinguoidep.vn:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // If we are becoming visible, then...
        if (isVisibleToUser) {
            Log.d("MyFragment", "Visible. Connect");
            // TODO Connect SocketIO
            connectSocket();

        } else {
            Log.d("MyFragment", "Invisible. Disconnect");
            // TODO Disconnect SocketIO
            disConnectSocket();
        }
    }

    public static StrangerChatFragment newInstance(String param1, String param2) {
        StrangerChatFragment fragment = new StrangerChatFragment();
        Bundle args = new Bundle();
        //put any extra arguments that you may want to supply to this fragment
        fragment.setArguments(args);
        return fragment;
    }

    public StrangerChatFragment() {
        super();
    }

    /**
     * Get name from session
     */
    private String getNameFromSession() {
        SessionManager session = new SessionManager(getActivity().getApplicationContext());
        return (session.isLoggedIn()) ? (session.getUserDetail().get(0)) : null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_stranger_chat, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(messageStrangerAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        if (!mSocket.connected()) {
            /**
             * Showing Swipe Refresh animation on activity create
             * As animation won't start on onCreate, post runnable is used
             */
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            swipeRefreshLayout.setRefreshing(true);
                                        }
                                    }
            );

        }

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    mSocket.emit("typing");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageView sendButton = (ImageView) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (!mSocket.connected()) {
            mSocket.connect();
            Toast.makeText(getActivity(), getResources().getString(R.string.search_stranger), Toast.LENGTH_SHORT).show();
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mUsername = this.getNameFromSession();
        messageStrangerAdapter = new MessageStrangerAdapter(getActivity().getApplicationContext(), listMessages);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.on("enterroom", onEnterRoom);
        mSocket.on("sendchat", onSendChat);
        mSocket.on("leaveroom", onLeaveRoom);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "destroy");
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.off("enterroom", onEnterRoom);
        mSocket.off("sendchat", onSendChat);
        mSocket.off("leaveroom", onLeaveRoom);

        disConnectSocket();
    }

    private void disConnectSocket() {
        if (mSocket.connected()) {
            mSocket.disconnect();
        }
    }

    private void connectSocket() {
        if (!mSocket.connected()) {
            mSocket.connect();
            Toast.makeText(getActivity(),"Connect", Toast.LENGTH_SHORT).show();
        }
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onLeaveRoom = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addLog(getResources().getString(R.string.message_user_left, getResources().getString(R.string.stranger)));
                    leave();
                }
            });
        }
    };

    private void leave() {
        if (mSocket.connected()) {
            partnerId = null;
            mSocket.disconnect();
        }
    }

    private Emitter.Listener onEnterRoom = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String content;
                    String status;
                    try {
                        Log.d("Partner Id PUSH:", args[0].toString());
                        partnerId = data.getString("partnerid");

                        status = data.getString("status");
                        content = data.getString("content");
                    } catch (JSONException e) {
                        return;
                    }
                    // stopping swipe refresh
                    swipeRefreshLayout.setRefreshing(false);
                    addLog(getResources().getString(R.string.message_user_joined, getResources().getString(R.string.stranger)));

                }
            });
        }
    };

    private Emitter.Listener onSendChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String name;
                    String content;
                    try {
                        name = data.getString("name");
                        content = data.getString("content");
                    } catch (JSONException e) {
                        return;
                    }
                    // Add message
                    addMessage(name, content);
                }
            });
        }
    };

    private void addMessage(String name, String content) {
        listMessages.add(new MessageStranger.Builder(MessageStranger.TYPE_MESSAGE)
                .username(name).message(content).build());
        messageStrangerAdapter.notifyItemInserted(listMessages.size() - 1);
        scrollToBottom();
    }

    private void addLog(String message) {
        listMessages.add(new MessageStranger.Builder(MessageStranger.TYPE_LOG)
                .message(message).build());
        messageStrangerAdapter.notifyItemInserted(listMessages.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(messageStrangerAdapter.getItemCount() - 1);
    }


    private void attemptSend() {
        if (null == mUsername) return;
        if (!mSocket.connected()) return;

        mTyping = false;
        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message) || partnerId == null || partnerId.isEmpty()) {
            mInputMessageView.requestFocus();
            return;
        }

        gson = new Gson();
        mInputMessageView.setText("");

        Stranger stranger = new Stranger();
        stranger.setStatus("xxxx");
        stranger.setPartnerid(partnerId);
        stranger.setContent(message);
        Log.d("Send chat", partnerId);
        JSONObject objSend = null;
        try {
            objSend = new JSONObject(gson.toJson(stranger));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // perform the sending message attempt.
        mSocket.emit("sendchat", objSend);
    }
}
