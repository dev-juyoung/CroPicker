package xyz.dev_juyoung.cropicker.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.dev_juyoung.cropicker.Configs;
import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.R2;
import xyz.dev_juyoung.cropicker.base.CroPickerRecyclerAdapter;
import xyz.dev_juyoung.cropicker.base.CroPickerViewHolder;
import xyz.dev_juyoung.cropicker.models.Media;

/**
 * Created by juyounglee on 2017. 4. 17..
 */

public class MediaAdapter extends CroPickerRecyclerAdapter<Media, MediaAdapter.ViewHolder> {
    private ArrayList<Media> selectedItem;

    public MediaAdapter(Context context) {
        super(context);
        this.selectedItem = new ArrayList<>();
    }

    public void updateSelectedItems(ArrayList<Media> items) {
        this.selectedItem = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends CroPickerViewHolder {
        @BindView(R2.id.iv_picture)
        ImageView ivPicture;
        @BindView(R2.id.overlay_view)
        RelativeLayout overlayView;
        @BindView(R2.id.index_view)
        TextView indexView;

        public ViewHolder(View itemView) {
            super(itemView);

            /**
             * Settings OverlayView Style
             */
            GradientDrawable overlayViewGradient = (GradientDrawable) overlayView.getBackground();
            overlayViewGradient.mutate();
            overlayViewGradient.setColor(Configs.overlayViewBackgroundColor);
            overlayViewGradient.setStroke(Configs.overlayViewStrokeWidth, Configs.overlayViewStrokeColor);

            /**
             * Settings IndexView Style
             */
            if (Configs.indexViewType == CroPicker.INDEX_VIEW_TYPE_TEXT) {
                indexView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Configs.indexViewTextSize);
                indexView.setTextColor(Configs.indexViewTextColor);
            } else {
                indexView.setBackgroundResource(Configs.indexViewIconDrawable);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        Media currentItem = getItem(position);

        Glide.with(getContext())
                .load(currentItem.getImagePath())
                .thumbnail(0.1f)
                .centerCrop()
                .into(holder.ivPicture);

        if (selectedItem.contains(currentItem)) {
            if (Configs.indexViewType == CroPicker.INDEX_VIEW_TYPE_TEXT) {
                int index = selectedItem.indexOf(currentItem);
                holder.indexView.setText(String.valueOf(index + 1));
            }

            holder.overlayView.setVisibility(View.VISIBLE);
        } else {
            holder.indexView.setText(null);
            holder.overlayView.setVisibility(View.GONE);
        }
    }
}
