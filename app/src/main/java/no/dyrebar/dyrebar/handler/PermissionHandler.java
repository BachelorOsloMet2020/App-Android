package no.dyrebar.dyrebar.handler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import no.dyrebar.dyrebar.interfaces.PermissionInterface;

public class PermissionHandler
{
    private PermissionInterface.PermissionListener mListener;

    public static final int PEMC_STORAGE = 343;
    public static final int PEMC_CAMERA = 584;
    public static final int PEMC_GPS = 864;

    public enum Permissions
    {
        Camera,
        Storage,
        Gps
    }

    private final String[] PEMS_Storage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final String[] PEMS_Camera = {Manifest.permission.CAMERA};
    private final String[] PEMS_GPS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private Activity activity;

    public PermissionHandler(Activity activity)
    {
        this.activity = activity;
        if (activity instanceof PermissionInterface.PermissionListener)
            this.mListener = (PermissionInterface.PermissionListener) activity;
        else
        {
            Log.e(getClass().getName(), "Listener not implemented!");
        }
    }

    public boolean isStoragePermitted()
    {
        boolean permitted = true;
        for (String pem : PEMS_Storage)
        {
            if (!isPermitted(pem))
            {
                permitted = false;
                break;
            }
        }
        return permitted;
    }

    public boolean isCameraPermitted()
    {
        boolean permitted = true;
        for (String pem : PEMS_Camera)
        {
            if (!isPermitted(pem))
            {
                permitted = false;
                break;
            }
        }
        return permitted;
    }

    public boolean isGpsPermitted()
    {
        boolean permitted = true;
        for (String pem : PEMS_GPS)
        {
            if (!isPermitted(pem))
            {
                permitted = false;
                break;
            }
        }
        return permitted;
    }

    private boolean isPermitted(String permission)
    {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasRequiredPermissions()
    {
        return (isCameraPermitted() && isStoragePermitted() && isGpsPermitted());
    }


    public void getStoragePermission()
    {
        ActivityCompat.requestPermissions(activity, PEMS_Storage, PEMC_STORAGE);
    }
    public void getCameraPermission()
    {
        ActivityCompat.requestPermissions(activity, PEMS_Camera, PEMC_CAMERA);
    }
    public void getGpsPermission()
    {
        ActivityCompat.requestPermissions(activity, PEMS_GPS, PEMC_GPS);
    }

    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        boolean granted = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        switch (requestCode)
        {
            case PEMC_CAMERA:
            {
                mListener.onCameraPermitted(granted);
                break;
            }
            case PEMC_GPS:
            {
                mListener.onGpsPermitted(granted);
                break;
            }

            case PEMC_STORAGE:
            {
                mListener.onStoragePermitted(granted);
                break;
            }

            default:
                Log.e(getClass().getName(), "Request code:" + String.valueOf(requestCode) + "; is not recognized");
        }
    }

}
