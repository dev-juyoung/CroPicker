package xyz.dev_juyoung.cropicker.activities;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import xyz.dev_juyoung.cropicker.Configs;
import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.adapters.DirectoryAdapter;
import xyz.dev_juyoung.cropicker.base.CroPickerActivity;
import xyz.dev_juyoung.cropicker.models.Album;
import xyz.dev_juyoung.cropicker.utils.GridSpacingItemDecoration;
import xyz.dev_juyoung.cropicker.utils.MediaStoreProvider;

public class DirectoryActivity extends CroPickerActivity {

    // @BindView(R2.id.album_list)
    // RecyclerView albumList;

    private RecyclerView albumList;

    private ArrayList<Album> dispAlbum;
    private GridLayoutManager layoutManager;
    private DirectoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        progressShow();
        setupToolbar();
        setupRecyclerView();
        getAlbumData();
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();

        String titleText = Configs.toolbarTitle != null ? Configs.toolbarTitle : getString(R.string.toolbar_title_album);
        getSupportActionBar().setTitle(titleText);
    }

    private void setupRecyclerView() {
        layoutManager = new GridLayoutManager(this, Configs.gridSpanCountOfAlbum);
        adapter = new DirectoryAdapter(this);
        albumList.setHasFixedSize(true);
        albumList.setLayoutManager(layoutManager);
        albumList.addItemDecoration(new GridSpacingItemDecoration(layoutManager.getSpanCount(), Configs.gridSpacing, true));
        albumList.setAdapter(adapter);
    }

    private void getAlbumData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                dispAlbum = MediaStoreProvider.getAlbums(DirectoryActivity.this);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateItems(dispAlbum);
                        // progressDismiss();
                    }
                });
            }
        });

        thread.start();
    }
}
