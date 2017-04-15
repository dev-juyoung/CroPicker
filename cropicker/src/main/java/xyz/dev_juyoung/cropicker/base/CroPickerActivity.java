package xyz.dev_juyoung.cropicker.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.R;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class CroPickerActivity extends AppCompatActivity {
    /*
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //ButterKnife.bind(this);

        checkInitializeConfigs(getIntent());
        setupToolbar();
    }

    private void checkInitializeConfigs(@Nullable Intent intent) {
        if (intent != null) {
            boolean isSetupConfigs = intent.getBooleanExtra(CroPicker.EXTRA_INIT_CONFIGS, false);
            if (isSetupConfigs) showMessage("Setting Configs");
        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
    }

    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    public void showMessage(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void progressShow() {
        CroPickerApplication.getInstance().progressShow(this);
    }

    public void progressDismiss() {
        CroPickerApplication.getInstance().progressDismiss();
    }
}
