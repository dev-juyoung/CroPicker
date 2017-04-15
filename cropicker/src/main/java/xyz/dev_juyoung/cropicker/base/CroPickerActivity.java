package xyz.dev_juyoung.cropicker.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.dev_juyoung.cropicker.R2;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class CroPickerActivity extends AppCompatActivity {
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        setupToolbar();
    }

    public void setupToolbar() {
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