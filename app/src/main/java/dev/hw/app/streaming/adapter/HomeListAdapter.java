package dev.hw.app.streaming.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.app.AppController;
import dev.hw.app.streaming.model.User;

/**
 * Created by huyuit on 5/19/2015.
 */
public class HomeListAdapter extends BaseAdapter {
    private Activity activity;
    private List<User> userList;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public HomeListAdapter(Activity activity, List<User> userList) {
        this.activity = activity;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_home, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView avatar = (NetworkImageView) convertView
                .findViewById(R.id.imv_avatar);
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView room = (TextView) convertView.findViewById(R.id.tv_room);
        TextView countView = (TextView) convertView.findViewById(R.id.tv_count_view);

        // Getting user data for the row
        User user = userList.get(position);
        // Thumbnail image avatar
        avatar.setImageUrl(user.getImageUrl(), imageLoader);
        // Name
        name.setText(user.getName());
        // Room
        room.setText(activity.getString(R.string.list_row_room) + ": " + String.valueOf(user.getRoom()));
        // Watching
        countView.setText(activity.getString(R.string.list_row_watching) + ": " + String.valueOf(user.getCountView()));
        return convertView;
    }
}
