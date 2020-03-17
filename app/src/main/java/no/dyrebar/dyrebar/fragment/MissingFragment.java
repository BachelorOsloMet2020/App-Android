package no.dyrebar.dyrebar.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import java.util.ArrayList;

import no.dyrebar.dyrebar.CustomControls.DrawerLayoutOverride;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.activity.AnimalPosterDisplayActivity;
import no.dyrebar.dyrebar.adapter.MissingAnimalAdapter;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MissingFragment extends Fragment implements MissingAnimalAdapter.ItemClickListener
{
    DrawerLayoutOverride spl;
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
        return inflater.inflate(R.layout.fragment_missing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        RecyclerView rv = getView().findViewById(R.id.missing_recyclerview);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        spl = getView().findViewById(R.id.fragment_missing_drawer);

        AsyncTask.execute(() -> {
            String resp = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "missing"));
            }});
            ArrayList<Missing> missings = new jMissing().decodeArray(resp);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                MissingAnimalAdapter maa = new MissingAnimalAdapter(getContext(), this, missings);
                rv.setAdapter(maa); }, 150
                );
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.fragment_poster_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.poster_menu_search);
        if (searchItem != null)
        {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView)searchItem.getActionView();
            if (searchView != null)
            {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        //searchContent(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        //searchContent(newText);
                        return false;
                    }
                });
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.poster_menu_filter)
        {
            DrawerLayoutOverride dlo = getView().findViewById(R.id.fragment_missing_drawer);
            dlo.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(Missing missing)
    {
        Intent i = new Intent(getActivity(), AnimalPosterDisplayActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("missing", missing);
        i.putExtras(b);
        mListener.launchActivity(i);
    }
}
