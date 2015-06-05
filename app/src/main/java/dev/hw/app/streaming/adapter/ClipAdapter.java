package dev.hw.app.streaming.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Collections;
import java.util.List;

import dev.hw.app.streaming.R;
import dev.hw.app.streaming.app.AppController;
import dev.hw.app.streaming.model.ClipItemData;

/**
 * Created by huyuit on 6/2/2015.
 */
public class ClipAdapter extends RecyclerView.Adapter<ClipAdapter.ViewHolder> {

    private class VIEW_TYPES {
        public static final int Header = 1;
        public static final int Normal = 2;
        public static final int Footer = 3;
    }

    @Override
    public int getItemViewType(int position) {

        if (isPositionHeader(position))
            return VIEW_TYPES.Header;
        else if (isPositionFooter(position))
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    private final Context context;
    private final LayoutInflater inflater;
    List<ClipItemData> data = Collections.emptyList();
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView imgClip;
        TextView tvNameClip;
        TextView tvCateClip;
        TextView tvCountViewClip;
        NetworkImageView imageClip;

        public ViewHolder(View itemView) {
            super(itemView);
            imgClip = (NetworkImageView) itemView.findViewById(R.id.img_clip);
            tvNameClip = (TextView) itemView.findViewById(R.id.txt_name_clip);
            tvCateClip = (TextView) itemView.findViewById(R.id.txt_cate_clip);
            tvCountViewClip = (TextView) itemView.findViewById(R.id.txt_count_view_clip);
            imageClip = (NetworkImageView) itemView.findViewById(R.id.img_clip);
            imageClip.setErrorImageResId(R.drawable.logo_thegioinguoidep);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClipAdapter(Context context, List<ClipItemData> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void remove(int i) {
        data.remove(i);
        notifyItemRemoved(i);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ClipAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int i) {
//        // create a new view
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_row_clip, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(view);
//        return vh;

        View rowView;

        switch (i) {
            case VIEW_TYPES.Normal:
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_clip, parent, false);
                break;
            case VIEW_TYPES.Header:
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_clip_row, parent, false);
                break;
            case VIEW_TYPES.Footer:
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_clip_row, parent, false);
                break;
            default:
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_clip, parent, false);
                break;
        }
        return new ViewHolder(rowView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ClipItemData current = data.get(position);

//        if(holder.getItemViewType() == VIEW_TYPES.Header){

            holder.tvNameClip.setText(current.getName());
            holder.tvCateClip.setText(current.getCate());
            holder.tvCountViewClip.setText(current.getCountView() + " " + context.getResources().getString(R.string.count_view));

            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();
            holder.imageClip.setImageUrl(current.getImage(), imageLoader);
//        }else if(holder.getItemViewType() == VIEW_TYPES.Footer){
//
//        }else{
//
//            holder.tvNameClip.setText(current.getName());
//            holder.tvCateClip.setText(current.getCate());
//            holder.tvCountViewClip.setText(current.getCountView() + " " + context.getResources().getString(R.string.count_view));
//
//            if (imageLoader == null)
//                imageLoader = AppController.getInstance().getImageLoader();
//            holder.imageClip.setImageUrl(current.getImage(), imageLoader);
//        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}
