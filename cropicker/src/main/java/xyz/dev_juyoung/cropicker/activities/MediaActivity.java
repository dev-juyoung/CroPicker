package xyz.dev_juyoung.cropicker.activities;

import android.os.Bundle;

import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.base.CroPickerActivity;

public class MediaActivity extends CroPickerActivity {

    private long bucketId;
    private String bucketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        getIntentData();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            bucketId = getIntent().getLongExtra(EXTRA_BUCKET_ID, 0L);
            bucketName = getIntent().getStringExtra(EXTRA_BUCKET_NAME);
            showMessage(bucketId + ", " + bucketName);
        } else {
            showMessage(getString(R.string.not_found_bucket_id));
            finish();
        }
    }
}
