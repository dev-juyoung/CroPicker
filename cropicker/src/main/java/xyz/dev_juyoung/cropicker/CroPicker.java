package xyz.dev_juyoung.cropicker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import xyz.dev_juyoung.cropicker.activities.DirectoryActivity;

/**
 * Created by juyounglee on 2017. 4. 15..
 *
 * Builder class to ease Intent setup.
 */

public class CroPicker {
    public static final int REQUEST_ALBUM = 3641;

    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    public static final String EXTRA_INIT_CONFIGS = EXTRA_PREFIX + ".InitializeConfigs";
    public static final String EXTRA_RESULT_IMAGES = EXTRA_PREFIX + ".Results";

    private Activity requestedActivity;
    private Intent mIntent;
    private Bundle mOptionBundle;

    /**
     * This method creates a new CroPicker instance and creates an Intent builder.
     *
     * @param activity
     */
    public static CroPicker init(@NonNull Activity activity) {
        return new CroPicker(activity);
    }

    private CroPicker(@NonNull Activity activity) {
        requestedActivity = activity;
        mIntent = new Intent();
        mOptionBundle = new Bundle();
        mOptionBundle.putBoolean(EXTRA_INIT_CONFIGS, true);
    }

    public void start() {
        start(REQUEST_ALBUM);
    }

    public void start(int requestCode) {
        if (ContextCompat.checkSelfPermission(requestedActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing required permission(READ_EXTERNAL_STORAGE). Did you checking required permission?");
        }

        requestedActivity.startActivityForResult(getIntent(requestedActivity), requestCode);
    }

    private Intent getIntent(@NonNull Context context) {
        mIntent.setClass(context, DirectoryActivity.class);
        mIntent.putExtras(mOptionBundle);
        return mIntent;
    }
}
