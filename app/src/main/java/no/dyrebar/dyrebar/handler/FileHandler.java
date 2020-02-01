package no.dyrebar.dyrebar.handler;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;

import no.dyrebar.dyrebar.S;

public class FileHandler
{
    public FileHandler() {}



    public File getImage(Context context, File root, String name)
    {
        File file1 = new File(root, name);
        return file1;
    }

    public File getImagesFolder(Context context)
    {
        File root = new ContextWrapper(context.getApplicationContext()).getDir("images", Context.MODE_PRIVATE);
        return root;
    }

}
