package com.troya.menuplanner.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHelper {

    public static Bitmap getImageBitmap(byte[] image) {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } else {
            return null;
        }
    }
}
