package no.dyrebar.dyrebar.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.interfaces.PermissionInterface;

public class PermissionCameraFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission_camera, container, false);
    }

    private PermissionInterface.PermissionListener mListener;
    public PermissionCameraFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
            getView().findViewById(R.id.fragment_permission_camera_permit).setOnClickListener(v -> mListener.onRequestCameraPermission());

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof PermissionInterface.PermissionListener)
        {
            mListener = (PermissionInterface.PermissionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                                               + " must implement PermissionInterfaceListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

}
