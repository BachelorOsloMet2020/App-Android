package no.dyrebar.dyrebar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;

public class HomeFragment extends Fragment
{
    FragmentInterface.FragmentListener fifl;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) findView(R.id.toolbar);
        if (fifl != null && toolbar != null)
            fifl.onSetToolbar(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void setOnMainActivityListener(FragmentInterface.FragmentListener fifl)
    {
        this.fifl = fifl;
    }



    /**
     * Checks if activity is null. If not view is cast.
     * Prevents NPE
     * @param id int value of R.id.<name>
     * @return view
     */
    private View findView(int id)
    {
        if(getActivity()==null)
            return null;
        return getActivity().findViewById(id);
    }

    public void settListView()
    {
        View findLV = findView(R.id.listViewPosterPet);
        if(findLV == null)
            return;
        ListView lv = (ListView) findLV;
        if(getActivity() == null)
            return;
        //lv.setAdapter();
    }

}
