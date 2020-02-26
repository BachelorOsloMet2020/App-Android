package no.dyrebar.dyrebar.handler;

import android.Manifest;
import android.app.Activity;
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
    public static final int PEMC_PHONE = 478;

    public enum Permissions
    {
        Camera,
        Storage,
        Gps,
        Phone
    }

    private final String[] PEMS_Storage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final String[] PEMS_Camera = {Manifest.permission.CAMERA};
    private final String[] PEMS_GPS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final String[] PEMS_Phone = {Manifest.permission.CALL_PHONE};

    private Activity activity;

    public PermissionHandler(Activity activity)
    {
        this.activity = activity;
    }

    public PermissionHandler(Activity activity, PermissionInterface.PermissionListener mListener)
    {
        this.mListener = mListener;
        this.activity = activity;
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

    public boolean isPhonePermitted()
    {
        boolean permitted = true;
        for (String pem : PEMS_Phone)
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
        return activity.getApplicationContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
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

    public void getPhonePermission()
    {
        ActivityCompat.requestPermissions(activity, PEMS_Phone, PEMC_PHONE);
    }

    /**
     * It is highly recommended to implement "PermissionInterface.PermissionListener" in the activity
     *
     */
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

            case PEMC_PHONE:
            {
                mListener.onPhonePermitted(granted);
                break;
            }

            default:
                Log.e(getClass().getName(), "Request code:" + String.valueOf(requestCode) + "; is not recognized");
        }
    }

}
