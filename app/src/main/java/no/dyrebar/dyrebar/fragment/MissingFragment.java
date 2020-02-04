package no.dyrebar.dyrebar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;

public class MissingFragment extends Fragment
{
    FragmentInterface.FragmentListener fifl;

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
        return inflater.inflate(R.layout.fragment_missing, container, false);
    }

    public void setOnMainActivityListener(FragmentInterface.FragmentListener fifl)
    {
        this.fifl = fifl;
    }
}
