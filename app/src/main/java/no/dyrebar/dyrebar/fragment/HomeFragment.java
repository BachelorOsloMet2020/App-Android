package no.dyrebar.dyrebar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.activity.AnimalPosterDisplayActivity;
import no.dyrebar.dyrebar.adapter.PetPosterAdapter;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.dialog.MissingDialog;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class HomeFragment extends Fragment implements PetPosterAdapter.ItemClickListener
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    /* Interface is being set */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        attachListeners();

        RecyclerView recyclerView = getView().findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        AsyncTask.execute(() -> {
            String resp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "missing"));
            }});
            ArrayList<Missing> missings = new jMissing().decodeArray(resp);

            new Handler(Looper.getMainLooper()).post(() -> {
                //MissingAnimalAdapter missingAnimalAdapter = new MissingAnimalAdapter(getContext(), this, missings);
                //recyclerView.setAdapter(missingAnimalAdapter);
            });
        });
    }




    private MissingDialog missingDialog;
    private void attachListeners()
    {
         this.findView(R.id.btn_missing).setOnClickListener(view ->
         {
             missingDialog = new MissingDialog(getActivity(), getString(R.string.missing));
             missingDialog.Show();
         });
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
        View findLV = findView(R.id.home_recyclerview);
        if(findLV == null)
            return;
        ListView lv = (ListView) findLV;
        if(getActivity() == null)
            return;
        //lv.setAdapter();
    }

    @Override
    public void onFoundItemClicked(Found found)
    {
        /**
         * AnimalFoundActivity.class mangler
         */
        /*
        Intent i = new Intent(getActivity(), AnimalFoundActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("found", found);
        i.putExtras(b);
        mListener.launchActivity(i);*/
    }

    @Override
    public void onMissingItemClicked(Missing missing)
    {
        Intent i = new Intent(getActivity(), AnimalPosterDisplayActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("missing", missing);
        i.putExtras(b);
        mListener.launchActivity(i);
    }
}
