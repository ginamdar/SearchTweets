package com.example.guru_i.Downloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by guru_i on 2016-04-23.
 */
public class ImageDownloader extends AsyncTask<Object, Void, Bitmap> {
    private static final String TAG = "ImageDownloader";
    private final WeakReference<ImageView> imageViewWeakReference;
    private String imageKey;

    public ImageDownloader(ImageView imageView, String key){
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        imageKey = key;
    }
    @Override
    protected Bitmap doInBackground(Object[] params) {
        Bitmap image = null;
        try {
            String avatarUrl = params[0].toString();
            URL url = new URL(avatarUrl);
            URLConnection connection = url.openConnection();
//                connection.setDefaultUseCaches(true);
            image = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }finally {
            return image;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        if (imageViewWeakReference != null && bitmap != null){
            final ImageView imageView = imageViewWeakReference.get();
            imageView.setImageBitmap(bitmap);
        }
    }
}