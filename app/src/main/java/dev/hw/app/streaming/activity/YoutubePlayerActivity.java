package dev.hw.app.streaming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.adapter.ClipRandomAdapter;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.app.AppController;
import dev.hw.app.streaming.helper.RecyclerItemClickListener;
import dev.hw.app.streaming.model.ClipItemData;
import dev.hw.app.streaming.model.ClipItemResponse;

public class YoutubePlayerActivity extends AppCompatActivity
        implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static final String TAG = "Youtube Player Activity";

    YouTubePlayerFragment myYouTubePlayerFragment;
    private Toolbar mToolbar;
    private String name;
    private String countView;
    private String youtubeId = "fhWaJi1Hsfo";
    private String cate;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ClipRandomAdapter mClipRandomAdapter;
    private List<ClipItemData> clipItemDatas = new ArrayList<>();
    private YouTubePlayer mYoutubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_youtube_player);

        mRecyclerView = (RecyclerView) findViewById(R.id.youtube_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mClipRandomAdapter = new ClipRandomAdapter(this, clipItemDatas);
        mRecyclerView.setAdapter(mClipRandomAdapter);

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();
        youtubeId = getBundle.getString("id");
        name = getBundle.getString("name");
        countView = getBundle.getString("count_view");
        cate = getBundle.getString("cate");
        myYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtubeplayerfragment);
        myYouTubePlayerFragment.initialize(AppConfig.YOUTUBE_ANDROID_KEY, this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getDataRandomClip(youtubeId);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                youtubeId = clipItemDatas.get(position).getYoutubeId();
                mYoutubePlayer.loadVideo(youtubeId);
                // Reload random fragment listview
                getDataRandomClip(youtubeId);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        mYoutubePlayer = youTubePlayer;
        if (!wasRestored) {
            youTubePlayer.loadVideo(youtubeId);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(AppConfig.YOUTUBE_ANDROID_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtubeplayerfragment);
    }

    private void getDataRandomClip(final String youtube_id) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RANDOM_CLIP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString() + youtubeId);
                Gson gson = new Gson();
                ClipItemResponse jsonItemResponse = gson.fromJson(response.toString(), ClipItemResponse.class);

                if (jsonItemResponse.isStatus()) {
                    mClipRandomAdapter.clearAll();
                    for (int i = 0; i < jsonItemResponse.getClipItemDataResponses().size(); i++) {
                        ClipItemData clipItemData = new ClipItemData(jsonItemResponse.getClipItemDataResponses().get(i).getYOUTUBE_ID(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getTITLE_VN(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getCLICK_CNT(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getVIDEO_TYPE(),
                                "http://img.youtube.com/vi/" + jsonItemResponse.getClipItemDataResponses().get(i).getYOUTUBE_ID() + "/0.jpg");
                        clipItemDatas.add(clipItemData);
                    }
                    mClipRandomAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "clip Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("youtube_id", youtube_id);
                return params;
            }
        };
        // Adding request to request queue
        String tag_string_req = "req_new_clip";
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
