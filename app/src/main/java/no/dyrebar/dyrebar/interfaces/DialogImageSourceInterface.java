package no.dyrebar.dyrebar.interfaces;

import android.content.Intent;
import android.net.Uri;

public class DialogImageSourceInterface
{
    private DialogImageSourceListener mListener;
    public DialogImageSourceInterface(DialogImageSourceListener listener) {this.mListener = listener; }

    public interface DialogImageSourceListener
    {
        void onCameraCapture();
        void onGalleryCapture(Uri uri);
        void onImageCrop(Uri uri);
    }
}
