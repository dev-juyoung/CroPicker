package xyz.dev_juyoung.cropicker.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class CroSquareRelativeLayout extends RelativeLayout {
    public CroSquareRelativeLayout(Context context) {
        super(context);
    }

    public CroSquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
