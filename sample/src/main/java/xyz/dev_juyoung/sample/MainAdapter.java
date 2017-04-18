package xyz.dev_juyoung.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import xyz.dev_juyoung.cropicker.models.Media;

/**
 * Created by juyounglee on 2017. 4. 17..
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 3641;
    private static final int VIEW_TYPE_RESULTS = 2172;

    private Context context;
    private ArrayList<Media> items;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public MainAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateItems(ArrayList<Media> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ResultHolder extends ViewHolder {
        public ImageView resultImages;

        public ResultHolder(View itemView) {
            super(itemView);

            resultImages = (ImageView) itemView.findViewById(R.id.iv_result_images);
        }
    }

    public class EmptyHolder extends ViewHolder implements View.OnClickListener {
        public EmptyHolder(View itemView) {
            super(itemView);

            (itemView.findViewById(R.id.btn_add)).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.size() > 0) {
            return VIEW_TYPE_RESULTS;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_EMPTY) {
            view = inflater.inflate(R.layout.item_empty, parent, false);
            viewHolder = new EmptyHolder(view);
        } else if (viewType == VIEW_TYPE_RESULTS) {
            view = inflater.inflate(R.layout.item_results, parent, false);
            viewHolder = new ResultHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_RESULTS) {
            ResultHolder resultHolder = (ResultHolder) holder;

            Media currentItem = items.get(position);
            Glide.with(context)
                    .load(currentItem.getImagePath())
                    .centerCrop()
                    .into(resultHolder.resultImages);
        }
    }

    @Override
    public int getItemCount() {
        if (items.isEmpty()) {
            return 1;
        }

        return items.size();
    }
}
