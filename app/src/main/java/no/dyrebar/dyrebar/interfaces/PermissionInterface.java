package no.dyrebar.dyrebar.interfaces;

import androidx.annotation.NonNull;

public class PermissionInterface
{
    private PermissionInterface mListener;
    public PermissionInterface(PermissionInterface mListener)
    {
        this.mListener = mListener;
    }

    public interface PermissionListener
    {
        void onRequestFilePermission();
        void onRequestGpsPermission();
        void onRequestCameraPermission();

        void onStoragePermitted(boolean permitted);
        void onCameraPermitted(boolean permitted);
        void onGpsPermitted(boolean permitted);
    }
}
