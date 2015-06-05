package dev.hw.app.streaming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.activity.StreamActivity;
import dev.hw.app.streaming.adapter.HomeListAdapter;
import dev.hw.app.streaming.app.AppConfig;
import dev.hw.app.streaming.model.User;
import io.vov.vitamio.LibsChecker;

/**
 * Created by huyuit on 5/11/2015.
 */

public class StreamFragment extends Fragment {

    private View rootView;
    private ListView listViewHome;
    private List<User> userList;
    private HomeListAdapter homeListAdapter;

    public static StreamFragment newInstance(String param1, String param2) {
        StreamFragment fragment = new StreamFragment();
        Bundle args = new Bundle();
        //put any extra arguments that you may want to supply to this fragment
        fragment.setArguments(args);
        return fragment;
    }

    public StreamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(getActivity()))
            return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_stream, container, false);
        userList = new ArrayList<>();
        listViewHome = (ListView) rootView.findViewById(R.id.list_home);

        homeListAdapter = new HomeListAdapter(getActivity(), userList);
        listViewHome.setAdapter(homeListAdapter);

        demo();
        events();
        return rootView;
    }

    private void events() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = userList.get(position).getLinkStream();
                Bundle bundle = new Bundle();
                bundle.putString("link", link);
                bundle.putString("name", userList.get(position).getName());
                bundle.putInt("count_view", userList.get(position).getCountView());
                bundle.putInt("room", userList.get(position).getRoom());
                bundle.putString("group_id", userList.get(position).getGroupId());
                Intent intent = new Intent(getActivity(), StreamActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void demo() {

        User one = new User("Haiwin Streaming", "http://thegioinguoidep.vn/assets/img-albums/avatar/8.jpg", 1234, 1, AppConfig.URL_RTMP_PATH_ONE, "myStream");
        User two = new User("ANTV live", "http://thegioinguoidep.vn/assets/img-albums/avatar/antv.png", 1234, 2, AppConfig.URL_RTMP_PATH_TWO, "6oF2jDGLQe0");
        User three = new User("VTC1 live", "http://thegioinguoidep.vn/assets/img-albums/avatar/vtc1.png", 1234, 3, AppConfig.URL_RTMP_PATH_THREE, "oI3pxPBYLfo");
        User four = new User("VTC3 live", "http://thegioinguoidep.vn/assets/img-albums/avatar/vtc3.png", 1234, 4, AppConfig.URL_RTMP_PATH_FOUR, "l8MuZPMKUfk");

        userList.add(one);
        userList.add(two);
        userList.add(three);
        userList.add(four);

        homeListAdapter.notifyDataSetChanged();
    }
}


