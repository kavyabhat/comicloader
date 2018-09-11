package com.kvb.xkcd_comicaday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Callback;

/*
 * Copyright (c) 2015 Samsung Electronics. All Rights Reserved
 *
 * PROPRIETARY/CONFIDENTIAL
 *
 * This software is the confidential and proprietary information of
 * SAMSUNG ELECTRONICS ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with SAMSUNG ELECTRONICS. SAMSUNG make no
 * representations or warranties about the suitability
 * of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. SAMSUNG shall not be liable
 * for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 */
public class ComicController {
    private static ComicController mComicController;
    public static ComicController getInstance(){
        if(mComicController == null){
            return new ComicController();
        }
        return mComicController;
    }

    public void fetchComics(String url, Callback callback){
        new ComicRetrofit().fetchComic(url, callback);
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(Context context, ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

//            bmpUri = getUriFromFile(context, file);
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
