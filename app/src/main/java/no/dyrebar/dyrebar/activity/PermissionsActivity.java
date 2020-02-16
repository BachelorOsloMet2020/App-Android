package no.dyrebar.dyrebar.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.fragment.PermissionCameraFragment;
import no.dyrebar.dyrebar.fragment.PermissionFileFragment;
import no.dyrebar.dyrebar.fragment.PermissionGpsFragment;
import no.dyrebar.dyrebar.handler.PermissionHandler;
import no.dyrebar.dyrebar.interfaces.PermissionInterface;

public class PermissionsActivity extends AppCompatActivity implements PermissionInterface.PermissionListener
{

    private ArrayList<Pair<PermissionHandler.Permissions, Boolean>> permFrags;


    private PermissionHandler permissionHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        permissionHandler = new PermissionHandler(this);
        loadPems();
    }

    private void loadPems()
    {
        permFrags = new ArrayList<Pair<PermissionHandler.Permissions, Boolean>>(){{
            add(new Pair<>(PermissionHandler.Permissions.Camera, permissionHandler.isCameraPermitted()));
            add(new Pair<>(PermissionHandler.Permissions.Storage, permissionHandler.isStoragePermitted()));
            add(new Pair<>(PermissionHandler.Permissions.Gps, permissionHandler.isGpsPermitted()));
        }};
        loadFragments();
    }

    private Fragment prevFragment = null;
    private void loadFragments()
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        boolean hasAll = true;
        Fragment fragment = null;
        for (Pair<PermissionHandler.Permissions, Boolean> ppp : permFrags)
        {
            if (ppp.second == false)
            {
                hasAll = false;
                if (ppp.first == PermissionHandler.Permissions.Camera)
                    fragment = new PermissionCameraFragment();
                else if (ppp.first == PermissionHandler.Permissions.Storage)
                    fragment = new PermissionFileFragment();
                else if (ppp.first == PermissionHandler.Permissions.Gps)
                    fragment = new PermissionGpsFragment();
                break;
            }
        }


        if (fragment != null && !hasAll)
        {
            if (prevFragment == fragment)
                return;
            if (prevFragment == null && fragmentTransaction.isEmpty() && !hasFragments())
            {
                fragmentTransaction.add(R.id.container_fragments, fragment);
            }
            else
            {
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.container_fragments, fragment);
            }
            fragmentTransaction.commitNow();

            prevFragment = fragment;
        }
        else
        {
            /** All permissions is present */
            setResult(RESULT_OK);
            finish();
        }

    }

    public boolean hasFragments()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fragments = fragmentManager.getFragments();
        }
        else
            return true;
        if (fragmentManager.getBackStackEntryCount() > 0 || fragments.size() > 0)
            return true;
        else
            return false;
    }








    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHandler.onPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onRequestFilePermission()
    {
        permissionHandler.getStoragePermission();
    }

    @Override
    public void onRequestGpsPermission()
    {
        permissionHandler.getGpsPermission();
    }

    @Override
    public void onRequestCameraPermission()
    {
        permissionHandler.getCameraPermission();
    }

    @Override
    public void onStoragePermitted(boolean permitted)
    {
        loadPems();
    }

    @Override
    public void onCameraPermitted(boolean permitted)
    {
        loadPems();
    }

    @Override
    public void onGpsPermitted(boolean permitted)
    {
        loadPems();
    }
}
