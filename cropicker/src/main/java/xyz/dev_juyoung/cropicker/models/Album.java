package xyz.dev_juyoung.cropicker.models;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class Album {
    private long id;
    private String displayName;
    private String previewImagePath;
    private int resourceCount;

    public Album(long id, String displayName, String previewImagePath, int resourceCount) {
        this.id = id;
        this.displayName = displayName;
        this.previewImagePath = previewImagePath;
        this.resourceCount = resourceCount;
    }

    public long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public int getResourceCount() {
        return resourceCount;
    }
}
