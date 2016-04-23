package com.example.guru_i.Downloader;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by guru_i on 2016-04-23.
 */
public class ImageCache extends LruCache<String, Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public ImageCache(int maxSize) {
        super(maxSize);
        Log.d("ImageCache:", String.valueOf(maxSize));
    }

    @Override
    protected int sizeOf(String key, Bitmap val){
        return val.getByteCount();
    }

    @Override
    protected void entryRemoved( boolean evicted, String key, Bitmap oldValue, Bitmap newValue ) {
        oldValue.recycle();
    }


}
