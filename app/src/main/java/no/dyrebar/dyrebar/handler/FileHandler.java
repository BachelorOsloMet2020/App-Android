package no.dyrebar.dyrebar.handler;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import java.io.File;

import no.dyrebar.dyrebar.BuildConfig;
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

    /**
     * Retrieves visible Storage Directory
     * @implNote Ensure that you check for storage permission before!
     * !Crates Storage Directory if not exists
     * @return Folder
     */
    public File getExternalImagesFolder()
    {
        File file = new File(Environment.getExternalStorageDirectory(), S.FOLDER_DYREBAR);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    public File getImageFile(File Folder, String FileName)
    {
        File image = new File(Folder, FileName);
        return image;
    }
    public Uri getUriFromFile(Context context, File file)
    {
        return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file);
    }


}
