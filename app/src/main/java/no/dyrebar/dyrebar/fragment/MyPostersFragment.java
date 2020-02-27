package no.dyrebar.dyrebar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;

public class MyPostersFragment extends Fragment
{
    FragmentInterface.FragmentListener mListener;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof FragmentInterface.FragmentListener)
        {
            mListener = (FragmentInterface.FragmentListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                                               + " must implement PermissionInterfaceListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_posters, container, false);
    }


}