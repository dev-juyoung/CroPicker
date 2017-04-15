package xyz.dev_juyoung.cropicker.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class Media implements Parcelable {
    private long id;
    private String imagePath;
    private String displayName;
    private String mimeType;
    private int orientation;

    public Media(long id, String imagePath, String displayName, String mimeType, int orientation) {
        this.id = id;
        this.imagePath = imagePath;
        this.displayName = displayName;
        this.mimeType = mimeType;
        this.orientation = orientation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.imagePath);
        dest.writeString(this.displayName);
        dest.writeString(this.mimeType);
        dest.writeInt(this.orientation);
    }

    public Media(Parcel src) {
        this.id = src.readLong();
        this.imagePath = src.readString();
        this.displayName = src.readString();
        this.mimeType = src.readString();
        this.orientation = src.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Media[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getOrientation() {
        return orientation;
    }
}
