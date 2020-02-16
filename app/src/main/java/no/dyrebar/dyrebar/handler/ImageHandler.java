package no.dyrebar.dyrebar.handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageHandler
{
    public ImageHandler() {}

    public String getImageAsString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encImg;
    }

    public Bitmap getBitmap(Context context, Uri uri)
    {
        Bitmap bitmap = null;
        try
        {
            InputStream is = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException | NullPointerException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
}
