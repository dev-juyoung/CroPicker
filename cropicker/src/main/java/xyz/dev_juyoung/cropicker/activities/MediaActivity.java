package xyz.dev_juyoung.cropicker.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.dev_juyoung.cropicker.Configs;
import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.R2;
import xyz.dev_juyoung.cropicker.adapters.MediaAdapter;
import xyz.dev_juyoung.cropicker.base.CroPickerActivity;
import xyz.dev_juyoung.cropicker.models.Media;
import xyz.dev_juyoung.cropicker.utils.GridSpacingItemDecoration;
import xyz.dev_juyoung.cropicker.utils.MediaStoreProvider;
import xyz.dev_juyoung.cropicker.utils.RecyclerViewItemClickListener;

public class MediaActivity extends CroPickerActivity {

    @BindView(R2.id.media_list)
    RecyclerView mediaList;

    private ArrayList<Media> dispMedia;
    private ArrayList<Media> selectedMedia;
    private GridLayoutManager layoutManager;
    private MediaAdapter adapter;

    private long bucketId;
    private String bucketName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        selectedMedia = new ArrayList<>();

        progressShow();
        getIntentData();
        setupToolbar();
        setupRecyclerView();
        setupRecyclerViewEvent();
        getMediaData();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            bucketId = getIntent().getLongExtra(EXTRA_BUCKET_ID, 0L);
            bucketName = getIntent().getStringExtra(EXTRA_BUCKET_NAME);
        } else {
            showMessage(getString(R.string.not_found_bucket_id));
            finish();
        }
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();

        String titleText = Configs.toolbarTitle != null ? Configs.toolbarTitle : bucketName;
        getSupportActionBar().setTitle(titleText);
    }

    private void setupRecyclerView() {
        layoutManager = new GridLayoutManager(this, Configs.gridSpanCountOfMedia);
        adapter = new MediaAdapter(this);
        mediaList.setHasFixedSize(true);
        mediaList.setLayoutManager(layoutManager);
        mediaList.addItemDecoration(new GridSpacingItemDecoration(layoutManager.getSpanCount(), Configs.gridSpacing, true));
        mediaList.setAdapter(adapter);
    }

    private void setupRecyclerViewEvent() {
        mediaList.addOnItemTouchListener(new RecyclerViewItemClickListener(this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Media item = dispMedia.get(position);
                boolean isSelected = selectedMedia.contains(item);
                int selectableCount = Configs.limitedCount - selectedMedia.size();

                if (!isSelected && selectableCount < 1) {
                    showMessage(Configs.limitedExeedMessage);
                    return;
                }

                if (isSelected) {
                    selectedMedia.remove(item);
                } else {
                    selectedMedia.add(item);
                }

                adapter.updateSelectedItems(selectedMedia);
            }
        }));
    }

    private void getMediaData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                dispMedia = MediaStoreProvider.getImagesOfAlbum(MediaActivity.this, String.valueOf(bucketId));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateItems(dispMedia);
                        progressDismiss();
                    }
                });
            }
        });

        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_done, menu);

        MenuItem actionDone = menu.findItem(R.id.action_done);

        final Drawable doneIcon = ContextCompat.getDrawable(this, Configs.toolbarDoneDrawable);
        if (doneIcon != null) {
            doneIcon.mutate();
            doneIcon.setColorFilter(Configs.toolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
            actionDone.setIcon(doneIcon);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            if (selectedMedia.size() > 0) {
                setResult(RESULT_OK, new Intent().putParcelableArrayListExtra(CroPicker.EXTRA_RESULT_IMAGES, selectedMedia));
                finish();
            } else {
                showMessage(Configs.notSelectedMessage);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
