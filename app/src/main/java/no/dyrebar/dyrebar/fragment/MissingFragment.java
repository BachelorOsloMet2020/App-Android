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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

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
import no.dyrebar.dyrebar.adapter.AnimalTypeAdapter;
import no.dyrebar.dyrebar.adapter.MissingAnimalAdapter;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.handler.DatasetHandler;
import no.dyrebar.dyrebar.interfaces.FragmentInterface;
import no.dyrebar.dyrebar.json.jMissing;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MissingFragment extends Fragment implements MissingAnimalAdapter.ItemClickListener
{
    DrawerLayoutOverride spl;
    FragmentInterface.FragmentListener mListener;
    private ArrayList<Missing> data = new ArrayList<>();

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
            data = new jMissing().decodeArray(resp);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                MissingAnimalAdapter maa = new MissingAnimalAdapter(getContext(), this, data);
                rv.setAdapter(maa); loadFilterData();}, 150
                );
        });
    }

    private DatasetHandler dh;
    private void loadFilterData()
    {
        dh = new DatasetHandler();
        Spinner spinner = getView().findViewById(R.id.filter_animal_type_spinner);
        spinner.setAdapter(new AnimalTypeAdapter(getActivity(), dh.getAnimalTypeFilterForPoster(getActivity())));
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Pair<Integer, String> p = (Pair<Integer, String>) parent.getAdapter().getItem(position);
                dh.setFilter_animal_type(p.first);
                applyFilter(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ((CheckBox)getView().findViewById(R.id.filter_animal_sex_male)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                dh.setFilter_sex_male(isChecked);
                applyFilter(true);
            }
        });
        ((CheckBox)getView().findViewById(R.id.filter_animal_sex_female)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                dh.setFilter_sex_female(isChecked);
                applyFilter(true);
            }
        });
        ((CheckBox)getView().findViewById(R.id.filter_animal_sex_unknown)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                dh.setFilter_sex_unknown(isChecked);
                applyFilter(true);
            }
        });


        getView().findViewById(R.id.filter_clear_button).setOnClickListener(v -> {
            dh.resetFilter();
            applyFilter(false);
        });
    }

    private void applyFilter(boolean hasFilter)
    {
        ArrayList<Missing> result = (ArrayList<Missing>) dh.getFiltered(data);
        RecyclerView rv = getView().findViewById(R.id.missing_recyclerview);
        MissingAnimalAdapter maa = (MissingAnimalAdapter) rv.getAdapter();
        if (hasFilter)
            maa.setItems(result);
        else
            maa.setItems(data);
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
                        userQuery(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        userQuery(newText);
                        return false;
                    }
                });
            }
        }
    }

    private void userQuery(String query)
    {
        if (query.length() == 0 || query.matches(" "))
        {
            RecyclerView rv = getView().findViewById(R.id.missing_recyclerview);
            MissingAnimalAdapter maa = (MissingAnimalAdapter) rv.getAdapter();
            maa.setItems(data);
        }
        else
        {
            ArrayList<Missing> result = new DatasetHandler().searchInList(query, data);
            RecyclerView rv = getView().findViewById(R.id.missing_recyclerview);
            MissingAnimalAdapter maa = (MissingAnimalAdapter) rv.getAdapter();
            maa.setItems(result);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        DrawerLayoutOverride dlo = getView().findViewById(R.id.fragment_missing_drawer);
        int id = item.getItemId();
        if (id == R.id.poster_menu_filter)
        {
            if (!dlo.isDrawerOpen(GravityCompat.END))
                dlo.openDrawer(GravityCompat.END);
            else
                dlo.closeDrawer(GravityCompat.END);
            return true;
        }
        else if (id == R.id.poster_menu_search)
            if (dlo.isDrawerOpen(GravityCompat.END))
                dlo.closeDrawer(GravityCompat.END);
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
