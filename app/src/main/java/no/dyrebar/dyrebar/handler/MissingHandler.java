package no.dyrebar.dyrebar.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.interfaces.PermissionInterface;

public class MissingHandler implements LocationListener, PermissionInterface.PermissionListener
{
    private LocationManager locationManager;
    private PermissionHandler permissionHandler;
    private MissingListener mListener;
    private Activity activity;
    public MissingHandler(Activity activity, MissingListener mListener)
    {
        this.mListener = mListener;
        this.activity = activity;
        this.permissionHandler = new PermissionHandler(activity, this);
    }

    private ProfileAnimal animal;
    private String mdesc;
    public void RequestMissing(ProfileAnimal animal, String mdesc)
    {
        this.animal = animal;
        this.mdesc = mdesc;
        if (permissionHandler.isGpsPermitted())
            requestLocation();
        else
            permissionHandler.getGpsPermission();
    }

    private void CreateMissing(Location location)
    {
        long time = Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis();
        String area = (location != null) ? getArea(location) : "";

        Missing m = new Missing(
                animal.get_ID(),
                Integer.valueOf(App.profile.getId()),
                (location != null) ? location.getLatitude() : 0,
                (location != null) ? location.getLongitude() : 0,
                time,
                area,
                mdesc
        );
        mListener.onMissingRequestCreated(m);
    }

    public String getArea(Location location)
    {
        List<Address> addressList = null;
        try
        {
            addressList = new Geocoder(activity.getApplicationContext(), Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList.get(0).getThoroughfare() != null)
                return addressList.get(0).getThoroughfare();
            else if (addressList.get(0).getLocality() != null)
                return addressList.get(0).getLocality();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @SuppressLint("MissingPermission")
    private void requestLocation()
    {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, this);

        new Handler().postDelayed(() -> {
            // Checking whether position has been obtained or not within given time span, not recommended to give 5k
            if (!positionObtained)
            {
                locationManager.removeUpdates(this);
                CreateMissing(null);
            }
        }, 1000);
    }

    public void onPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        permissionHandler.onPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean positionObtained = false;
    @Override
    public void onLocationChanged(Location location)
    {
        positionObtained = true;
        locationManager.removeUpdates(this);
        CreateMissing(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    @Override
    public void onRequestFilePermission()
    {

    }

    @Override
    public void onRequestGpsPermission()
    {

    }

    @Override
    public void onRequestCameraPermission()
    {

    }

    @Override
    public void onRequestPhonePermission()
    {

    }

    @Override
    public void onStoragePermitted(boolean permitted)
    {

    }

    @Override
    public void onCameraPermitted(boolean permitted)
    {

    }

    @Override
    public void onGpsPermitted(boolean permitted)
    {
        if (permitted)
            requestLocation();
    }

    @Override
    public void onPhonePermitted(boolean permitted)
    {

    }


    public interface MissingListener
    {
        void onMissingRequestCreated(Missing missing);
        void onMissingRequestCreatedNoLocation(Missing missing);
    }
}
