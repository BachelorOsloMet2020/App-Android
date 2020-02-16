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


public class PermissionFileFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission_file, container, false);
    }

    private PermissionInterface.PermissionListener mListener;
    public PermissionFileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null)
            getView().findViewById(R.id.fragment_permission_storage_permit).setOnClickListener(v -> mListener.onRequestFilePermission());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
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
