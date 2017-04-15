package xyz.dev_juyoung.cropicker.activities;

import android.os.Bundle;

import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.base.CroPickerActivity;

public class DirectoryActivity extends CroPickerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
    }
}
