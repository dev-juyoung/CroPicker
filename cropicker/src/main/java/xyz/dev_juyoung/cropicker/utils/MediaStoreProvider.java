package xyz.dev_juyoung.cropicker.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import xyz.dev_juyoung.cropicker.models.Album;
import xyz.dev_juyoung.cropicker.models.Media;

/**
 * Created by juyounglee on 2017. 4. 15..
 */

public class MediaStoreProvider {
    public static ArrayList<Album> getAlbums(Context context) {
        ArrayList<Album> albums = new ArrayList<>();
        Set<Long> bucketIds = new HashSet<>();

        String[] projection = new String[] {
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexOfBucketId = cursor.getColumnIndexOrThrow(projection[0]);
                int columnIndexOfBucketDispName = cursor.getColumnIndexOrThrow(projection[1]);

                do {
                    long bucketId = cursor.getLong(columnIndexOfBucketId);
                    String bucketDispName = cursor.getString(columnIndexOfBucketDispName);

                    if (!bucketIds.contains(bucketId)) {
                        bucketIds.add(bucketId);

                        Album album = new Album(bucketId, bucketDispName, lastImageOfAlbum(context, String.valueOf(bucketId)), countImagesOfAlbum(context, String.valueOf(bucketId)));
                        albums.add(album);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return albums;
    }

    public static ArrayList<Media> getImagesOfAlbum(Context context, String bucketId) {
        ArrayList<Media> medias = new ArrayList<>();

        String[] projection = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.ORIENTATION
        };

        String selection = MediaStore.Images.Media.BUCKET_ID + "= ?";
        String[] args = new String[] {
                bucketId
        };

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, args, MediaStore.Images.Media.DATE_ADDED + " desc");

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndexOfId = cursor.getColumnIndexOrThrow(projection[0]);
                int columnIndexOfData = cursor.getColumnIndexOrThrow(projection[1]);
                int columnIndexOfDispName = cursor.getColumnIndexOrThrow(projection[2]);
                int columnIndexOfMimeType = cursor.getColumnIndexOrThrow(projection[3]);
                int columnIndexOfOrientation = cursor.getColumnIndexOrThrow(projection[4]);

                do {
                    long originId = cursor.getLong(columnIndexOfId);
                    String imagePath = cursor.getString(columnIndexOfData);
                    String displayName = cursor.getString(columnIndexOfDispName);
                    String mimeType = cursor.getString(columnIndexOfMimeType);
                    int orientation = cursor.getInt(columnIndexOfOrientation);

                    Media media = new Media(originId, imagePath, displayName, mimeType, orientation);
                    medias.add(media);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return medias;
    }

    private static String lastImageOfAlbum(Context context, String bucketId) {
        String path = null;

        String[] projection = new String[] {
                MediaStore.Images.Media.DATA
        };

        String selection = MediaStore.Images.Media.BUCKET_ID + "= ?";

        String[] args = new String[] {
                bucketId
        };

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, args, MediaStore.Images.Media.DATE_ADDED + " desc");

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return path;
    }

    private static int countImagesOfAlbum(Context context, String bucketId) {
        int count = 0;

        String[] projection = new String[] {
                MediaStore.Images.Media.DATA
        };

        String selection = MediaStore.Images.Media.BUCKET_ID + "= ?";

        String[] args = new String[] {
                bucketId
        };

        Cursor cursor = null;

        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, args, MediaStore.Images.Media.DATE_ADDED + " desc");

            if (cursor != null) {
                count = cursor.getCount();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    private static String getThumbnailPath(Context context, long originId) {
        String path = null;

        ContentResolver resolver = context.getContentResolver();
        int kind = MediaStore.Images.Thumbnails.MINI_KIND;
        String[] projection = new String[] {
                MediaStore.Images.Thumbnails.DATA
        };

        Cursor cursor = null;

        try {
            cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(resolver, originId, kind, projection);

            if (cursor != null && cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(projection[0]));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return path;
    }
}
