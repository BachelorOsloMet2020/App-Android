package no.dyrebar.dyrebar.interfaces;


import androidx.appcompat.widget.Toolbar;

import no.dyrebar.dyrebar.classes.Profile;

public class FragmentInterface
{
    private FragmentListener mListener;
    public FragmentInterface(FragmentListener fl)
    {
        this.mListener = fl;
    }

    public interface FragmentListener
    {
        void onSetToolbar(Toolbar toolbar);
        Profile getProfile();
    }
}
