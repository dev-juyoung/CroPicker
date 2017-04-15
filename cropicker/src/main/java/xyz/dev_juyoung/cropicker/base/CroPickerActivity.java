package xyz.dev_juyoung.cropicker.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import xyz.dev_juyoung.cropicker.Configs;
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
            if (isSetupConfigs) setupConfigs(intent);
        }
    }

    private void setupConfigs(@NonNull Intent intent) {
        Configs.statusColor = intent.getIntExtra(CroPicker.Options.EXTRA_STATUSBAR_COLOR, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        Configs.toolbarColor = intent.getIntExtra(CroPicker.Options.EXTRA_TOOLBAR_COLOR, ContextCompat.getColor(this, R.color.colorPrimary));
        Configs.toolbarWidgetColor = intent.getIntExtra(CroPicker.Options.EXTRA_TOOLBAR_WIDGET_COLOR, ContextCompat.getColor(this, R.color.colorWhite));
        Configs.toolbarTitle = intent.getStringExtra(CroPicker.Options.EXTRA_TOOLBAR_TITLE_TEXT);
        Configs.toolbarArrowDrawable = intent.getIntExtra(CroPicker.Options.EXTRA_TOOLBAR_BACK_ARROW_DRAWABLE, R.drawable.abc_ic_ab_back_material);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setStatusBarColor(Configs.statusColor);
        toolbar.setBackgroundColor(Configs.toolbarColor);
        toolbar.setTitleTextColor(Configs.toolbarWidgetColor);

        final Drawable upArrow = ContextCompat.getDrawable(this, Configs.toolbarArrowDrawable);
        upArrow.setColorFilter(Configs.toolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Configs.toolbarTitle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
