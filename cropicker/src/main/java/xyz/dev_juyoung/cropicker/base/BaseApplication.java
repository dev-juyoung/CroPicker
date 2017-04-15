package xyz.dev_juyoung.cropicker.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;

import xyz.dev_juyoung.cropicker.R;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    private AppCompatDialog progressDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        this.baseApplication = this;
    }

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    public void progressShow(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.cropicker_progress);
            progressDialog.show();
        }
    }

    public void progressDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
