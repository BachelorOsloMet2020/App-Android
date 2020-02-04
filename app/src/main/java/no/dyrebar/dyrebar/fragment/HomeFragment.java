package no.dyrebar.dyrebar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import no.dyrebar.dyrebar.R;

public class HomeFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /**
     * Checks if activity is null. If not view is cast.
     * Prevents NPE
     * @param id
     * @return
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
