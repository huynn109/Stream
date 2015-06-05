package dev.hw.app.streaming.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.activity.YoutubePlayerActivity;
import dev.hw.app.streaming.adapter.ClipAdapter;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.app.AppController;
import dev.hw.app.streaming.helper.RecyclerItemClickListener;
import dev.hw.app.streaming.model.ClipItemData;
import dev.hw.app.streaming.model.ClipItemResponse;

/**
 * Fragment for list row clip ---- new clip
 *
 * @version 1.0
 * @author huyuit
 * @since 03062015
 */

public class ClipFragment extends Fragment {

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 4;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int page = 1;

    private static final String TAG = "ClipFragment";
    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ClipAdapter mClipAdapter;
    private List<ClipItemData> clipItemDatas = new ArrayList<>();

    public static ClipFragment newInstance(String param1, String param2) {
        ClipFragment fragment = new ClipFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ClipFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_clip, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.clip_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mClipAdapter = new ClipAdapter(getActivity().getApplicationContext(), clipItemDatas);
        mRecyclerView.setAdapter(mClipAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", clipItemDatas.get(position).getYoutubeId());
                bundle.putString("name", clipItemDatas.get(position).getName());
                bundle.putString("cate", clipItemDatas.get(position).getCate());
                bundle.putString("count_view", clipItemDatas.get(position).getCountView());
                Intent intent = new Intent(getActivity(), YoutubePlayerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    Log.i("...", "end called");
                    // Do something
                    mClipAdapter.remove(mClipAdapter.getItemCount() - 1);
                    getDataNewClip(++page);
                    loading = true;
                }
            }
        });

        getDataNewClip(page);

        return rootView;
    }

    private void getDataNewClip(final int page) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_NEW_CLIP, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                ClipItemResponse jsonItemResponse = gson.fromJson(response.toString(), ClipItemResponse.class);

                if (jsonItemResponse.isStatus()) {
                    for (int i = 0; i < jsonItemResponse.getClipItemDataResponses().size(); i++) {
                        ClipItemData clipItemData = new ClipItemData(jsonItemResponse.getClipItemDataResponses().get(i).getYOUTUBE_ID(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getTITLE_VN(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getCLICK_CNT(),
                                jsonItemResponse.getClipItemDataResponses().get(i).getVIDEO_TYPE(),
                                "http://img.youtube.com/vi/" + jsonItemResponse.getClipItemDataResponses().get(i).getYOUTUBE_ID() + "/0.jpg");
                        clipItemDatas.add(clipItemData);
                    }
                    mClipAdapter.notifyDataSetChanged();
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
                params.put("page", String.valueOf(page));
                return params;
            }
        };
        // Adding request to request queue
        String tag_string_req = "req_new_clip";
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


}
