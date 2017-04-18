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
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.dev_juyoung.cropicker.Configs;
import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.R2;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class CroPickerActivity extends AppCompatActivity {
    public static final String EXTRA_BUCKET_ID = "bucket_id";
    public static final String EXTRA_BUCKET_NAME = "bucket_name";

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
        Configs.toolbarDoneDrawable = intent.getIntExtra(CroPicker.Options.EXTRA_TOOLBAR_DONE_DRAWABLE, R.drawable.ic_done_white_24dp);
        Configs.gridSpanCountOfAlbum = intent.getIntExtra(CroPicker.Options.EXTRA_GRID_SPAN_COUNT_OF_ALBUM, 2);
        Configs.gridSpanCountOfMedia = intent.getIntExtra(CroPicker.Options.EXTRA_GRID_SPAN_COUNT_OF_MEDIA, 3);
        Configs.gridSpacing = intent.getIntExtra(CroPicker.Options.EXTRA_GRID_SPACING, R.dimen.md_grid_spacing);
        Configs.gridSpacing = getResources().getDimensionPixelSize(Configs.gridSpacing);
        Configs.overlayViewBackgroundColor = intent.getIntExtra(CroPicker.Options.EXTRA_OVERLAY_VIEW_BACKGROUND_COLOR, ContextCompat.getColor(this, R.color.colorOverlay));
        Configs.overlayViewStrokeColor = intent.getIntExtra(CroPicker.Options.EXTRA_OVERLAY_VIEW_STROKE_COLOR, ContextCompat.getColor(this, R.color.colorPrimary));
        Configs.overlayViewStrokeWidth = intent.getIntExtra(CroPicker.Options.EXTRA_OVERLAY_VIEW_STROKE_WIDTH, R.dimen.md_linesize);
        Configs.overlayViewStrokeWidth = getResources().getDimensionPixelSize(Configs.overlayViewStrokeWidth);
        Configs.indexViewType = intent.getIntExtra(CroPicker.Options.EXTRA_INDEX_VIEW_TYPE, CroPicker.INDEX_VIEW_TYPE_TEXT);
        Configs.indexViewTextSize = intent.getIntExtra(CroPicker.Options.EXTRA_INDEX_VIEW_TEXT_SIZE, 24);
        Configs.indexViewTextColor = intent.getIntExtra(CroPicker.Options.EXTRA_INDEX_VIEW_TEXT_COLOR, ContextCompat.getColor(this, R.color.colorWhite));
        Configs.indexViewIconDrawable = intent.getIntExtra(CroPicker.Options.EXTRA_INDEX_VIEW_ICON_DRAWABLE, R.drawable.ic_check_circle_white_48dp);
        Configs.notSelectedMessage = intent.getStringExtra(CroPicker.Options.EXTRA_NOT_SELECTED_MESSAGE);
        Configs.notSelectedMessage = Configs.notSelectedMessage != null ? Configs.notSelectedMessage : getString(R.string.not_selected_images);
        Configs.limitedCount = intent.getIntExtra(CroPicker.Options.EXTRA_LIMITED_COUNT, Integer.MAX_VALUE);
        Configs.limitedExeedMessage = intent.getStringExtra(CroPicker.Options.EXTRA_LIMITED_EXCEED_MESSAGE);
        Configs.limitedExeedMessage = Configs.limitedExeedMessage != null ? Configs.limitedExeedMessage : getString(R.string.limited_exeed_message, Configs.limitedCount);
        Configs.messageViewType = intent.getIntExtra(CroPicker.Options.EXTRA_MESSAGE_VIEW_TYPE, CroPicker.MESSAGE_VIEW_TYPE_TOAST);
    }

    public void setupToolbar() {
        setStatusBarColor(Configs.statusColor);
        toolbar.setBackgroundColor(Configs.toolbarColor);
        toolbar.setTitleTextColor(Configs.toolbarWidgetColor);

        final Drawable upArrow = ContextCompat.getDrawable(this, Configs.toolbarArrowDrawable);
        upArrow.setColorFilter(Configs.toolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(upArrow);

        setSupportActionBar(toolbar);
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
        showMessage(Configs.messageViewType, message);
    }

    private void showMessage(int viewType, String message) {
        if (viewType == CroPicker.MESSAGE_VIEW_TYPE_TOAST) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void progressShow() {
        CroPickerApplication.getInstance().progressShow(this);
    }

    public void progressDismiss() {
        CroPickerApplication.getInstance().progressDismiss();
    }
}
