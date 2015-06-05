package dev.hw.app.streaming.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.model.Message;

/**
 * Created by huyuit on 5/16/2015.
 */
public class MessageListAdapter extends BaseAdapter {
    private Context context;
    private List<Message> messageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Message message = messageList.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // message layout
        convertView = mInflater.inflate(R.layout.item_message,
                null);

        TextView txtName = (TextView) convertView.findViewById(R.id.txt_name);
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txt_message);

        if (message.getName() != null && message.getName() != "" && !message.getName().isEmpty()) {
            txtName.setText(message.getName() + ": ");
        }
        txtMessage.setText(message.getMessage());
        if (message.getColor() != null) {

            txtName.setTextColor(Color.parseColor("#" + message.getColor()));
        }
        return convertView;
    }

}
