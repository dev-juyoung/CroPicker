package xyz.dev_juyoung.cropicker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import xyz.dev_juyoung.cropicker.Configs;
import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.R;
import xyz.dev_juyoung.cropicker.R2;
import xyz.dev_juyoung.cropicker.adapters.DirectoryAdapter;
import xyz.dev_juyoung.cropicker.base.CroPickerActivity;
import xyz.dev_juyoung.cropicker.models.Album;
import xyz.dev_juyoung.cropicker.utils.GridSpacingItemDecoration;
import xyz.dev_juyoung.cropicker.utils.MediaStoreProvider;
import xyz.dev_juyoung.cropicker.utils.RecyclerViewItemClickListener;

public class DirectoryActivity extends CroPickerActivity {

    @BindView(R2.id.album_list)
    RecyclerView albumList;

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
        setupRecyclerViewEvent();
        getAlbumData();
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();

        String titleText = Configs.toolbarTitle != null ? Configs.toolbarTitle : getString(R.string.toolbar_title_album);
        getSupportActionBar().setTitle(titleText);
    }

    private void setupRecyclerView() {
        Log.d("GRID SPAN COUNT", "Configs.gridSpanCountOfAlbum: " + Configs.gridSpanCountOfAlbum);

        layoutManager = new GridLayoutManager(this, Configs.gridSpanCountOfAlbum);
        adapter = new DirectoryAdapter(this);
        albumList.setHasFixedSize(true);
        albumList.setLayoutManager(layoutManager);
        albumList.addItemDecoration(new GridSpacingItemDecoration(layoutManager.getSpanCount(), Configs.gridSpacing, true));
        albumList.setAdapter(adapter);
    }

    private void setupRecyclerViewEvent() {
        albumList.addOnItemTouchListener(new RecyclerViewItemClickListener(this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Album item = dispAlbum.get(position);
                long id = item.getId();
                String dispName = item.getDisplayName();

                Intent intent = new Intent(DirectoryActivity.this, MediaActivity.class);
                intent.putExtra(EXTRA_BUCKET_ID, id);
                intent.putExtra(EXTRA_BUCKET_NAME, dispName);
                startActivityForResult(intent, CroPicker.REQUEST_MEDIA);
            }
        }));
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
                        progressDismiss();
                    }
                });
            }
        });

        thread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CroPicker.REQUEST_MEDIA && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
