package no.dyrebar.dyrebar.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.adapter.FoundAnimalAdapter;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jFound;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class FoundFragment extends Fragment implements FoundAnimalAdapter.ItemClickListener
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
        return inflater.inflate(R.layout.fragment_found, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = getView().findViewById(R.id.found_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        AsyncTask.execute(()-> {
            String resp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "found"));
            }});
            ArrayList<Found> founds = new jFound().decodeArray(resp);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                FoundAnimalAdapter foundAnimalAdapter = new FoundAnimalAdapter(getContext(), this, founds);
                recyclerView.setAdapter(foundAnimalAdapter); }, 150
                );
        });
    }

    @Override
    public void onItemClicked(Found found)
    {
        // TODO: Start data displaying Activity
    }
}
