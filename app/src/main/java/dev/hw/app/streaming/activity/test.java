package dev.hw.app.streaming.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;
import java.util.List;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.adapter.ClipAdapter;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.helper.RecyclerItemClickListener;
import dev.hw.app.streaming.model.ClipItemData;

public class test extends AppCompatActivity {

    private static final String YOUR_GOOGLE_API_KEY = AppConfig.YOUTUBE_ANDROID_KEY;
    private YouTubePlayerSupportFragment youTubePlayerSupportFragment;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<ClipItemData> clipItemDatas = new ArrayList<>();
    private ClipAdapter mClipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.viewpager);
        loadVideo("To0tJu75ugg");

        mRecyclerView = (RecyclerView) findViewById(R.id.clip_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        for (int i = 0; i < 50; i++) {
            ClipItemData clipItemData = new ClipItemData("To0tJu75ugg",
                    "Test",
                    "123",
                    "XE",
                    "http://img.youtube.com/vi/" + "To0tJu75ugg" + "/0.jpg");

            clipItemDatas.add(clipItemData);
        }

        // specify an adapter (see also next example)
        mClipAdapter = new ClipAdapter(this, clipItemDatas);
        mRecyclerView.setAdapter(mClipAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                loadVideo("dSajQAGo6x8");
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
    }

    private void loadVideo(final String videoid) {
            youTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, youTubePlayerSupportFragment).commit();

        youTubePlayerSupportFragment.initialize(YOUR_GOOGLE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean isRecovered) {
                if (!isRecovered) {
                    youTubePlayer.loadVideo(videoid);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
