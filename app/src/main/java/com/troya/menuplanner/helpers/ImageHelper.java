package com.troya.menuplanner.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ImageHelper {

    //SDF to generate a unique name for the compressed file.
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyymmddhhmmss", Locale.getDefault());
    private static final String COMPRESSED_IMG_DIR = "/ImageCompressor";
    private static final String IMG_FORMAT = ".jpg";

    /*
     compress the file/photo from @param <b>path</b> to a private location on the current device and return the compressed file.
     @param path = The original image path
     @param context = Current android Context
     */
    public static byte[] getCompressedImageFile(@NonNull Context context, Uri imgUri) throws IOException {

        //getting device external cache directory, might not be available on some devices,
        // so our code fall back to internal storage cache directory, which is always available but in smaller quantity
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            //fall back
            cacheDir = context.getCacheDir();
        }

        String rootDir = cacheDir.getAbsolutePath() + COMPRESSED_IMG_DIR;
        File root = new File(rootDir);

        //Create ImageCompressor folder if it doesn't already exists.
        if (!root.exists())
            root.mkdirs();


        InputStream inputStream = context.getContentResolver().openInputStream(imgUri);


        //decode and resize the original bitmap from @param path.
        Bitmap bitmap = decodeImageFromFiles(inputStream, 500, 300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        return outputStream.toByteArray();

       /* File compressedImage = new File(root, SDF.format(new Date()) + IMG_FORMAT);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

        FileOutputStream fileOutputStream = new FileOutputStream(compressedImage);
        fileOutputStream.write(byteArrayOutputStream.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();

        return compressedImage;*/
    }

    private static Bitmap decodeImageFromFiles(InputStream stream, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        //bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

        BitmapFactory.Options scaleOptions = new BitmapFactory.Options();
        scaleOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, scaleOptions);
        //BitmapFactory.decodeStream(path, scaleOptions);
        int scale = 1;
        while (scaleOptions.outWidth / scale / 2 >= width
                && scaleOptions.outHeight / scale / 2 >= height) {
            scale *= 2;
        }

        // decode with the sample size
        BitmapFactory.Options outOptions = new BitmapFactory.Options();
        outOptions.inSampleSize = scale;
        return BitmapFactory.decodeStream(stream, null, outOptions);
    }

    public static Bitmap getImageBitmap(byte[] image) {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } else {
            return null;
        }
    }
}
