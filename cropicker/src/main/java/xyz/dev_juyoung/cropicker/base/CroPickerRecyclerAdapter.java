package xyz.dev_juyoung.cropicker.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public abstract class CroPickerRecyclerAdapter<T, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    protected ArrayList<T> items;

    public CroPickerRecyclerAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void addItems(ArrayList<T> items) {
        if (this.items == null) {
            this.items = items;
        } else {
            this.items.addAll(items);
        }

        notifyDataSetChanged();
    }

    public void updateItems(ArrayList<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (items == null) {
            return null;
        }

        return items.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindView((H) holder, position);
    }

    abstract public void onBindView(H holder, int position);

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }

        return items.size();
    }
}
