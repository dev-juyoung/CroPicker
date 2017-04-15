package xyz.dev_juyoung.cropicker.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class CroPickerViewHolder extends RecyclerView.ViewHolder {
    public CroPickerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
