package xyz.dev_juyoung.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import xyz.dev_juyoung.cropicker.CroPicker;
import xyz.dev_juyoung.cropicker.models.Media;

public class MainActivity extends AppCompatActivity {
    private Context context;

    private RecyclerView imageList;
    private LinearLayoutManager layoutManager;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        imageList = (RecyclerView) findViewById(R.id.image_list);

        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        layoutManager = new LinearLayoutManager(context);
        adapter = new MainAdapter(context);
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                new TedPermission(context)
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                CroPicker.Options options = new CroPicker.Options();
                                options.setStatusBarColor(ContextCompat.getColor(context, R.color.colorAccent));
                                options.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
                                options.setIndexViewIconDrawable(R.drawable.ic_favorite_white_48dp);
                                options.setLimitedCount(5);
                                options.setLimitedExceedMessage("Not selectable");
                                options.setNotSelectedMessage("Did you images choose?");
                                options.setMessageViewType(CroPicker.MESSAGE_VIEW_TYPE_SNACKBAR);

                                CroPicker
                                        .init(MainActivity.this)
                                        .withOptions(options)
                                        .start();
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> deniedPermissions) {}
                        })
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        });

        imageList.setHasFixedSize(true);
        imageList.setLayoutManager(layoutManager);
        imageList.setAdapter(adapter);
    }

    private void updateAdapterData(ArrayList<Media> items) {
        if (items.size() > 0) {
            layoutManager = new GridLayoutManager(context, 2);
            imageList.setLayoutManager(layoutManager);
            adapter.updateItems(items);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CroPicker.REQUEST_ALBUM && resultCode == RESULT_OK) {
            ArrayList<Media> results = data.getParcelableArrayListExtra(CroPicker.EXTRA_RESULT_IMAGES);
            updateAdapterData(results);
            /*
            ArrayList<Media> results = data.getParcelableArrayListExtra(CroPicker.EXTRA_RESULT_IMAGES);
            for (Media item : results) {
                Log.d("CROPICKER RESULTS", item.getImagePath());
            }
            */
        }
    }
}
